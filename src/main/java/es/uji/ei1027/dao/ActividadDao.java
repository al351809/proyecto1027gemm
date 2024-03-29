package es.uji.ei1027.dao;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import es.uji.ei1027.model.Actividad;

import javax.sql.DataSource;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class ActividadDao {

	private JdbcTemplate jdbcTemplate;

    // Obté el jdbcTemplate a partir del Data Source
    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addActividad(Actividad actividad) throws ParseException {
    	SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
    	java.util.Date date = sdf1.parse(actividad.getFecha());
    	java.sql.Date fecha = new java.sql.Date(date.getTime()); 
        jdbcTemplate.update("INSERT INTO Actividad VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                actividad.getNombre(),actividad.getDni(), actividad.getDescripcion(), actividad.getDuracion(), fecha, actividad.getPrecio(), actividad.getMinPersonas(), actividad.getMaxPersonas(), actividad.getLugar(),actividad.getPuntoEncuentro(), actividad.getTextoCliente(), actividad.getTipo(), actividad.getEstado());
    }

    public void deleteActividad(Actividad actividad) {
        jdbcTemplate.update("DELETE from Actividad where nombre=?", actividad.getNombre());
    }
    
    public void deleteActividad(String nombre) {
		jdbcTemplate.update("DELETE from Actividad where nombre=?", nombre);
		
	}

    public void updateActividad(Actividad actividad) throws ParseException {
    	String fechaAntigua = actividad.getFecha();
    	String [] parts = fechaAntigua.split("-");
        String nuevaFecha = parts[2]+"-"+parts[1]+"-"+parts[0];
        actividad.setFecha(nuevaFecha);
    	SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
    	java.util.Date date = sdf1.parse(actividad.getFecha());
    	java.sql.Date fecha = new java.sql.Date(date.getTime()); 
        jdbcTemplate.update("UPDATE Actividad SET nombre=?, dni=?, descripcion=?, duracion=?, fecha=?, precio=?, minPersonas=?, maxPersonas=?, lugar=?, puntoEncuentro=?, textoCliente=?, tipo=?, estado=? WHERE nombre=?",
        		actividad.getNombre(),actividad.getDni(), actividad.getDescripcion(), actividad.getDuracion(), fecha, actividad.getPrecio(), actividad.getMinPersonas(), actividad.getMaxPersonas(), actividad.getLugar(),actividad.getPuntoEncuentro(), actividad.getTextoCliente(), actividad.getTipo(), actividad.getEstado(), actividad.getNombre());
    }

    public Actividad getActividad(String nombre) {
    	Actividad actividad;
        try { 
            actividad = jdbcTemplate.queryForObject("SELECT * from Actividad WHERE nombre=?",
                    new ActividadRowMapper(), nombre);
            
            actividad.setFecha(cambiarFecha(actividad.getFecha()));
            
            return actividad;
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }
    
    public List<Actividad> getActividadInstructor(String dni) {
    	List<Actividad> lista;
        try {
            lista = jdbcTemplate.query("SELECT * from Actividad WHERE dni=?",
                    new ActividadRowMapper(), dni);
            
            for(Actividad actividad: lista) {
            	actividad.setFecha(cambiarFecha(actividad.getFecha()));
            }
            
            
            return lista;
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Actividad> getActividad() {
    	List<Actividad> lista;
        try {
            lista = jdbcTemplate.query("SELECT * from Actividad",
                    new ActividadRowMapper());
            
            for(Actividad actividad: lista) {
            	actividad.setFecha(cambiarFecha(actividad.getFecha()));
            }
            
            
            return lista;
            
            
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<Actividad>();
        }
    }
    
    public List<Actividad> getActividadNombre( String nombre) {
    	List<Actividad> lista;
        try {
            lista = jdbcTemplate.query("SELECT * from Actividad WHERE nombre = ?",
                    new ActividadRowMapper(), nombre);
            
            for(Actividad actividad: lista) {
            	actividad.setFecha(cambiarFecha(actividad.getFecha()));
            }
            
            
            return lista;
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<Actividad>();
        }
    }
    
    public static Date ParseFecha(String fecha)
    {
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        Date fechaDate = null;
        try {
            fechaDate = formato.parse(fecha);
        } 
        catch (ParseException ex) 
        {
            System.out.println(ex);
        }
        return fechaDate;
    }
    

    
    public String cambiarFecha(String fecha) {
        String [] parts = fecha.split("-");
        String nuevaFecha = parts[2]+"-"+parts[1]+"-"+parts[0];
        
        return nuevaFecha;
   }
    
	
}
