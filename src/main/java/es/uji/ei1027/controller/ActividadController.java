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
import es.uji.ei1027.dao.InstructorDao;
import es.uji.ei1027.model.Actividad;
import es.uji.ei1027.model.DetallesUsuario;
import es.uji.ei1027.model.Instructor;
import es.uji.ei1027.service.ActividadService;




@Controller	
@RequestMapping("/actividad")
public class ActividadController {

	private ActividadDao actividaddao;
	private ActividadService actividadService;
	private InstructorDao instructordao;
	
	@Autowired
	public void setActividadService(ActividadService actividadService) {
		this.actividadService = actividadService;
	}

	
	@Autowired 
	public void setActividadDao(ActividadDao actividadDao) { 
	       this.actividaddao=actividadDao;
	   }
	
	@Autowired
	public void setInstructorDao(InstructorDao instructorDao) {
		this.instructordao = instructorDao;
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
		DetallesUsuario user = (DetallesUsuario) session.getAttribute("user");
		Instructor instructor = instructordao.getInstructorAlias(user.getUsuario());
		model.addAttribute("instructor", instructor);
		session.setAttribute("nextUrl", null);
		model.addAttribute("actividad", actividaddao.getActividad());
		
	return "actividad/listarActividades"; 
	}
	
	@RequestMapping("/listarActividadesInstructor")
	public String actividadesInstructor(HttpSession session, Model model) {
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
		Instructor instructor = instructordao.getInstructorAlias(usuario.getUsuario());
		session.setAttribute("nextUrl", null);
		model.addAttribute("actividad", actividaddao.getActividadInstructor(instructor.getDni()));
		
	return "actividad/listarActividadesInstructor"; 
	}
	
	@RequestMapping(value="/add", method=RequestMethod.GET)
	public String addActiv(HttpSession session, Model model) {
		model.addAttribute("actividad", new Actividad());
		model.addAttribute("nombre", actividadService.getTiposActividad());
	   return "actividad/add";
	}
	
	@RequestMapping(value="/add", method=RequestMethod.POST) 
	public String processAddSubmit(HttpSession session, @ModelAttribute("actividad") Actividad actividad, BindingResult bindingResult, @ModelAttribute("dni") String dni) {
		DetallesUsuario usuario = (DetallesUsuario) session.getAttribute("user");
		Instructor instructor = instructordao.getInstructorAlias(usuario.getUsuario());
		actividad.setDni(instructor.getDni());
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
	    
	  return "actividad/listarActividadesInstructor"; 
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
      return "redirect:../listarActividadesInstructor"; 
    }
	
	@RequestMapping(value="/delete/{nombre}")
    public String processDelete(@PathVariable String nombre) {
		actividaddao.deleteActividad(nombre);
           return "redirect:../listarActividadesInstructor"; 
    }
	
	@RequestMapping(value="/delete/{nombre}", method=RequestMethod.POST) 
	public String processDeleteSubmit(@ModelAttribute("actividad") Actividad actividad,
			BindingResult bindingResult) {  
     if (bindingResult.hasErrors()) 
            return "actividad/delete";
     actividaddao.deleteActividad(actividad);
     return "redirect:../listarActividadesInstructor"; 
	}

}
