package es.uji.ei1027.dao;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import es.uji.ei1027.model.Cliente;

public class ClienteDao {

    private JdbcTemplate jdbcTemplate;

    // Obté el jdbcTemplate a partir del Data Source
    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addCliente(Cliente cliente) {
        jdbcTemplate.update("INSERT INTO Cliente VALUES(?, ?, ?, ?, ?, ?)",
                cliente.getDni(), cliente.getAlias(), cliente.getNombre(), cliente.getEmail(), cliente.getSexo(), cliente.getFechaNacimiento());
    }

    public void deleteCliente(Cliente cliente) {
        jdbcTemplate.update("DELETE from Cliente where dni=?", cliente.getDni());
    }
    
    public void deleteCliente(String dni) {
		jdbcTemplate.update("DELETE from Cliente where dni=?", dni);
		
	}

    public void updateCliente(Cliente cliente) {
        jdbcTemplate.update("UPDATE Cliente SET  alias=?, nombre=?, email=?, sexo=?, fechaNacimiento=? WHERE dni=?",
                cliente.getAlias(),
                cliente.getNombre(), cliente.getEmail(), cliente.getSexo(), cliente.getFechaNacimiento(), cliente.getDni());
    }

    public Cliente getCliente(String dni) {
        try {
            return jdbcTemplate.queryForObject("SELECT * from Cliente WHERE dni=?",
                    new ClienteRowMapper(), dni);
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Cliente> getCliente() {
        try {
            return jdbcTemplate.query("SELECT * from Cliente",
                    new ClienteRowMapper());
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<Cliente>();
        }
    }

}
