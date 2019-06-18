package es.uji.ei1027.controller;


	import org.springframework.validation.Errors;
	import org.springframework.validation.Validator;
	import es.uji.ei1027.model.Actividad;

	public class ActividadValidator implements Validator {
	  @Override
	  public boolean supports(Class<?> cls) {
		  return Actividad.class.equals(cls);
		 
	   }
	 
	  @Override
	  public void validate(Object obj, Errors errors) {
		 Actividad actividad = (Actividad)obj;
		 if (actividad.getNombre().trim().equals("") || actividad.getNombre().contains(" "))
		       errors.rejectValue("nombre", "obligatorio",
		                          "Hay que introducir un nombre valido, no se permiten espacios");
		 
		 if(actividad.getFecha().trim().equals(""))
			 errors.rejectValue("fecha", "obligatorio","Introduce una fecha válida");
		 
		 if(actividad.getPrecio().trim().equals(""))
			 errors.rejectValue("precio", "obligatorio", "Introduce un precio");
		 
		 if(actividad.getMinPersonas()<=0)
			 errors.rejectValue("minPersonas", "obligatorio", "Introduce un número");
		 
		 if(actividad.getMaxPersonas()<=0)
			 errors.rejectValue("maxPersonas", "obligatorio", "Introduce un número");
	   }
	}



