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
                actividad.getNombre(), actividad.getDescripcion(), actividad.getDuracion(), fecha, actividad.getPrecio(), actividad.getMinPersonas(), actividad.getMaxPersonas(), actividad.getLugar(),actividad.getPuntoEncuentro(), actividad.getImagenes(), actividad.getTextoCliente(), actividad.getTipo(), actividad.getEstado());
    }

    public void deleteActividad(Actividad actividad) {
        jdbcTemplate.update("DELETE from Actividad where nombre=?", actividad.getNombre());
    }
    
    public void deleteActividad(String nombre) {
		jdbcTemplate.update("DELETE from Actividad where nombre=?", nombre);
		
	}

    public void updateActividad(Actividad actividad) throws ParseException {
    	SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
    	java.util.Date date = sdf1.parse(actividad.getFecha());
    	java.sql.Date fecha = new java.sql.Date(date.getTime()); 
        jdbcTemplate.update("UPDATE Actividad SET nombre=?, descripcion=?, duracion=?, fecha=?, precio=?, minPersonas=?, maxPersonas=?, lugar=?, puntoEncuentro=?, imagenes=?, textoCliente=?, tipo=?, estado=? WHERE nombre=?",
        		actividad.getNombre(), actividad.getDescripcion(), actividad.getDuracion(), fecha, actividad.getPrecio(), actividad.getMinPersonas(), actividad.getMaxPersonas(), actividad.getLugar(),actividad.getPuntoEncuentro(), actividad.getImagenes(), actividad.getTextoCliente(), actividad.getTipo(), actividad.getEstado(), actividad.getNombre());
    }

    public Actividad getActividad(String nombre) {
        try {
            return jdbcTemplate.queryForObject("SELECT * from Actividad WHERE nombre=?",
                    new ActividadRowMapper(), nombre);
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Actividad> getActividad() {
        try {
            return jdbcTemplate.query("SELECT * from Actividad",
                    new ActividadRowMapper());
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
	
}
