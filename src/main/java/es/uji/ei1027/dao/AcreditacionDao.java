package es.uji.ei1027.dao;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import es.uji.ei1027.model.Acreditacion;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Repository // En Spring els DAOs van anotats amb @Repository
public class AcreditacionDao {

    private JdbcTemplate jdbcTemplate;

    // Obté el jdbcTemplate a partir del Data Source
    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addAcreditacion(Acreditacion acreditacion) {
        jdbcTemplate.update("INSERT INTO Acreditacion VALUES(NEXTVAL('acreditacion_idcertificado_seq'),?, ?, ?, ?)",
                acreditacion.getCertificado(), acreditacion.getDni(), acreditacion.getEstado(), acreditacion.getTipo());
    }

    public void deleteAcreditacion(Acreditacion acreditacion) {
        jdbcTemplate.update("DELETE from Acreditacion where idcertificado=?", acreditacion.getIdcertificado());
    }
    
    public void deleteAcreditacion(int idcertificado) {
		jdbcTemplate.update("DELETE from Acreditacion where idcertificado=?", idcertificado);
		
	}

    public void updateAcreditacion(Acreditacion acreditacion) {
        jdbcTemplate.update("UPDATE Acreditacion SET  certificado=?, dni=?, estado=?, tipo=? WHERE idcertificado=?",
                acreditacion.getCertificado(), acreditacion.getDni(),
                acreditacion.getEstado(), acreditacion.getTipo(), acreditacion.getIdcertificado());
    }
    
    public void updateAcreditacion(Integer idcertificado, String estado) {
        jdbcTemplate.update("UPDATE Acreditacion SET estado=? WHERE idcertificado=?",
                estado, idcertificado);
    }

    public Acreditacion getAcreditacion(int idcertificado) {
        try {
            return jdbcTemplate.queryForObject("SELECT * from Acreditacion WHERE idcertificado=?",
                    new AcreditacionRowMapper(), idcertificado);
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Acreditacion> getAcreditacion() {
        try {
            return jdbcTemplate.query("SELECT * from Acreditacion",
                    new AcreditacionRowMapper());
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<Acreditacion>();
        }
    }
    
    public List<Acreditacion> getAcreditacionDni(String dni) {
        try {
            return jdbcTemplate.query("SELECT * from Acreditacion WHERE dni=?",
                    new AcreditacionRowMapper(), dni);
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<Acreditacion>();
        }
    }
    
    public List<String> getAcreditacionDniEstado(String dni) {
    	List<Acreditacion> lista;
        try {
            lista = jdbcTemplate.query("SELECT * from Acreditacion WHERE dni=? and estado='aceptada'",
                    new AcreditacionRowMapper(), dni);
            
            List<String> listaTipos = new LinkedList<String>();
            for(Acreditacion acred:lista) {
            	listaTipos.add(acred.getTipo());
            }
            
            return listaTipos;
            
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<String>();
        }
    }
}