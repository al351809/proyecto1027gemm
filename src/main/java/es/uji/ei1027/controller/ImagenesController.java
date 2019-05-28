package es.uji.ei1027.controller;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import es.uji.ei1027.dao.ImagenesDao;
import es.uji.ei1027.dao.InstructorDao;
import es.uji.ei1027.model.DetallesUsuario;
import es.uji.ei1027.model.Imagenes;
import es.uji.ei1027.model.Instructor;

@Controller	
@RequestMapping("/imagenes")
public class ImagenesController {
	
	private ImagenesDao imagenesdao;
	private InstructorDao instructordao;
	@Autowired 
	public void setImagenDao(ImagenesDao imagenesdao) { 
	       this.imagenesdao=imagenesdao;
	   }
	
	@Autowired 
	public void setInstructorDao(InstructorDao instructordao) { 
	       this.instructordao=instructordao;
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
	   List<Imagenes> imagen = imagenesdao.getImagenes(nombre);
	   List<String> imagenes = new LinkedList<String>();
	   for (Imagenes ima : imagen) {
		   imagenes.add(ima.getImagen());
	   }
	   model.addAttribute("imagenes", imagenes);
	   return "imagenes/listarImagenes"; 
	}
}
