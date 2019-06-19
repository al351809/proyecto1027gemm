package es.uji.ei1027.dao;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import es.uji.ei1027.model.TipoActividad;

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

}
