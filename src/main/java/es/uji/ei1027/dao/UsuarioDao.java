package es.uji.ei1027.dao;

import java.util.Collection;

import es.uji.ei1027.model.DetallesUsuario;

public interface UsuarioDao {
	DetallesUsuario loadUserByUsername(String usuario, String contraseña);
 	Collection<DetallesUsuario> listAllUsers();

}
