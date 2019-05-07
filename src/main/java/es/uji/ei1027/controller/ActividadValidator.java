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
		 if (actividad.getNombre().trim().equals(""))
		       errors.rejectValue("nombre", "obligatorio",
		                          "Hay que introducir un valor");
		 	   
	   }
	}



