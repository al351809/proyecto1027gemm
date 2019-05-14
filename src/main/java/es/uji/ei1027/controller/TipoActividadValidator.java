package es.uji.ei1027.controller;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import es.uji.ei1027.model.TipoActividad;

public class TipoActividadValidator implements Validator {
  @Override
  public boolean supports(Class<?> cls) {
	  return TipoActividad.class.equals(cls);
	 
   }
 
  @Override
  public void validate(Object obj, Errors errors) {
	 TipoActividad Tipoactividad = (TipoActividad)obj;
	 if (Tipoactividad.getNombre().trim().equals(""))
	       errors.rejectValue("nombre", "obligatorio",
	                          "Hay que introducir un nombre");
	 if (Tipoactividad.getNivel().trim().equals(""))
		 errors.rejectValue("nivel", "obligatorio", "Hay que introducir un nivel");
	 	   
   }
}
