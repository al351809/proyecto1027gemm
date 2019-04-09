package es.uji.ei1027.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;


import es.uji.ei1027.model.Actividad;

public class ActividadRowMapper implements RowMapper<Actividad> {
    public Actividad mapRow(ResultSet rs, int rowNum) throws SQLException {
        Actividad actividad = new Actividad();
        actividad.setNombre(rs.getString("nombre"));
        actividad.setDescripcion(rs.getString("descripcion"));
        actividad.setDuracion(rs.getString("duracion"));
        actividad.setFecha(rs.getString("fecha"));
        actividad.setPrecio(rs.getString("precio"));
        actividad.setMinPersonas(rs.getInt("minPersonas"));
        actividad.setMaxPersonas(rs.getInt("maxPersonas"));
        actividad.setLugar(rs.getString("lugar"));
        actividad.setPuntoEncuentro(rs.getString("puntoEncuentro"));
        actividad.setImagenes(rs.getString("imagenes"));
        actividad.setTextoCliente(rs.getString("textoCliente"));
        actividad.setTipo(rs.getString("tipo"));
        actividad.setEstado(rs.getString("estado"));
        return actividad;
    }

}
