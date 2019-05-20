package es.uji.ei1027.dao;

import java.util.Collection;
import java.util.HashMap; 
import java.util.Map;

import javax.sql.DataSource;

import org.jasypt.util.password.BasicPasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import es.uji.ei1027.model.DetallesUsuario;
import es.uji.ei1027.model.Instructor;


@Repository
public class FakeUserProvider implements UsuarioDao {

  final Map<String, DetallesUsuario> knownUsers = new HashMap<String, DetallesUsuario>();
  @Autowired
  public FakeUserProvider() {
	BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor(); 
	DetallesUsuario usuarioAlice = new DetallesUsuario(); 
	usuarioAlice.setUsuario("alice"); 
	usuarioAlice.setContraseña(passwordEncryptor.encryptPassword("alice")); 
	knownUsers.put("alice", usuarioAlice);
	  
       DetallesUsuario usuarioBob = new DetallesUsuario(); 
       usuarioBob.setUsuario("bob"); 
       usuarioBob.setContraseña(passwordEncryptor.encryptPassword("bob")); 
       knownUsers.put("bob", usuarioBob);
  }
  

  @Override
  public DetallesUsuario loadUserByUsername(String usuario, String contraseña) { 
	  DetallesUsuario user = knownUsers.get(usuario.trim());
	  if (user == null)
		  return null; // Usuario no trobat
	  // Contrasenya
	 BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor(); 
	 if (passwordEncryptor.checkPassword(contraseña, user.getContraseña())) {
	 // Es deuria esborrar de manera segura el camp password abans de tornar-lo
		 return user; 
        } 
	 else {
		 return null; // bad login!
	 }
  }

  @Override 
  public Collection<DetallesUsuario> listAllUsers() {
	 return knownUsers.values();
  }
}
