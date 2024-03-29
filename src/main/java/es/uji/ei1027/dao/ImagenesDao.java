package es.uji.ei1027.dao;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import es.uji.ei1027.model.Imagenes;
import es.uji.ei1027.model.Instructor;


	
	@Repository
	public class ImagenesDao {
		private JdbcTemplate jdbcTemplate;

	    // Obté el jdbcTemplate a partir del Data Source
	    @Autowired
	    public void setDataSource(DataSource dataSource) {
	        jdbcTemplate = new JdbcTemplate(dataSource);
	    }

	    public void addImagen(Imagenes imagen) {
	        jdbcTemplate.update("INSERT INTO imagenes VALUES(?, ?)",
	                imagen.getNombre(), imagen.getImagen());
	    }

	    public void deleteImagen(Imagenes imagen) {
	        jdbcTemplate.update("DELETE from imagenes where imagen=?", imagen.getImagen());
	    }
	    
	    public void deleteImagenActividad(String nombre) {
	        jdbcTemplate.update("DELETE from Imagenes where nombre=?", nombre);
	    }
	  
	    public void updateImagen(Imagenes imagen) {
	        jdbcTemplate.update("UPDATE imagenes SET  nombre=?, imagen=?",
	        		imagen.getNombre(), imagen.getImagen());
	    }
	    
	    public void updateImagen(String imagenVieja, String imagenNueva) {
	    	jdbcTemplate.update("UPDATE imagenes SET imagen=? WHERE imagen=?", imagenNueva, imagenVieja);
	    }
	    
	    public List<Imagenes> getImagenes(String nombre) {
	    	List<Imagenes> imagenes = new LinkedList<Imagenes>();
	        try {
	            imagenes = jdbcTemplate.query("SELECT * from Imagenes WHERE nombre=?",
	                    new ImagenesRowMapper(), nombre);
	            
	            for(Imagenes img:imagenes) {
	            	String imagen = img.getImagen();
	            	if(imagen != null)
	            		img.setImagen(imagen.substring(25, imagen.length()));
	            }
	            
	            
	            return imagenes;
	        }
	        catch(EmptyResultDataAccessException e) {
	            return new ArrayList<Imagenes>();
	        }
	    }
	}   


