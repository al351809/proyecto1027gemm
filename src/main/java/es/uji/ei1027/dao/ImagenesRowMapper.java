package es.uji.ei1027.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import es.uji.ei1027.model.Imagenes;


	public class ImagenesRowMapper implements RowMapper<Imagenes> {
	    public Imagenes mapRow(ResultSet rs, int rowNum) throws SQLException {
	        Imagenes imagen = new Imagenes();
	        imagen.setNombre(rs.getString("nombre"));
	        imagen.setImagen(rs.getString("imagen"));
	        return imagen;
	    }
	}

