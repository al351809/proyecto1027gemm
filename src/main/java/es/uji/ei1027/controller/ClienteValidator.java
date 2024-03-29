package es.uji.ei1027.controller;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import es.uji.ei1027.model.Cliente;

public class ClienteValidator implements Validator {
	  @Override
	  public boolean supports(Class<?> cls) {
		  return Cliente.class.equals(cls);
		 
	   }
	 
	  @Override
	  public void validate(Object obj, Errors errors) {
		 Cliente cliente = (Cliente)obj;
		 if (cliente.getDni().trim().equals(""))
		       errors.rejectValue("dni", "obligatorio",
		                          "Hay que introducir un dni");
		 
		 if (cliente.getEmail().trim().equals(""))
		       errors.rejectValue("email", "obligatorio",
		                          "Hay que introducir un email");
		 
		/*
		 * if (cliente.getFechaNacimiento().trim().equals(""))
		 * errors.rejectValue("fechaNacimiento", "obligatorio",
		 * "Hay que introducir una fecha");
		 */
		 	   
	   }
		 	   
	   }
	  
	  
	
