package es.uji.ei1027.dao;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import es.uji.ei1027.model.TipoActividad;

public class TipoActividadDao {
	
	private JdbcTemplate jdbcTemplate;

    // Obté el jdbcTemplate a partir del Data Source
    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addTipoActividad(TipoActividad tipoActividad) {
        jdbcTemplate.update("INSERT INTO TipoActividad VALUES(?, ?)",
                tipoActividad.getNombre(), tipoActividad.getNivel());
    }

    public void deleteTipoActividad(TipoActividad tipoActividad) {
        jdbcTemplate.update("DELETE from TipoActividad where nombre=?", tipoActividad.getNombre());
    }
    
    public void deleteTipoActividad(String nombre) {
		jdbcTemplate.update("DELETE from TipoActividad where nombre=?", nombre);
		
	}

    //No hemos hecho el update porque no es necesario.

    public TipoActividad getTipoActividad(String nombre) {
        try {
            return jdbcTemplate.queryForObject("SELECT * from TipoActividad WHERE nombre=?",
                    new TipoActividadRowMapper(), nombre);
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

}
