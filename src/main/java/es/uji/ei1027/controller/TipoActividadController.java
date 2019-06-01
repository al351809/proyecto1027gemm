package es.uji.ei1027.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.uji.ei1027.dao.TipoActividadDao;
import es.uji.ei1027.model.TipoActividad;

@Controller	
@RequestMapping("/tipoActividad")
public class TipoActividadController {
	
	private TipoActividadDao tipoActividaddao;
	@Autowired 
	public void setTipoActividadDao(TipoActividadDao tipoActividadDao) { 
	       this.tipoActividaddao=tipoActividadDao;
	   }
	
	@RequestMapping("/listarTiposActividad")
	public String pruevaUnTipoActividad(Model model) {
	   model.addAttribute("tipoActividad", tipoActividaddao.getTipoActividad());
	   return "tipoActividad/listarTiposActividad"; 
	}
	
	@RequestMapping(value="/add") 
    public String addTipoActividad(Model model) {
        model.addAttribute("tipoActividad", new TipoActividad());
        return "tipoActividad/add";
    }
	
	@RequestMapping(value="/add", method=RequestMethod.POST) 
	public String processAddSubmit(@ModelAttribute("tipoActividad") TipoActividad tipoActividad, BindingResult bindingResult) { 
	TipoActividadValidator TipoactividadValidator = new TipoActividadValidator(); 
	TipoactividadValidator.validate(tipoActividad, bindingResult);
     if (bindingResult.hasErrors()) 
            return "tipoActividad/add";
     tipoActividaddao.addTipoActividad(tipoActividad);
     return "redirect:listarTiposActividad"; 
	}
	
	@RequestMapping(value="/delete/{nombre}")
    public String processDelete(@PathVariable String nombre) {
		
		tipoActividaddao.deleteTipoActividad(nombre);
           return "redirect:../listarTiposActividad"; 
    }
	
	@RequestMapping(value="/delete/{nombre}", method=RequestMethod.POST) 
	public String processDeleteSubmit(@ModelAttribute("tipoActividad") TipoActividad tipoActividad,
			BindingResult bindingResult) {  
     if (bindingResult.hasErrors()) 
            return "tipoActividad/delete";
     tipoActividaddao.deleteTipoActividad(tipoActividad);
     return "redirect:../listarTiposActividad"; 
	}

}
