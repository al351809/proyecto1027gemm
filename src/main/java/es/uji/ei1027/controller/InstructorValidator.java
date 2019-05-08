package es.uji.ei1027.controller;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import es.uji.ei1027.model.Instructor;

public class InstructorValidator implements Validator {
	  @Override
	  public boolean supports(Class<?> cls) {
		  return Instructor.class.equals(cls);
		 
	   }
	 
	  @Override
	  public void validate(Object obj, Errors errors) {
		 Instructor instructor = (Instructor)obj;
		 if (instructor.getDni().trim().equals(""))
		       errors.rejectValue("DNI", "obligatorio",
		                          "Hay que introducir un dni");
		 	   
	   }
	}