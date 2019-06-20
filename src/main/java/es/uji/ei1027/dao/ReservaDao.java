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

import es.uji.ei1027.model.Reserva;

@Repository
public class ReservaDao {
	private JdbcTemplate jdbcTemplate;

    // Obté el jdbcTemplate a partir del Data Source
    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addReserva(Reserva reserva) throws ParseException {
    	String fechaAntigua = reserva.getFecha();
    	String [] parts = fechaAntigua.split("-");
        String nuevaFecha = parts[2]+"-"+parts[1]+"-"+parts[0];
        reserva.setFecha(nuevaFecha);
    	SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
    	java.util.Date date = sdf1.parse(reserva.getFecha());
    	java.sql.Date fecha = new java.sql.Date(date.getTime()); 

        jdbcTemplate.update("INSERT INTO Reserva VALUES(NEXTVAL('reserva_idreserva_seq'), ?, NEXTVAL('reserva_numtransaccion_seq'), ?, ?, ?, ?, ?)",
                reserva.getEstadoPago(), reserva.getNumAsistentes(), reserva.getPrecioPersona(), fecha, reserva.getDniCliente(), reserva.getNombreActividad());
    }

    public void deleteReserva(Reserva reserva) {
        jdbcTemplate.update("DELETE from Reserva where idreserva=?", reserva.getIdReserva());
    }
    
    public void deleteReserva(int idReserva) {
		jdbcTemplate.update("DELETE from Reserva where idreserva=?", idReserva);
		
	}

    public void updateReserva(Reserva reserva) throws ParseException { 
        jdbcTemplate.update("UPDATE Reserva SET  estadoPago=?, numAsistentes=?, precioPersona=?, dniCliente=?, nombreActividad=? WHERE idreserva=?",
        		reserva.getEstadoPago(), reserva.getNumAsistentes(), reserva.getPrecioPersona(), reserva.getDniCliente(), reserva.getNombreActividad(), reserva.getIdReserva());
    }

    public Reserva getReserva(int idReserva) {
    	Reserva reserva;
        try {
            reserva = jdbcTemplate.queryForObject("SELECT * from Reserva WHERE idreserva=?",
                    new ReservaRowMapper(), idReserva);
            
            reserva.setFecha(cambiarFecha(reserva.getFecha()));
            
            return reserva;
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Reserva> getReserva() {
    	List<Reserva> lista;
        try {
            lista = jdbcTemplate.query("SELECT * from Reserva",
                    new ReservaRowMapper());
            
            for(Reserva reserva : lista) {
            	reserva.setFecha(cambiarFecha(reserva.getFecha()));
            }
            
            return lista;
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<Reserva>();
        }
    }
    
    public List<Reserva> getReservaDni(String dni) {
    	List<Reserva> lista;
        try {
            lista = jdbcTemplate.query("SELECT * from Reserva WHERE dniCliente=?",
                    new ReservaRowMapper(), dni);
            
            for(Reserva reserva:lista) {
            	reserva.setFecha(cambiarFecha(reserva.getFecha()));
            }

            return lista;
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<Reserva>();
        }
    }
    
    public List<Reserva> getReservaNombre(String nombreActividad) {
    	List<Reserva> lista;
        try {
            lista = jdbcTemplate.query("SELECT * from Reserva WHERE nombreActividad=? ORDER BY fecha, estadoPago ASC , precioPersona DESC",
                    new ReservaRowMapper(), nombreActividad);
            
            for(Reserva reserva:lista) {
            	reserva.setFecha(cambiarFecha(reserva.getFecha()));
            }

            return lista;
            
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<Reserva>();
        }
    }
    
    public List<Reserva> getReservaActividad(String nombreActividad) {
    	List<Reserva> lista;
        try {
            lista = jdbcTemplate.query("SELECT * from Reserva WHERE nombreactividad=?",
                    new ReservaRowMapper(), nombreActividad);
            
            for(Reserva reserva:lista) {
            	reserva.setFecha(cambiarFecha(reserva.getFecha()));
            }

            return lista;
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<Reserva>();
        }
    }
    
    public String cambiarFecha(String fecha) {
        String [] parts = fecha.split("-");
        String nuevaFecha = parts[2]+"-"+parts[1]+"-"+parts[0];
        
        return nuevaFecha;
   }
    
}
