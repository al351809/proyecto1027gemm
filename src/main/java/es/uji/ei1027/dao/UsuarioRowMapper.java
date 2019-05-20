package es.uji.ei1027.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import es.uji.ei1027.model.DetallesUsuario;


public class UsuarioRowMapper implements RowMapper<DetallesUsuario> {
    public DetallesUsuario mapRow(ResultSet rs, int rowNum) throws SQLException {
    	DetallesUsuario usuario = new DetallesUsuario();
        usuario.setUsuario(rs.getString("Usuario"));
        usuario.setPassword(rs.getString("Password"));
        usuario.setRol(rs.getString("Rol"));
        
    
        return usuario;
    }
}
