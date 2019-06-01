package es.uji.ei1027.dao;

import java.util.ArrayList;
import java.util.LinkedList;
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
    
    public void updateFoto(String dni, String foto) {
    	jdbcTemplate.update("UPDATE Instructor SET foto=? WHERE dni=?", foto, dni);
    }

    public Instructor getInstructor(String dni) {
    	Instructor instructor = new Instructor();
    	
        try {
        	
        	instructor = jdbcTemplate.queryForObject("SELECT * from Instructor WHERE dni=?",
			        new InstructorRowMapper(), dni);
        	
        	String foto = instructor.getFoto();
        	if(foto != null)
        		instructor.setFoto(foto.substring(25, foto.length()));
        	
			return instructor;
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Instructor> getInstructor() {
    	List<Instructor> instructors = new LinkedList<Instructor>();
        try {
        	
        	instructors = jdbcTemplate.query("SELECT * from Instructor",
                    new InstructorRowMapper());
        	
        	for(Instructor inst : instructors) {
        		String foto = inst.getFoto();
        		if(foto != null)
        			inst.setFoto(foto.substring(25, foto.length()));
        	}
        	
            return instructors;
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<Instructor>();
        }
    }
    
    public Instructor getInstructorAlias(String alias) {
    	Instructor instructor = new Instructor();
        try {
				instructor= jdbcTemplate.queryForObject("SELECT * from Instructor WHERE alias=?",
				        new InstructorRowMapper(), alias);
				
				String foto = instructor.getFoto();
				if(foto != null)
					instructor.setFoto(foto.substring(25, foto.length()));
	        	
				return instructor;
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }
    
}
