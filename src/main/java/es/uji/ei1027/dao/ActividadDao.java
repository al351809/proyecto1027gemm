package es.uji.ei1027.dao;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import es.uji.ei1027.model.Actividad;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ActividadDao {

	private JdbcTemplate jdbcTemplate;

    // Obté el jdbcTemplate a partir del Data Source
    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addActividad(Actividad actividad) {
        jdbcTemplate.update("INSERT INTO Actividad VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                actividad.getNombre(), actividad.getDescripcion(), actividad.getDuracion(), actividad.getFecha(), actividad.getPrecio(), actividad.getMinPersonas(), actividad.getMaxPersonas(), actividad.getLugar(),actividad.getPuntoEncuentro(), actividad.getImagenes(), actividad.getTextoCliente(), actividad.getTipo(), actividad.getEstado());
    }

    public void deleteActividad(Actividad actividad) {
        jdbcTemplate.update("DELETE from Actividad where nombre=?", actividad.getNombre());
    }
    
    public void deleteActividad(String nombre) {
		jdbcTemplate.update("DELETE from Actividad where nombre=?", nombre);
		
	}

    public void updateActividad(Actividad actividad) {
        jdbcTemplate.update("UPDATE Actividad SET  descripcion=?, duracion=?, fecha=?, precio=?, minPersonas=?, maxPersonas=?, lugar=?, puntoEncuentro=?, imagenes=?, textoCliente=?, tipo=?, estado=? WHERE nombre=?",
        		actividad.getDescripcion(), actividad.getDuracion(), actividad.getFecha(), actividad.getPrecio(), actividad.getMinPersonas(), actividad.getMaxPersonas(), actividad.getLugar(),actividad.getPuntoEncuentro(), actividad.getImagenes(), actividad.getTextoCliente(), actividad.getTipo(), actividad.getEstado(), actividad.getNombre());
    }

    public Actividad getActividad(String nombre) {
        try {
            return jdbcTemplate.queryForObject("SELECT * from Acreditacion WHERE nombre=?",
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
	
}