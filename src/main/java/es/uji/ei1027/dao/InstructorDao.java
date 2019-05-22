package es.uji.ei1027.dao;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import es.uji.ei1027.model.Instructor;

@Repository
public class InstructorDao {
	private JdbcTemplate jdbcTemplate;

    // Obté el jdbcTemplate a partir del Data Source
    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addInstructor(Instructor instructor) {
        jdbcTemplate.update("INSERT INTO Instructor VALUES(?, ?, ?, ?, ?, ?)",
                instructor.getDni(), instructor.getAlias(), instructor.getNombre(), instructor.getEmail(), instructor.getNumeroCuenta(), instructor.getEstado());
    }

    public void deleteInstructor(Instructor instructor) {
        jdbcTemplate.update("DELETE from Instructor where dni=?", instructor.getDni());
    }
    
    public void deleteInstructor(String dni) {
		jdbcTemplate.update("DELETE from Instructor where dni=?", dni);
		
	}

    public void updateInstructor(Instructor instructor) {
        jdbcTemplate.update("UPDATE Instructor SET  nombre=?, email=?, numeroCuenta=? WHERE dni=?",
        		instructor.getNombre(), instructor.getEmail(), instructor.getNumeroCuenta(), instructor.getDni());
    }
    
    public void updateEstado(String dni, String estado) {
    	jdbcTemplate.update("UPDATE Instructor SET estado=? WHERE dni=?", estado, dni);
    }

    public Instructor getInstructor(String dni) {
        try {
				return jdbcTemplate.queryForObject("SELECT * from Instructor WHERE dni=?",
				        new InstructorRowMapper(), dni);
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Instructor> getInstructor() {
        try {
            return jdbcTemplate.query("SELECT * from Instructor",
                    new InstructorRowMapper());
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<Instructor>();
        }
    }
    
    public Instructor getInstructorAlias(String alias) {
        try {
				return jdbcTemplate.queryForObject("SELECT * from Instructor WHERE alias=?",
				        new InstructorRowMapper(), alias);
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }
    
}
