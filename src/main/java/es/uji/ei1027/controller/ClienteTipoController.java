package es.uji.ei1027.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.uji.ei1027.dao.ClienteTipoDao;
import es.uji.ei1027.model.ClienteTipo;

@Controller	
@RequestMapping("/clienteTipo")
public class ClienteTipoController {
	
	private ClienteTipoDao clienteTipodao;
	@Autowired 
	public void setClienteTipoDao(ClienteTipoDao clienteTipoDao) { 
	       this.clienteTipodao=clienteTipoDao;
	   }
	
	@RequestMapping("/listarClientesTipo")
	public String pruevaUnClienteTipo(Model model) {
	   model.addAttribute("clienteTipo", clienteTipodao.getClienteTipo());
	   return "clienteTipo/listarClientesTipo"; 
	}
	
	@RequestMapping(value="/add") 
    public String addClienteTipo(Model model) {
        model.addAttribute("clienteTipo", new ClienteTipo());
        return "clienteTipo/add";
    }
	
	@RequestMapping(value="/add", method=RequestMethod.POST) 
	public String processAddSubmit(@ModelAttribute("clienteTipo") ClienteTipo clienteTipo, BindingResult bindingResult) {  
	ClienteTipoValidator clientetipoValidator = new ClienteTipoValidator(); 
	clientetipoValidator.validate(clienteTipo, bindingResult);
     if (bindingResult.hasErrors()) 
            return "clienteTipo/add";
     clienteTipodao.addClienteTipo(clienteTipo);
     return "redirect:listarClientesTipo"; 
	}
	
	@RequestMapping(value="/update/{dni}", method = RequestMethod.GET) 
    public String editCliente(Model model, @PathVariable String dni) { 
        model.addAttribute("clienteTipo", clienteTipodao.getClienteTipo(dni));
        return "clienteTipo/update"; 
    }
	
 @RequestMapping(value="/update/{dni}", method = RequestMethod.POST) 
    public String processUpdateSubmit(@PathVariable String dni, 
                            @ModelAttribute("clienteTipo") ClienteTipo clienteTipo, 
                            BindingResult bindingResult) {
	 ClienteTipoValidator clientetipoValidator = new ClienteTipoValidator(); 
		clientetipoValidator.validate(clienteTipo, bindingResult);
         if (bindingResult.hasErrors()) 
             return "clienteTipo/update";
         clienteTipodao.updateClienteTipo(clienteTipo);
         return "redirect:../listarClientesTipo"; 
    }
	
	@RequestMapping(value="/delete/{dni}")
    public String processDelete(@PathVariable String dni) {
		clienteTipodao.deleteClienteTipo(dni);
           return "redirect:../listarClientesTipo"; 
    }
	
	@RequestMapping(value="/delete/{dni}", method=RequestMethod.POST) 
	public String processDeleteSubmit(@ModelAttribute("clienteTipo") ClienteTipo clienteTipo,
			BindingResult bindingResult) {  
     if (bindingResult.hasErrors()) 
            return "clienteTipo/delete";
     clienteTipodao.deleteClienteTipo(clienteTipo);
     return "redirect:../listarClientesTipo"; 
	}

}

