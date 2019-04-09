package es.uji.ei1027.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import es.uji.ei1027.model.TipoActividad;

public class TipoActividadRowMapper implements RowMapper<TipoActividad> {
    public TipoActividad mapRow(ResultSet rs, int rowNum) throws SQLException {
        TipoActividad tipoActividad = new TipoActividad();
        tipoActividad.setNombre(rs.getString("nombre"));
        tipoActividad.setNivel(rs.getString("nivel"));
        
        return tipoActividad;
    }

}
