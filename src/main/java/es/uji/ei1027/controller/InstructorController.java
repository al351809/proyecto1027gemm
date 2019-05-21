package es.uji.ei1027.controller;

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

import es.uji.ei1027.dao.InstructorDao;
import es.uji.ei1027.dao.UsuarioDao;
import es.uji.ei1027.model.DetallesUsuario;
import es.uji.ei1027.model.Instructor;

@Controller	
@RequestMapping("/instructor")
public class InstructorController {
	
	private InstructorDao instructordao;
	private UsuarioDao usuariodao;
	@Autowired 
	public void setInstructorDao(InstructorDao instructorDao) { 
	       this.instructordao=instructorDao;
	   }
	
	@Autowired 
	public void setUsuarioDao(UsuarioDao usuarioDao) { 
	       this.usuariodao=usuarioDao;
	   }
	
	@RequestMapping("/listarInstructores")
	public String pruevaUnInstructor(HttpSession session, Model model) {
		if (session.getAttribute("user") == null){ 
	          model.addAttribute("user", new DetallesUsuario()); 
	          session.setAttribute("nextUrl", "instructor/listarInstructores");
	          return "login";
	       }
		
		DetallesUsuario usuario = (DetallesUsuario) session.getAttribute("user");
	    model.addAttribute("instructor", instructordao.getInstructor());
	    
	    switch(usuario.getRol()) {
	    	case "admin":
	    		System.out.println("Soy una patata admin");break;
	    	case "instructor":
	    		System.out.println("Soy una patata instructor");break;
	    	case "cliente":
	    		System.out.println("Soy una patata cliente");break;
	    }
	    
	    session.setAttribute("nextUrl", null);
	   
	   return "instructor/listarInstructores"; 
	}
	
	@RequestMapping(value="/add") 
    public String addInstructor(Model model) {
        model.addAttribute("instructor", new Instructor());
        model.addAttribute("usuario", new DetallesUsuario());
        return "instructor/add";
    }
	
	@RequestMapping(value="/add", method=RequestMethod.POST) 
	public String processAddSubmit(@ModelAttribute("instructor") Instructor instructor, BindingResult bindingResult, @ModelAttribute("usuario") DetallesUsuario usuario, BindingResult bindingResult2) { 
	InstructorValidator instructorValidator = new InstructorValidator();
	UsuarioValidator usuarioValidator = new UsuarioValidator();
	instructorValidator.validate(instructor, bindingResult);
	usuarioValidator.validate(usuario, bindingResult2);
	
     if (bindingResult.hasErrors() || bindingResult2.hasErrors()) 
            return "instructor/add";
     
    usuario.setRol("instructor");
    
    
 	try {
		usuariodao.addUsuario(usuario);
	} catch (DuplicateKeyException e1) {
		bindingResult2.rejectValue("usuario", "obligatorio", "El usuario ya existe");
		return "instructor/add";
	}
 	
     
     try {
    	instructor.setAlias(usuario.getUsuario());
		instructordao.addInstructor(instructor);
	} catch (DuplicateKeyException e) {
		bindingResult.rejectValue("dni", "obligatorio","El dni ya existe");
		usuariodao.deleteUsuario(usuario);
		return "instructor/add";
	}
     return "redirect:listarInstructores"; 
	}
	
	@RequestMapping(value="/update/{dni}", method = RequestMethod.GET) 
    public String editInstructor(Model model, @PathVariable String dni) { 
        model.addAttribute("instructor", instructordao.getInstructor(dni));
        return "instructor/update"; 
    }
	
 @RequestMapping(value="/update/{dni}", method = RequestMethod.POST) 
    public String processUpdateSubmit(@PathVariable String dni, 
                            @ModelAttribute("instructor") Instructor instructor, 
                            BindingResult bindingResult) {
	 	InstructorValidator instructorValidator = new InstructorValidator(); 
		instructorValidator.validate(instructor, bindingResult);
         if (bindingResult.hasErrors()) 
             return "instructor/update";
         try {
     		instructordao.updateInstructor(instructor);
     	} catch (DuplicateKeyException e) {
     		bindingResult.rejectValue("dni", "obligatorio","El dni ya existe");
     		return "instructor/add";
     	}
         return "redirect:../perfil"; 
    }
 
	 @RequestMapping(value="/updateEstado/{dni}/{estado}", method = RequestMethod.GET) 
	 public String editEstadoInstructor(Model model, @PathVariable String dni, @PathVariable String estado) { 
	     model.addAttribute("instructor", instructordao.getInstructor(dni));
	     instructordao.updateEstado(dni, estado);
	     return "redirect:../../listarInstructores"; 
	 }
  
	
	@RequestMapping(value="/delete/{dni}")
    public String processDelete(@PathVariable String dni) {
		DetallesUsuario usuarioInstructor = usuariodao.getUsuario(instructordao.getInstructor(dni).getAlias());
		instructordao.deleteInstructor(dni);
		usuariodao.deleteUsuario(usuarioInstructor);
           return "redirect:../listarInstructores"; 
    }
	
	@RequestMapping(value="/delete/{dni}", method=RequestMethod.POST) 
	public String processDeleteSubmit(@ModelAttribute("instrutor") Instructor instructor,
			BindingResult bindingResult) {  
     if (bindingResult.hasErrors()) 
            return "instructor/delete";
     
     DetallesUsuario usuarioInstructor = usuariodao.getUsuario(instructor.getAlias());
     instructordao.deleteInstructor(instructor);
     usuariodao.deleteUsuario(usuarioInstructor);
     return "redirect:../listarInstructores"; 
	}
	
	@RequestMapping("/perfil")
    public String processPerfil(HttpSession session, Model model) {
		DetallesUsuario usuario = (DetallesUsuario) session.getAttribute("user");
		Instructor instructor = instructordao.getInstructorAlias(usuario.getUsuario());
		System.out.println(instructor);
		System.out.println(instructor.getDni());
		model.addAttribute("instructor", instructor );
        return "instructor/perfil"; 
    }

}
