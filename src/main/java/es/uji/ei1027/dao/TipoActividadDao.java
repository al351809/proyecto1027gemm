package es.uji.ei1027.dao;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import es.uji.ei1027.model.Acreditacion;
import es.uji.ei1027.model.TipoActividad;
import es.uji.ei1027.model.TiposdeActividad;
import es.uji.ei1027.model.TiposdeNiveles;

@Repository
public class TipoActividadDao {
	
	private JdbcTemplate jdbcTemplate;

    // Obté el jdbcTemplate a partir del Data Source
    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addTipoActividad(TipoActividad tipoActividad) {
        jdbcTemplate.update("INSERT INTO TipoActividad VALUES(?, ?, ?)",
                tipoActividad.getNombreCompleto(), tipoActividad.getNombre(), tipoActividad.getNivel());
    }

    public void deleteTipoActividad(TipoActividad tipoActividad) {
        jdbcTemplate.update("DELETE from TipoActividad where nombreactividad=?", tipoActividad.getNombreCompleto());
    }
    
    public void deleteTipoActividad(String nombreactividad) {
		jdbcTemplate.update("DELETE from TipoActividad where nombreactividad=?", nombreactividad);
		
	}

    //No hemos hecho el update porque no es necesario.

    public TipoActividad getTipoActividad(String nombreactividad) {
        try {
            return jdbcTemplate.queryForObject("SELECT * from TipoActividad WHERE nombreactividad=?",
                    new TipoActividadRowMapper(), nombreactividad);
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<TipoActividad> getTipoActividad() {
        try {
            return jdbcTemplate.query("SELECT * from TipoActividad",
                    new TipoActividadRowMapper());
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<TipoActividad>();
        }
    }
    
    public List<TiposdeActividad> getTiposdeActividad() {
        try {
            return jdbcTemplate.query("SELECT * from TiposdeActividad",
                    new TiposdeActividadRowMapper());
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<TiposdeActividad>();
        }
    }
    
    public List<TiposdeNiveles> getTiposdeNivel() {
        try {
            return jdbcTemplate.query("SELECT * from Tiposnivel",
                    new TiposdeNivelesRowMapper());
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<TiposdeNiveles>();
        }
    }
    
    public List<String> getNombreActividad( List <String> listadeTipos) {
    	List<TipoActividad> nombresActividad;
    	List<String> listaNombreActividad = new LinkedList<String>();;
        try {
        	for (String tipo : listadeTipos) {
        		nombresActividad = jdbcTemplate.query("SELECT * from TipoActividad WHERE nombre=?",
                    new TipoActividadRowMapper(), tipo);
        		for (TipoActividad nombreActividad : nombresActividad) {
        			
        			listaNombreActividad.add(nombreActividad.getNombreCompleto());
        			
        		}
        		
        	}
        	return listaNombreActividad;
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<String>();
        }
    }

}
