package es.uji.ei1027.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import es.uji.ei1027.model.TiposdeActividad;

public class TiposdeActividadRowMapper implements RowMapper<TiposdeActividad> {
    public TiposdeActividad mapRow(ResultSet rs, int rowNum) throws SQLException {
        TiposdeActividad tiposdeActividad = new TiposdeActividad();
        tiposdeActividad.setNombre(rs.getString("tipo"));
        
        return tiposdeActividad;
    }

}