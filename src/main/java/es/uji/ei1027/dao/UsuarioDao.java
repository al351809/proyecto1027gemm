package es.uji.ei1027.dao;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import es.uji.ei1027.model.DetallesUsuario;

@Repository
public class UsuarioDao {
	 private JdbcTemplate jdbcTemplate;
	 @Autowired
	 public void setDataSource(DataSource dataSource) {
	      jdbcTemplate = new JdbcTemplate(dataSource);
	  }

	  public void addUsuario(DetallesUsuario usuario) {
	      jdbcTemplate.update("INSERT INTO usuario VALUES(?, ?, ?)",
	              usuario.getUsuario(), usuario.getRol(), usuario.getPassword());
	  }

	  public void deleteUsuario(DetallesUsuario usuario) {
	      jdbcTemplate.update("DELETE from Usuario where Usuario=?", usuario.getUsuario());
	  }
	  
	  public void deleteUsuario(String usuario) {
	      jdbcTemplate.update("DELETE from Usuario where Usuario=?", usuario);
	  }
	  
	  public void updateUsuario(DetallesUsuario usuario) {
	      jdbcTemplate.update("UPDATE Usuario SET  contrasenya=?, alias=? WHERE Usuario=?",
	    		  usuario.getUsuario(), usuario.getPassword(), usuario.getRol());
	  }
	    public DetallesUsuario getUsuario(String usuario) {
	        try {
					return jdbcTemplate.queryForObject("SELECT * from Usuario WHERE usuario=?",
					        new UsuarioRowMapper(), usuario);
	        }
	        catch(EmptyResultDataAccessException e) {
	            return null;
	        }
	    }

	    public List<DetallesUsuario> getUsuarios() {
	        try {
	            return jdbcTemplate.query("SELECT * from Usuario",
	                    new UsuarioRowMapper());
	        }
	        catch(EmptyResultDataAccessException e) {
	            return new ArrayList<DetallesUsuario>();
	        }
	    }

}
