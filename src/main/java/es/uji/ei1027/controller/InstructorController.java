package es.uji.ei1027.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import es.uji.ei1027.dao.AcreditacionDao;
import es.uji.ei1027.dao.InstructorDao;
import es.uji.ei1027.dao.UsuarioDao;
import es.uji.ei1027.model.Acreditacion;
import es.uji.ei1027.model.DetallesUsuario;
import es.uji.ei1027.model.Instructor;

@Controller	
@RequestMapping("/instructor")
public class InstructorController {
	
	@Value("${upload.file.directory}")
	private String uploadDirectory;

	
	private InstructorDao instructordao;
	private UsuarioDao usuariodao;
	private AcreditacionDao acreditaciondao;
	@Autowired 
	public void setInstructorDao(InstructorDao instructorDao) { 
	       this.instructordao=instructorDao;
	   }
	
	@Autowired 
	public void setUsuarioDao(UsuarioDao usuarioDao) { 
	       this.usuariodao=usuarioDao;
	   }
	
	@Autowired 
	public void setAcreditacionDao(AcreditacionDao acreditacionDao) { 
	       this.acreditaciondao=acreditacionDao;
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
	    
	    session.setAttribute("nextUrl", null);
	   
	   return "instructor/listarInstructores"; 
	}
	
	@RequestMapping(value="/add") 
    public String addInstructor(Model model) {
        model.addAttribute("instructor", new Instructor());
        model.addAttribute("usuario", new DetallesUsuario());
        model.addAttribute("acreditacion", new Acreditacion());
        return "instructor/add";
    }
	
