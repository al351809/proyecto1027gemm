package es.uji.ei1027.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;


import es.uji.ei1027.model.TiposdeNiveles;

public class TiposdeNivelesRowMapper implements RowMapper<TiposdeNiveles> {
    public TiposdeNiveles mapRow(ResultSet rs, int rowNum) throws SQLException {
        TiposdeNiveles tiposdeNivel = new TiposdeNiveles();
        tiposdeNivel.setNombre(rs.getString("tipo"));
        
        return tiposdeNivel;
    }

}