package es.uji.ei1027.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import es.uji.ei1027.model.ClienteTipo;

public class ClienteTipoRowMapper implements RowMapper<ClienteTipo> {
    public ClienteTipo mapRow(ResultSet rs, int rowNum) throws SQLException {
        ClienteTipo clienteTipo = new ClienteTipo();
        clienteTipo.setDni(rs.getString("dni"));
        clienteTipo.setNombreActividad(rs.getString("nombreActividad"));
        
        return clienteTipo;
    }

}
