package es.uji.ei1027.controller;


import java.text.ParseException;

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
	public String pruevaUnaActividad(Model model) {
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
            return "acreditaciones/add";
		
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
