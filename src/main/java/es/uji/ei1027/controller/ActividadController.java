package es.uji.ei1027.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller; 
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.uji.ei1027.dao.ActividadDao;
import es.uji.ei1027.model.Acreditacion;
import es.uji.ei1027.model.Actividad;



@Controller	
@RequestMapping("/actividad")
public class ActividadController {
	@RequestMapping("/prova") 
	public String provaWeb(Model model ) {
		String message = "Provant la Web";
		model.addAttribute("message", message);
		return "actividad/prova"; 
	}
	
	private ActividadDao actividaddao;
	@Autowired 
	public void setActividadDao(ActividadDao actividadDao) { 
	       this.actividaddao=actividadDao;
	   }
	
	@RequestMapping("/listarActividades")
	public String provaUnAcreditacion(Model model) {
	   model.addAttribute("actividad", actividaddao.getActividad());
	   return "actividad/listarActividades"; 
	}
	
	@RequestMapping(value="/add") 
    public String addActividad(Model model) {
        model.addAttribute("actividad", new Actividad());
        return "actividad/add";
    }
	
	@RequestMapping(value="/add", method=RequestMethod.POST) 
	public String processAddSubmit(@ModelAttribute("actividad") Actividad actividad, BindingResult bindingResult) {  
     if (bindingResult.hasErrors()) 
            return "actividad/add";
     actividaddao.addActividad(actividad);
     return "redirect:listarActividades"; 
	}
	
	@RequestMapping(value="/update/{nombre}", method = RequestMethod.GET) 
    public String editAcreditacion(Model model, @PathVariable String nombre) { 
        model.addAttribute("actividad", actividaddao.getActividad(nombre));
        return "actividad/update"; 
    }
	
	@RequestMapping(value="/update/{nombre}", method = RequestMethod.POST) 
    public String processUpdateSubmit(@PathVariable String nombre, 
                            @ModelAttribute("actividad") Actividad actividad, 
                            BindingResult bindingResult) {
         if (bindingResult.hasErrors()) 
             return "actividad/update";
         actividaddao.updateActividad(actividad);;
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
