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
		       errors.rejectValue("DNI", "obligatorio",
		                          "Hay que introducir un dni");
		 	   
	   }
	}