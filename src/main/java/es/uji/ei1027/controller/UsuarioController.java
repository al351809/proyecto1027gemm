package es.uji.ei1027.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Controller; 
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import es.uji.ei1027.dao.UsuarioDao;
import es.uji.ei1027.model.DetallesUsuario; 



@Controller 
@RequestMapping("/user") 
public class UsuarioController {
   private UsuarioDao usuarioDao;

   @Autowired 
   public void setSociDao(UsuarioDao usuarioDao) {
       this.usuarioDao = usuarioDao;
   }
  
   @RequestMapping("/list") 
   public String listSocis(HttpSession session, Model model) {
       if (session.getAttribute("user") == null) 
       { 
          model.addAttribute("user", new DetallesUsuario()); 
          session.setAttribute("nextUrl", "user/list");
          return "login";
       } 
       model.addAttribute("users", usuarioDao.listAllUsers());
       return "user/list";
   }
}

