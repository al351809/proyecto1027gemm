package es.uji.ei1027.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller; 
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import es.uji.ei1027.dao.AcreditacionDao;
import es.uji.ei1027.model.Acreditacion;


@Controller	
public class AcreditacionController {
	@RequestMapping("/prova") 
	public String provaWeb(Model model ) {
		String message = "Provant la Web";
		model.addAttribute("message", message);
		return "prova"; 
	}
	//holaaaaa

	@Autowired 
	AcreditacionDao acreditaciondao;
	
	@RequestMapping("/provaAcreditacion")
	public String provaUnAcreditacion(Model model) {
	   Acreditacion acreditacion = acreditaciondao.getAcreditacion(1);
	   model.addAttribute("message", acreditacion.toString()); 
	   return "prova"; 
	}
}
