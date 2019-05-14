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
		 if (reserva.getIdReserva()<=0)
		       errors.rejectValue("idReserva", "obligatorio",
		                          "Hay que introducir un dni");
		 
		 if(reserva.getFecha().trim().equals(""))
			 errors.rejectValue("fecha", "obligatorio", "Introduce una fecha válida");
		 
		 if(reserva.getDniCliente().trim().equals(""))
			 errors.rejectValue("dniCliente", "obligatorio", "Es necesario el dni del cliente");
		 
		 if(reserva.getNombreActividad().trim().equals(""))
			 errors.rejectValue("nombreActividad", "obligatorio", "Es necesario el nombre de la actividad");
		 	   
	   }
	}
