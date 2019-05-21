package es.uji.ei1027.controller;

import java.text.ParseException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.uji.ei1027.dao.ClienteDao;
import es.uji.ei1027.dao.UsuarioDao;
import es.uji.ei1027.model.Cliente;
import es.uji.ei1027.model.DetallesUsuario;

@Controller	
@RequestMapping("/cliente")
public class ClienteController {
	
	private ClienteDao clientedao;
	private UsuarioDao usuariodao;
	@Autowired 
	public void setClienteDao(ClienteDao clienteDao) { 
	       this.clientedao=clienteDao;
	   }
	@Autowired
	public void setUsuarioDao(UsuarioDao usuarioDao) {
		this.usuariodao = usuarioDao;
	}
	
	@RequestMapping("/listarClientes")
	public String pruevaUnCliente(HttpSession session, Model model) {
	   if (session.getAttribute("user") == null){ 
	          model.addAttribute("user", new DetallesUsuario()); 
	          session.setAttribute("nextUrl", "cliente/listarClientes");
	          return "login";
	   }
	   System.out.println(session.getAttribute("user"));
	   DetallesUsuario usuario = (DetallesUsuario) session.getAttribute("user");
	   System.out.println(usuario.getUsuario());

	   if (usuario.getRol().equals("admin"))
		   System.out.println("patata");
	   model.addAttribute("cliente", clientedao.getCliente());
	   return "cliente/listarClientes"; 
	}
	
	@RequestMapping(value="/add") 
    public String addCliente(Model model) {
        model.addAttribute("cliente", new Cliente());
        model.addAttribute("usuario", new DetallesUsuario());
        return "cliente/add";
    }
	
	@RequestMapping(value="/add", method=RequestMethod.POST) 
	public String processAddSubmit(@ModelAttribute("cliente") Cliente cliente, BindingResult bindingResult, @ModelAttribute("usuario") DetallesUsuario usuario,  BindingResult bindingResult2) {  
	ClienteValidator clienteValidator = new ClienteValidator(); 
	UsuarioValidator usuarioValidator = new UsuarioValidator();
	clienteValidator.validate(cliente, bindingResult);
	usuarioValidator.validate(usuario, bindingResult2);
     if (bindingResult.hasErrors() || bindingResult2.hasErrors()) 
            return "cliente/add";
     
     usuario.setRol("cliente");
	 try {
		usuariodao.addUsuario(usuario);
	} catch (DuplicateKeyException e1) {
		bindingResult2.rejectValue("usuario", "obligatorio","El usuario ya existe");
		return "cliente/add";
	}
     try {
		try {
			
			cliente.setAlias(usuario.getUsuario());
			clientedao.addCliente(cliente);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} catch (DuplicateKeyException e) {
		bindingResult.rejectValue("dni", "obligatorio","El dni ya existe");
		usuariodao.deleteUsuario(usuario);
		return "cliente/add";
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
	 	ClienteValidator clienteValidator = new ClienteValidator(); 
	 	clienteValidator.validate(cliente, bindingResult);
         if (bindingResult.hasErrors()) 
             return "cliente/update";
         try {
     		try {
     			clientedao.addCliente(cliente);
     		} catch (ParseException e) {
     			// TODO Auto-generated catch block
     			e.printStackTrace();
     		}
     	} catch (DuplicateKeyException e) {
     		bindingResult.rejectValue("dni", "obligatorio","El dni ya existe");
     		return "cliente/add";
     	}
         return "redirect:../listarClientes"; 
    }
	
	@RequestMapping(value="/delete/{dni}")
    public String processDelete(@PathVariable String dni) {
		DetallesUsuario usuarioCliente= usuariodao.getUsuario(clientedao.getCliente(dni).getAlias());
		clientedao.deleteCliente(dni);
		usuariodao.deleteUsuario(usuarioCliente);
        return "redirect:../listarClientes"; 
    }
	
	@RequestMapping(value="/delete/{dni}", method=RequestMethod.POST) 
	public String processDeleteSubmit(@ModelAttribute("cliente") Cliente cliente,
			BindingResult bindingResult) {  
     if (bindingResult.hasErrors()) 
            return "cliente/delete";
     DetallesUsuario usuarioCliente = usuariodao.getUsuario(cliente.getAlias());     
     clientedao.deleteCliente(cliente);
     usuariodao.deleteUsuario(usuarioCliente);
     return "redirect:../listarClientes"; 
	}

}