	@RequestMapping(value="/add", method=RequestMethod.POST) 
	public String processAddSubmit(@ModelAttribute("instructor") Instructor instructor, BindingResult bindingResult, @ModelAttribute("usuario") DetallesUsuario usuario, BindingResult bindingResult2, 
			@ModelAttribute("acreditacion") Acreditacion acreditacion, BindingResult bindingResult3, @RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes ) { 
	InstructorValidator instructorValidator = new InstructorValidator();
	UsuarioValidator usuarioValidator = new UsuarioValidator();
	instructorValidator.validate(instructor, bindingResult);
	usuarioValidator.validate(usuario, bindingResult2);
	
	//Gestionar el file de la acreditacion como pdf
	if (file.isEmpty()) {

        redirectAttributes.addFlashAttribute("message", 
                                         "Please select a file to upload");
        return "instructor/add";
    }

    try {
        // Obtener el fichero y guardarlo
        byte[] bytes = file.getBytes();
        Path path = Paths.get(uploadDirectory + "pdfs/" 
                                      + file.getOriginalFilename());
        Files.write(path, bytes);

        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded '" + path + "'");

    } catch (IOException e) {
        e.printStackTrace();
    }
	
	
	//-----------------------------------------------
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
    	instructor.setEstado("pendiente");
		instructordao.addInstructor(instructor);
		
		acreditacion.setDni(instructor.getDni());
		acreditacion.setEstado("pendiente");
		acreditacion.setCertificado(uploadDirectory + "pdfs/" + file.getOriginalFilename());
		acreditaciondao.addAcreditacion(acreditacion);
	} catch (DuplicateKeyException e) {
		bindingResult.rejectValue("dni", "obligatorio","El dni ya existe");
		usuariodao.deleteUsuario(usuario);
		return "instructor/add";
	}
     return "redirect:perfil"; 
	}
	
	@RequestMapping(value="/update/{dni}", method = RequestMethod.GET) 
    public String editInstructor(Model model, @PathVariable String dni) { 
        model.addAttribute("instructor", instructordao.getInstructor(dni));
        model.addAttribute("acreditacion", new Acreditacion());
        return "instructor/update"; 
    }
	
 @RequestMapping(value="/update/{dni}", method = RequestMethod.POST) 
    public String processUpdateSubmit(@PathVariable String dni, @ModelAttribute("instructor") Instructor instructor, 
                            BindingResult bindingResult, @ModelAttribute("acreditacion") Acreditacion acreditacion, BindingResult bindingResult2, @RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
	 	InstructorValidator instructorValidator = new InstructorValidator(); 
		instructorValidator.validate(instructor, bindingResult);
		//Gestionar el file de la acreditacion como pdf
		if (file.isEmpty()) {

	        redirectAttributes.addFlashAttribute("message", 
	                                         "No file");
	    }
		else {
		    try {
		        // Obtener el fichero y guardarlo
		        byte[] bytes = file.getBytes();
		        Path path = Paths.get(uploadDirectory + "pdfs/" 
		                                      + file.getOriginalFilename());
		        Files.write(path, bytes);
	
		        redirectAttributes.addFlashAttribute("message",
		                "You successfully uploaded '" + path + "'");
	
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
		    
		    acreditacion.setDni(instructor.getDni());
			acreditacion.setEstado("pendiente");
			acreditacion.setCertificado(uploadDirectory + "pdfs/" + file.getOriginalFilename());
			acreditaciondao.addAcreditacion(acreditacion);
		} 
	    //-------------------------------------------	
		
         if (bindingResult.hasErrors()) 
             return "instructor/update";
         try {
     		instructordao.updateInstructor(instructor);
     		
     		
     	} catch (DuplicateKeyException e) {
     		bindingResult.rejectValue("dni", "obligatorio","El dni ya existe");
     		return "instructor/update";
     	}
         return "redirect:../perfil"; 
    }
 
 @RequestMapping(value="/updateFoto/{dni}", method = RequestMethod.GET) 
 public String editInstructorFoto(Model model, @PathVariable String dni) { 
     model.addAttribute("instructor", instructordao.getInstructor(dni));
     model.addAttribute("acreditacion", new Acreditacion());
     return "instructor/updateFoto"; 
 }
 
 
 @RequestMapping(value="/updateFoto/{dni}", method = RequestMethod.POST) 
 public String processUpdateSubmitFoto(@PathVariable String dni, @ModelAttribute("instructor") Instructor instructor, 
                         BindingResult bindingResult, @ModelAttribute("acreditacion") Acreditacion acreditacion, BindingResult bindingResult2, @RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
	
		//Gestionar el file de la acreditacion como pdf
		if (file.isEmpty()) {

	        redirectAttributes.addFlashAttribute("message", 
	                                         "No file");
	    }
		else {
		    try {
		        // Obtener el fichero y guardarlo
		        byte[] bytes = file.getBytes();
		        Path path = Paths.get(uploadDirectory + "imagenes/" 
		                                      + file.getOriginalFilename());
		        Files.write(path, bytes);
	
		        redirectAttributes.addFlashAttribute("message",
		                "You successfully uploaded '" + path + "'");
	
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
		    
		    instructor.setFoto(uploadDirectory + "imagenes/" + file.getOriginalFilename());
			instructordao.updateFoto(instructor.getDni(), instructor.getFoto());
		} 
	    //-------------------------------------------	
		
      if (bindingResult.hasErrors()) 
          return "instructor/update";
  	
      return "redirect:../perfil"; 
 }
 
	 @RequestMapping(value="/updateEstado/{dni}/{estado}", method = RequestMethod.GET) 
	 public String editEstadoInstructor(Model model, @PathVariable String dni, @PathVariable String estado) { 
	     model.addAttribute("instructor", instructordao.getInstructor(dni));
	     model.addAttribute("estado", estado);
	     instructordao.updateEstado(dni, estado);
	     return "/correo/correoInstructorAdmin"; 
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
		if (session.getAttribute("user") == null){ 
	          model.addAttribute("user", new DetallesUsuario()); 
	          session.setAttribute("nextUrl", "instructor/perfil");
	          return "login";
	       }
		DetallesUsuario usuario = (DetallesUsuario) session.getAttribute("user");
		Instructor instructor = instructordao.getInstructorAlias(usuario.getUsuario());
		model.addAttribute("instructor", instructor );
        return "instructor/perfil"; 
    }

}
