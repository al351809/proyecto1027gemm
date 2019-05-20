package es.uji.ei1027.dao;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import es.uji.ei1027.model.Cliente;

@Repository
public class ClienteDao {

    private JdbcTemplate jdbcTemplate;

    // Obté el jdbcTemplate a partir del Data Source
    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addCliente(Cliente cliente) throws ParseException  {
    	SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
    	java.util.Date date = sdf1.parse(cliente.getFechaNacimiento());
    	java.sql.Date fecha = new java.sql.Date(date.getTime());
        jdbcTemplate.update("INSERT INTO Cliente VALUES(?, ?, ?, ?, ?, ?)",
                cliente.getDni(), cliente.getAlias(), cliente.getNombre(), cliente.getEmail(), cliente.getSexo(), fecha);
    }

    public void deleteCliente(Cliente cliente) {
        jdbcTemplate.update("DELETE from Cliente where dni=?", cliente.getDni());
    }
    
    public void deleteCliente(String dni) {
		jdbcTemplate.update("DELETE from Cliente where dni=?", dni);
		
	}

    public void updateCliente(Cliente cliente) throws ParseException {
    	SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
    	java.util.Date date = sdf1.parse(cliente.getFechaNacimiento());
    	java.sql.Date fecha = new java.sql.Date(date.getTime());
        jdbcTemplate.update("UPDATE Cliente SET nombre=?, email=?, sexo=?, fechaNacimiento=? WHERE dni=?",
                cliente.getNombre(), cliente.getEmail(), cliente.getSexo(), fecha, cliente.getDni());
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
