package es.uji.ei1027.dao;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import es.uji.ei1027.model.ClienteTipo;

@Repository
public class ClienteTipoDao {

    private JdbcTemplate jdbcTemplate;

    // Obté el jdbcTemplate a partir del Data Source
    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addClienteTipo(ClienteTipo clienteTipo) {
        jdbcTemplate.update("INSERT INTO ClienteTipo VALUES(?, ?)",
                Integer.parseInt(clienteTipo.getDni()), clienteTipo.getNombreActividad());
    }

    public void deleteClienteTipo(ClienteTipo clienteTipo) {
        jdbcTemplate.update("DELETE from ClienteTipo where dni=?", clienteTipo.getDni());
    }
    
    public void deleteClienteTipo(String dni) {
		jdbcTemplate.update("DELETE from ClienteTipo where dni=?", dni);
		
	}

    public void updateClienteTipo(ClienteTipo clienteTipo) {
        jdbcTemplate.update("UPDATE ClienteTipo SET  nombreActividad=? WHERE dni=?",
                clienteTipo.getNombreActividad(),
                clienteTipo.getDni());
    }

    public ClienteTipo getClienteTipo(String dni) {
        try {
            return jdbcTemplate.queryForObject("SELECT * from ClienteTipo WHERE dni=?",
                    new ClienteTipoRowMapper(), dni);
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<ClienteTipo> getClienteTipo() {
        try {
            return jdbcTemplate.query("SELECT * from ClienteTipo",
                    new ClienteTipoRowMapper());
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<ClienteTipo>();
        }
    }

}
