package es.uji.ei1027.dao;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import es.uji.ei1027.model.Reserva;

public class ReservaDao {
	private JdbcTemplate jdbcTemplate;

    // Obté el jdbcTemplate a partir del Data Source
    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addReserva(Reserva reserva) {
        jdbcTemplate.update("INSERT INTO Reserva VALUES(?, ?, ?, ?, ?, ?, ?, ?)",
                reserva.getIdReserva(), reserva.getEstadoPago(), reserva.getNumTransaccion(), reserva.getNumAsistentes(), reserva.getPrecioPersona(), reserva.getFecha(), reserva.getDniCliente(), reserva.getNombreActividad());
    }

    public void deleteReserva(Reserva reserva) {
        jdbcTemplate.update("DELETE from Reserva where idReserva=?", reserva.getIdReserva());
    }
    
    public void deleteIReserva(int idReserva) {
		jdbcTemplate.update("DELETE from Reserva where idReserva=?", idReserva);
		
	}

    public void updateReserva(Reserva reserva) {
        jdbcTemplate.update("UPDATE Reserva SET  estadoPago=?, numTransaccion=?, numAsistentes=?, precioPersona=?, fecha=?, dniCliente=?, nombreActividad=? WHERE idReserva=?",
        		reserva.getEstadoPago(), reserva.getNumTransaccion(), reserva.getNumAsistentes(), reserva.getPrecioPersona(), reserva.getFecha(), reserva.getDniCliente(), reserva.getNombreActividad(), reserva.getIdReserva());
    }

    public Reserva getReserva(String idReserva) {
        try {
            return jdbcTemplate.queryForObject("SELECT * from Reserva WHERE idReserva=?",
                    new ReservaRowMapper(), idReserva);
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Reserva> getReserva() {
        try {
            return jdbcTemplate.query("SELECT * from Reserva",
                    new ReservaRowMapper());
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<Reserva>();
        }
    }
}
