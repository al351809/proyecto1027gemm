package es.uji.ei1027.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import es.uji.ei1027.model.Reserva;

public class ReservaRowMapper implements RowMapper<Reserva> {
    public Reserva mapRow(ResultSet rs, int rowNum) throws SQLException {
        Reserva reserva = new Reserva();
        reserva.setIdReserva(rs.getInt("idReserva"));
        reserva.setEstadoPago(rs.getString("estadoPago"));
        reserva.setNumTransaccion(rs.getInt("numTransaccion"));
        reserva.setNumAsistentes(rs.getInt("numAsistentes"));
        reserva.setPrecioPersona(rs.getInt("precioPersona"));
        reserva.setFecha(rs.getString("fecha"));
        reserva.setDniCliente(rs.getString("dniCliente"));
        reserva.setNombreActividad(rs.getString("nombreActividad"));
    
        return reserva;
    }

}
