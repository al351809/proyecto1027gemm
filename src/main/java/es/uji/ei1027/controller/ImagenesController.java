package es.uji.ei1027.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import es.uji.ei1027.dao.ActividadDao;
import es.uji.ei1027.dao.ImagenesDao;
import es.uji.ei1027.dao.InstructorDao;
import es.uji.ei1027.model.Acreditacion;
import es.uji.ei1027.model.Actividad;
import es.uji.ei1027.model.DetallesUsuario;
import es.uji.ei1027.model.Imagenes;
import es.uji.ei1027.model.Instructor;

@Controller	
@RequestMapping("/imagenes")
public class ImagenesController {
	
	@Value("${upload.file.directory}")
	private String uploadDirectory;
	
	
	private ImagenesDao imagenesdao;
	private InstructorDao instructordao;
	private ActividadDao actividaddao;
	
	@Autowired 
	public void setImagenDao(ImagenesDao imagenesdao) { 
	       this.imagenesdao=imagenesdao;
	   }
	
	@Autowired 
	public void setInstructorDao(InstructorDao instructordao) { 
	       this.instructordao=instructordao;
	   }
	
	@Autowired 
	public void setActividadDao(ActividadDao actividaddao) { 
	       this.actividaddao=actividaddao;
	   }
	
	@RequestMapping("/listarImagenes/{nombre}")
	public String provaUnAcreditacion(HttpSession session, @PathVariable String nombre, Model model) {
	   //model.addAttribute("imagenes", imagenesdao.getImagenes(nombre));
		DetallesUsuario usuario = (DetallesUsuario) session.getAttribute("user");
		Instructor instructor;

		if(usuario.getRol().equals("instructor")) {
			instructor = instructordao.getInstructorAlias(usuario.getUsuario());
			model.addAttribute("instructor", instructor);
		
		}
		Actividad actividad = actividaddao.getActividad(nombre);
		model.addAttribute("actividad", actividad);
	   List<Imagenes> imagen = imagenesdao.getImagenes(nombre);
	   List<String> imagenes = new LinkedList<String>();
	   for (Imagenes ima : imagen) {
		   imagenes.add(ima.getImagen());
	   }
	   model.addAttribute("imagenes", imagenes);
	   return "imagenes/listarImagenes"; 
	}
	

	@RequestMapping("/listarImagenes")
	public String provaUnImagen(HttpSession session, @ModelAttribute("actividad") Actividad actividad, Model model) {
	   //model.addAttribute("imagenes", imagenesdao.getImagenes(nombre));
		DetallesUsuario usuario = (DetallesUsuario) session.getAttribute("user");
		Instructor instructor;

		if(usuario.getRol().equals("instructor")) {
			instructor = instructordao.getInstructorAlias(usuario.getUsuario());
			model.addAttribute("instructor", instructor);
		
		}
		
	  String nombre = (String) session.getAttribute("nombre");
	   List<Imagenes> imagen = imagenesdao.getImagenes(nombre);
	   List<String> imagenes = new LinkedList<String>();
	   for (Imagenes ima : imagen) {
		   imagenes.add(ima.getImagen());
	   }
	   
	   model.addAttribute("imagenes", imagenes);
	   return "imagenes/listarImagenes"; 
	}
	
	@RequestMapping(value="/update/{nombre}", method = RequestMethod.GET) 
    public String editImagen(HttpSession session, Model model, @PathVariable String nombre) { 
        model.addAttribute("actividad", actividaddao.getActividad(nombre));
        model.addAttribute("imagenes", new Imagenes());
        model.addAttribute("usuario", new DetallesUsuario());
        return "imagenes/update"; 
    }
	
	@RequestMapping(value="/update/{nombre}", method=RequestMethod.POST) 
	public String processUpdateSubmit(@ModelAttribute("actividad") Actividad actividad, BindingResult bindingResult, @ModelAttribute("usuario") DetallesUsuario usuario, BindingResult bindingResult2, 
			@ModelAttribute("imagenes") Imagenes imagenes, BindingResult bindingResult3, @RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes, HttpSession session, Model model ) { 
	
	//Gestionar el file de la acreditacion como pdf
	if (file.isEmpty()) {

        redirectAttributes.addFlashAttribute("message", 
                                         "Please select a file to upload");
        return "imagenes/update";
    }

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
	
	
	//-----------------------------------------------
     if (bindingResult.hasErrors() || bindingResult2.hasErrors()) 
            return "imagenes/update";
     
    usuario.setRol("instructor");
 	model.addAttribute("actividad", actividad);
 	session.setAttribute("nombre", actividad.getNombre());
 
    imagenes.setImagen(uploadDirectory + "imagenes/" + file.getOriginalFilename());
    imagenes.setNombre(actividad.getNombre());
    
	try {
		imagenesdao.addImagen(imagenes);
	} catch (DuplicateKeyException e) {
		return "imagenes/avisoImagen";
	}
		

     return "redirect:../listarImagenes"; 
	}
}
