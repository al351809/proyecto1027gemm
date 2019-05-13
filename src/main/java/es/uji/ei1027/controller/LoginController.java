package es.uji.ei1027.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Controller; 
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult; 
import org.springframework.web.bind.annotation.ModelAttribute; 
import org.springframework.web.bind.annotation.RequestMapping; 
import org.springframework.web.bind.annotation.RequestMethod; 


import es.uji.ei1027.dao.UsuarioDao;
import es.uji.ei1027.model.DetallesUsuario;

import org.springframework.validation.Errors; 
import org.springframework.validation.Validator;



class UserValidator implements Validator { 
	@Override
	public boolean supports(Class<?> cls) { 
		return DetallesUsuario.class.isAssignableFrom(cls);
	}
	@Override 
	public void validate(Object obj, Errors errors) {
	  DetallesUsuario detallesUsuario = (DetallesUsuario)obj;
	  if(detallesUsuario.getUsuario().isEmpty()) {
		  errors.rejectValue("Usuario", "obligatorio",
                  "Hay que introducir un nombre usuario");
	  }
	  if(detallesUsuario.getContraseña().isEmpty()) {
		  errors.rejectValue("Contraseña", "obligatorio",
                  "Hay que introducir una contraseña");
	  }
	}
}

@Controller
public class LoginController {
	@Autowired
	private UsuarioDao usuarioDao;

	@RequestMapping("/login")
	public String login(Model model) {
		model.addAttribute("user", new DetallesUsuario());
		return "login";
	}

	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String checkLogin(@ModelAttribute("user") DetallesUsuario user,  		
				BindingResult bindingResult, HttpSession session) {
		UserValidator userValidator = new UserValidator(); 
		userValidator.validate(user, bindingResult); 
		if (bindingResult.hasErrors()) {
			return "login";
		}
	       // Comprova que el login siga correcte 
		// intentant carregar les dades de l'usuari 
		user = usuarioDao.loadUserByUsername(user.getUsuario(),user.getContraseña()); 
		if (user == null) {
			bindingResult.rejectValue("contraseña", "badpw", "Contraseña incorrecta"); 
			return "login";
		}
		// Autenticats correctament. 
		// Guardem les dades de l'usuari autenticat a la sessió
		session.setAttribute("user", user); 
		String url;
		if (session.getAttribute("nextUrl") != null)
			url = (String) session.getAttribute("nextUrl");
		else
			url = "/";

		// Torna a la pàgina principal
		return "redirect:/" + url;
	}

	@RequestMapping("/logout") 
	public String logout(HttpSession session) {
		session.invalidate(); 
		return "redirect:/";
	}
}
