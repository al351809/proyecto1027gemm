package es.uji.ei1027.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import es.uji.ei1027.dao.ImagenesDao;

@Controller	
@RequestMapping("/imagenes")
public class ImagenesController {
	
	private ImagenesDao imagenesdao;
	@Autowired 
	public void setImagenDao(ImagenesDao imagenesdao) { 
	       this.imagenesdao=imagenesdao;
	   }
	
	@RequestMapping("/listarImagenes/{nombre}")
	public String provaUnAcreditacion(@PathVariable String nombre, Model model) {
	   model.addAttribute("imagenes", imagenesdao.getImagenes(nombre));
	   return "imagenes/listarImagenes"; 
	}
}
