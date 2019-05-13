package es.uji.ei1027.controller;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import es.uji.ei1027.dao.InstructorDao;
import es.uji.ei1027.model.Instructor;

public class InstructorValidator implements Validator {
	  InstructorDao dao = new InstructorDao();
	
	  @Override
	  public boolean supports(Class<?> cls) {
		  return Instructor.class.equals(cls);
		 
	   }
	 
	  @Override
	  public void validate(Object obj, Errors errors) {
		 Instructor instructor = (Instructor)obj;
		 
		 if (instructor.getDni().trim().equals(""))
		       errors.rejectValue("dni", "obligatorio",
		                          "Hay que introducir un dni");	
		 
		 if (instructor.getEmail().trim().equals(""))
		       errors.rejectValue("email", "obligatorio",
		                          "Hay que introducir un email");	
		 
		 if (instructor.getNumeroCuenta().trim().equals(""))
		       errors.rejectValue("numeroCuenta", "obligatorio",
		                          "Hay que introducir un numero de cuenta");	
		 	   
	   }
	}
