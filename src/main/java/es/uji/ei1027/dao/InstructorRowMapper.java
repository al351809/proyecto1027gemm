package es.uji.ei1027.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import es.uji.ei1027.model.Instructor;

public class InstructorRowMapper implements RowMapper<Instructor> {
    public Instructor mapRow(ResultSet rs, int rowNum) throws SQLException {
        Instructor instructor = new Instructor();
        instructor.setDni(rs.getString("dni"));
        instructor.setNombre(rs.getString("nombre"));
        instructor.setAlias(rs.getString("alias"));
        instructor.setEmail(rs.getString("email"));
        instructor.setNumeroCuenta(rs.getString("numeroCuenta"));
        instructor.setIdCertificado(rs.getInt("idCertificado"));
        instructor.setEstado(rs.getString("estado"));
    
        return instructor;
    }

}
