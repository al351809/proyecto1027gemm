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

import es.uji.ei1027.dao.ClienteDao;
import es.uji.ei1027.model.Cliente;

@Controller	
@RequestMapping("/cliente")
public class ClienteController {
	
	private ClienteDao clientedao;
	@Autowired 
	public void setClienteDao(ClienteDao clienteDao) { 
	       this.clientedao=clienteDao;
	   }
	
	@RequestMapping("/listarClientes")
	public String pruevaUnCliente(Model model) {
	   model.addAttribute("cliente", clientedao.getCliente());
	   return "cliente/listarClientes"; 
	}
	
	@RequestMapping(value="/add") 
    public String addCliente(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "cliente/add";
    }
	
	@RequestMapping(value="/add", method=RequestMethod.POST) 
	public String processAddSubmit(@ModelAttribute("cliente") Cliente cliente, BindingResult bindingResult) {  
	ClienteValidator clienteValidator = new ClienteValidator(); 
	clienteValidator.validate(cliente, bindingResult);
     if (bindingResult.hasErrors()) 
            return "cliente/add";
     try {
		clientedao.addCliente(cliente);
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
     return "redirect:listarClientes"; 
	}
	
	@RequestMapping(value="/update/{dni}", method = RequestMethod.GET) 
    public String editCliente(Model model, @PathVariable String dni) { 
        model.addAttribute("cliente", clientedao.getCliente(dni));
        return "cliente/update"; 
    }
	
 @RequestMapping(value="/update/{dni}", method = RequestMethod.POST) 
    public String processUpdateSubmit(@PathVariable String dni, 
                            @ModelAttribute("cliente") Cliente cliente, 
                            BindingResult bindingResult) {
         if (bindingResult.hasErrors()) 
             return "cliente/update";
         try {
			clientedao.updateCliente(cliente);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         return "redirect:../listarClientes"; 
    }
	
	@RequestMapping(value="/delete/{dni}")
    public String processDelete(@PathVariable String dni) {
		clientedao.deleteCliente(dni);
           return "redirect:../listarClientes"; 
    }
	
	@RequestMapping(value="/delete/{dni}", method=RequestMethod.POST) 
	public String processDeleteSubmit(@ModelAttribute("cliente") Cliente cliente,
			BindingResult bindingResult) {  
     if (bindingResult.hasErrors()) 
            return "cliente/delete";
     clientedao.deleteCliente(cliente);
     return "redirect:../listarClientes"; 
	}

}
