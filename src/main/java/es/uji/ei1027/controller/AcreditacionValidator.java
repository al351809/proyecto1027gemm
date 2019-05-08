package es.uji.ei1027.controller;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import es.uji.ei1027.model.Acreditacion;

public class AcreditacionValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return Acreditacion.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		Acreditacion acreditacion = (Acreditacion)target;
		if (acreditacion.getIdcertificado() <=0)
			errors.rejectValue("idcertificado", "obligatori", "Hay que introducir un idCertificado");

	}

}
