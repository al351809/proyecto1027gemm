package es.uji.ei1027.controller;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import es.uji.ei1027.dao.UsuarioDao;
import es.uji.ei1027.model.DetallesUsuario;
import es.uji.ei1027.model.Instructor;

public class UsuarioValidator implements Validator{

	  UsuarioDao dao = new UsuarioDao();
	
	  @Override
	  public boolean supports(Class<?> cls) {
		  return Instructor.class.equals(cls);
		 
	   }
	 
	  @Override
	  public void validate(Object obj, Errors errors) {
		 DetallesUsuario usuario = (DetallesUsuario)obj;
		 
		 if (usuario.getUsuario().trim().equals(""))
		       errors.rejectValue("usuario", "obligatorio",
		                          "Hay que introducir un usuario");	
		 
		 if (usuario.getPassword().trim().equals(""))
		       errors.rejectValue("password", "obligatorio",
		                          "Hay que introducir una contraseña");	
	
	  }
}
