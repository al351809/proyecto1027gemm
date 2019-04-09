package es.uji.ei1027.dao;

import org.springframework.jdbc.core.RowMapper;

import es.uji.ei1027.model.Acreditacion;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class AcreditacionRowMapper implements RowMapper<Acreditacion> {
    public Acreditacion mapRow(ResultSet rs, int rowNum) throws SQLException {
        Acreditacion acreditacion = new Acreditacion();
        acreditacion.setIdcertificado(rs.getInt("idcertificado"));
        acreditacion.setCertificado(rs.getString("certificado"));
        acreditacion.setEstado(rs.getString("estado"));
        return acreditacion;
    }
}
