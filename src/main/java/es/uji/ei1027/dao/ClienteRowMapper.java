package es.uji.ei1027.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import es.uji.ei1027.model.Cliente;

public class ClienteRowMapper implements RowMapper<Cliente> {
    public Cliente mapRow(ResultSet rs, int rowNum) throws SQLException {
        Cliente cliente = new Cliente();
        cliente.setDni(rs.getString("dni"));
        cliente.setAlias(rs.getString("alias"));
        cliente.setNombre(rs.getString("nombre"));
        cliente.setEmail(rs.getString("email"));
        cliente.setSexo(rs.getString("sexo"));
        cliente.setFechaNacimiento(rs.getDate("fechaNacimiento"));
        
        return cliente;
    }
}
