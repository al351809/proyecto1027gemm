package es.uji.ei1027.controller;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import es.uji.ei1027.model.Reserva;

public class ReservaValidator implements Validator {
	  @Override
	  public boolean supports(Class<?> cls) {
		  return Reserva.class.equals(cls);
		 
	   }
	 
	  @Override
	  public void validate(Object obj, Errors errors) {
		 Reserva reserva = (Reserva)obj;
		 
		 if(reserva.getNumAsistentes() <=0)
			 errors.rejectValue("numAsistentes", "obligatorio", "Minimo un asistente");
		 
		 	   
	   }
	  
	}
