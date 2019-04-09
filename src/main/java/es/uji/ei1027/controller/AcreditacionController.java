package es.uji.ei1027.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller; 
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import es.uji.ei1027.dao.AcreditacionDao;
import es.uji.ei1027.model.Acreditacion;


@Controller	
@RequestMapping("/acreditaciones")
public class AcreditacionController {
	@RequestMapping("/prova") 
	public String provaWeb(Model model ) {
		String message = "Provant la Web";
		model.addAttribute("message", message);
		return "acreditaciones/prova"; 
	}
	
	private AcreditacionDao acreditaciondao;
	@Autowired 
	public void setAcreditcaionDao(AcreditacionDao acreditacionDao) { 
	       this.acreditaciondao=acreditacionDao;
	   }
	
	@RequestMapping("/listarAcreditaciones")
	public String provaUnAcreditacion(Model model) {
	   model.addAttribute("acreditacion", acreditaciondao.getAcreditacion());
	   return "acreditaciones/listarAcreditaciones"; 
	}
	
	@RequestMapping(value="/add") 
    public String addAcreditacion(Model model) {
        model.addAttribute("acreditacion", new Acreditacion());
        return "acreditaciones/add";
    }
	
	@RequestMapping(value="/add", method=RequestMethod.POST) 
	public String processAddSubmit(@ModelAttribute("acreditacion") Acreditacion acreditacion, BindingResult bindingResult) {  
     if (bindingResult.hasErrors()) 
            return "acreditacion/add";
     acreditaciondao.addAcreditacion(acreditacion);
     return "redirect:listarAcreditaciones"; 
	}
	
	@RequestMapping(value="/update/{idcertificado}", method = RequestMethod.GET) 
    public String editAcreditacion(Model model, @PathVariable Integer idcertificado) { 
        model.addAttribute("acreditacion", acreditaciondao.getAcreditacion(idcertificado));
        return "acreditaciones/update"; 
    }
	
	@RequestMapping(value="/update/{idcertificado}", method = RequestMethod.POST) 
    public String processUpdateSubmit(@PathVariable Integer idcertificado, 
                            @ModelAttribute("acreditacion") Acreditacion acreditacion, 
                            BindingResult bindingResult) {
         if (bindingResult.hasErrors()) 
             return "acreditaciones/update";
         acreditaciondao.updateAcreditacion(acreditacion);;
         return "redirect:../listarAcreditaciones"; 
    }
	
	@RequestMapping(value="/delete/{idcertificado}")
    public String processDelete(@PathVariable Integer idcertificado) {
           acreditaciondao.deleteAcreditacion(idcertificado);
           return "redirect:../listarAcreditaciones"; 
    }
	
	@RequestMapping(value="/delete/{idcertificado}", method=RequestMethod.POST) 
	public String processDeleteSubmit(@ModelAttribute("acreditacion") Acreditacion acreditacion,
			BindingResult bindingResult) {  
     if (bindingResult.hasErrors()) 
            return "acreditacion/delete";
     acreditaciondao.deleteAcreditacion(acreditacion);
     return "redirect:../listarAcreditaciones"; 
	}
	
	
}