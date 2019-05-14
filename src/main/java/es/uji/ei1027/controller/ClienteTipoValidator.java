package es.uji.ei1027.controller;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import es.uji.ei1027.model.ClienteTipo;

public class ClienteTipoValidator implements Validator {
	  @Override
	  public boolean supports(Class<?> cls) {
		  return ClienteTipo.class.equals(cls);
		 
	   }
	 
	  @Override
	  public void validate(Object obj, Errors errors) {
		 ClienteTipo clientetipo = (ClienteTipo)obj;
		 if (clientetipo.getDni().trim().equals(""))
		       errors.rejectValue("dni", "obligatorio",
		                          "Hay que introducir un dni");
		 
		 if (clientetipo.getNombreActividad().trim().equals(""))
			 errors.rejectValue("nombreActividad", "obligatorio", "Hay que introducir un nombre");
		 	   
	   }
	}