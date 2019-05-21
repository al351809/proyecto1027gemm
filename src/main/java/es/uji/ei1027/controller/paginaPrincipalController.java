package es.uji.ei1027.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class paginaPrincipalController {
	
	@Autowired
	@RequestMapping("/paginaprincipal")
	public String paginaprincipal() {
		return "paginaprincipal";
	}
	
}
