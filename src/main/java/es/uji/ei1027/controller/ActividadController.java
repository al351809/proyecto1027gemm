package es.uji.ei1027.controller;


import java.text.ParseException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller; 
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.uji.ei1027.dao.ActividadDao;
import es.uji.ei1027.model.Actividad;
import es.uji.ei1027.model.DetallesUsuario;
import es.uji.ei1027.service.ActividadService;




@Controller	
@RequestMapping("/actividad")
public class ActividadController {

	private ActividadDao actividaddao;
	private ActividadService actividadService;
	
	@Autowired
	public void setActividadService(ActividadService actividadService) {
		this.actividadService = actividadService;
	}

	
	@Autowired 
	public void setActividadDao(ActividadDao actividadDao) { 
	       this.actividaddao=actividadDao;
	   }
	
	@RequestMapping("/listarActividades")
	public String pruevaUnaActividad(HttpSession session, Model model) {
		if (session.getAttribute("user") == null){ 
	          model.addAttribute("user", new DetallesUsuario()); 
	          session.setAttribute("nextUrl", "actividad/listarActividades");
	          return "login";
	   }
		DetallesUsuario usuario = (DetallesUsuario) session.getAttribute("user");
		
		/*switch(usuario.getRol()) {
    	case "admin":
    		System.out.println("Soy una patata admin");break;
    	case "instructor":
    		System.out.println("Soy una patata instructor");break;
    	case "cliente":
    		System.out.println("Soy una patata cliente");break;
    }*/
		
		session.setAttribute("nextUrl", null);
		model.addAttribute("actividad", actividaddao.getActividad());
		
	return "actividad/listarActividades"; 
	}
	
	@RequestMapping(value="/add", method=RequestMethod.GET)
	public String addActiv(Model model) {
	   model.addAttribute("actividad", new Actividad());
	   model.addAttribute("nombre", actividadService.getTiposActividad());
	   return "actividad/add";
	}
	
	@RequestMapping(value="/add", method=RequestMethod.POST) 
	public String processAddSubmit(@ModelAttribute("actividad") Actividad actividad, BindingResult bindingResult) {  
		ActividadValidator actividadValidator = new ActividadValidator(); 
		actividadValidator.validate(actividad, bindingResult);
		if (bindingResult.hasErrors()) 
            return "actividad/add";
		
		//Esto es el control de la excepcion de la fecha que lanzamos desde la otra clase.
	     try {
			actividaddao.addActividad(actividad);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	     return "redirect:../listarActividades"; 
	}

	
	@RequestMapping(value="/update/{nombre}", method = RequestMethod.GET) 
    public String editActividad(Model model, @PathVariable String nombre) { 
        model.addAttribute("actividad", actividaddao.getActividad(nombre));
        model.addAttribute("nombre", actividadService.getTiposActividad());
        return "actividad/update"; 
    }
	
	@RequestMapping(value="/update/{nombre}", method = RequestMethod.POST) 
    public String processUpdateSubmit(@PathVariable String nombre, 
                            @ModelAttribute("actividad") Actividad actividad, 
                            BindingResult bindingResult) {
		ActividadValidator actividadValidator = new ActividadValidator(); 
		actividadValidator.validate(actividad, bindingResult);
         if (bindingResult.hasErrors()) 
             return "actividad/update";
         try {
			actividaddao.updateActividad(actividad);
		} catch (ParseException e) {
			e.printStackTrace();
		}
         return "redirect:../listarActividades"; 
    }
	
	@RequestMapping(value="/delete/{nombre}")
    public String processDelete(@PathVariable String nombre) {
		actividaddao.deleteActividad(nombre);
           return "redirect:../listarActividades"; 
    }
	
	@RequestMapping(value="/delete/{nombre}", method=RequestMethod.POST) 
	public String processDeleteSubmit(@ModelAttribute("actividad") Actividad actividad,
			BindingResult bindingResult) {  
     if (bindingResult.hasErrors()) 
            return "actividad/delete";
     actividaddao.deleteActividad(actividad);
     return "redirect:../listarActividades"; 
	}

}
