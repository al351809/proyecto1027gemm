package es.uji.ei1027.controller;

import java.text.ParseException;
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

import es.uji.ei1027.dao.ActividadDao;
import es.uji.ei1027.dao.ClienteDao;
import es.uji.ei1027.dao.InstructorDao;
import es.uji.ei1027.dao.ReservaDao;
import es.uji.ei1027.model.Actividad;
import es.uji.ei1027.model.Cliente;
import es.uji.ei1027.model.DetallesUsuario;
import es.uji.ei1027.model.Instructor;
import es.uji.ei1027.model.Reserva;

@Controller	
@RequestMapping("/reserva")
public class ReservaController {
	
	private ReservaDao reservadao;
	private ClienteDao clientedao;
	private ActividadDao actividaddao;
	private InstructorDao instructordao;
	
	@Autowired 
	public void setReservaDao(ReservaDao reservaDao, ClienteDao clientedao, ActividadDao actividaddao, InstructorDao instructordao) { 
	       this.reservadao=reservaDao;
	       this.clientedao=clientedao;
	       this.actividaddao=actividaddao;
	       this.instructordao=instructordao;
	   }
	
	@RequestMapping("/listarReservas")
	public String pruevaUnaReserva(Model model) {
	   model.addAttribute("reserva", reservadao.getReserva());
	   return "reserva/listarReservas"; 
	}
	
	@RequestMapping(value="/add") 
    public String addReserva(Model model) {
        model.addAttribute("reserva", new Reserva());
        return "reserva/add";
    }
	
	@RequestMapping(value="/add", method=RequestMethod.POST) 
	public String processAddSubmit(@ModelAttribute("reserva") Reserva reserva, BindingResult bindingResult) {  
     if (bindingResult.hasErrors()) 
            return "reserva/add";
     try {
		reservadao.addReserva(reserva);
	} catch (ParseException e) {
		e.printStackTrace();
	}
     return "redirect:listarReservas"; 
	}
	
	@RequestMapping(value="/add/{nombre}") 
    public String addReservaActividad(HttpSession session, @PathVariable String nombre, Model model) {
		DetallesUsuario usuario = (DetallesUsuario) session.getAttribute("user");
		Cliente cliente = clientedao.getClienteAlias(usuario.getUsuario());
		Actividad actividad = actividaddao.getActividad(nombre);
		model.addAttribute("dni", cliente.getDni());
		model.addAttribute("actividad", actividad);
        model.addAttribute("reserva", new Reserva());
        return "reserva/add";
    }
	
	@RequestMapping(value="/add/{nombre}", method=RequestMethod.POST) 
	public String processAddReservaActividadSubmit(HttpSession session, @PathVariable String nombre, @ModelAttribute("reserva") Reserva reserva, BindingResult bindingResult,  Model model) { 
		ReservaValidator reservaValidator = new ReservaValidator();
		reservaValidator.validate(reserva, bindingResult);
		Actividad actividad = actividaddao.getActividad(nombre);
		model.addAttribute("actividad", actividad);
		DetallesUsuario usuario = (DetallesUsuario) session.getAttribute("user");
		Cliente cliente = clientedao.getClienteAlias(usuario.getUsuario());
		model.addAttribute("dni", cliente.getDni());
		
     if (bindingResult.hasErrors()) 
            return "reserva/add";
     try {
    	usuario = (DetallesUsuario) session.getAttribute("user");
 		cliente = clientedao.getClienteAlias(usuario.getUsuario());
    	actividad = actividaddao.getActividad(nombre);
    	reserva.setDniCliente(cliente.getDni());
    	reserva.setEstadoPago("pendiente");
    	reserva.setNombreActividad(actividad.getNombre());
    	reserva.setPrecioPersona(Integer.parseInt(actividad.getPrecio()));
    	reserva.setFecha(actividad.getFecha());
		reservadao.addReserva(reserva);
	} catch (ParseException e) {
		e.printStackTrace();
	}
     return "redirect:../reservasCliente"; 
	}
	
	
	@RequestMapping(value="/update/{idReserva}", method = RequestMethod.GET) 
    public String editReserva(Model model, @PathVariable Integer idReserva) { 
        model.addAttribute("reserva", reservadao.getReserva(idReserva));
        return "reserva/update"; 
    }
	
 @RequestMapping(value="/update/{idReserva}", method = RequestMethod.POST) 
    public String processUpdateSubmit(@PathVariable Integer idReserva, 
                            @ModelAttribute("reserva") Reserva reserva, 
                            BindingResult bindingResult) {
         if (bindingResult.hasErrors()) 
             return "reserva/update";
         try {
        	 Reserva reservaVieja = reservadao.getReserva(idReserva);
        	 reserva.setDniCliente(reservaVieja.getDniCliente());
        	 reserva.setEstadoPago(reservaVieja.getEstadoPago());
        	 reserva.setPrecioPersona(reservaVieja.getPrecioPersona());
        	 reserva.setNombreActividad(reservaVieja.getNombreActividad());
			reservadao.updateReserva(reserva);
		} catch (ParseException e) {
			e.printStackTrace();
		}
         return "redirect:../reservasCliente"; 
    }
 
	 @RequestMapping(value="/updatePagado/{idReserva}", method = RequestMethod.GET) 
	 public String editReservaPagado(Model model, @PathVariable Integer idReserva) { 
		 Reserva reserva = reservadao.getReserva(idReserva);
		 reserva.setEstadoPago("pagado");
		 try {
			reservadao.updateReserva(reserva);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 List<Reserva> reservas = reservadao.getReservaNombre(reserva.getNombreActividad());
	     model.addAttribute("reserva", reservas);
	     return "correo/correoInstructorCliente"; 
	 }
	
	@RequestMapping(value="/delete/{idReserva}")
    public String processDelete(@PathVariable Integer idReserva) {
		reservadao.deleteReserva(idReserva);
           return "redirect:../reservasCliente"; 
    }
	
	@RequestMapping(value="/delete/{idReserva}", method=RequestMethod.POST) 
	public String processDeleteSubmit(@ModelAttribute("reserva") Reserva reserva,
			BindingResult bindingResult) {  
     if (bindingResult.hasErrors()) 
            return "reserva/delete";
     reservadao.deleteReserva(reserva);
     return "redirect:../reservasCliente"; 
	}
	
	@RequestMapping("/reservasCliente")
	public String pruebaReserva(HttpSession session, Model model) {
	   DetallesUsuario usuario = (DetallesUsuario) session.getAttribute("user");
	   String alias = usuario.getUsuario();
	   Cliente cliente = clientedao.getClienteAlias(alias);
	   model.addAttribute("reserva", reservadao.getReservaDni(cliente.getDni()));
	   return "reserva/reservasCliente"; 
	}
	
	@RequestMapping("/reservasActividadInstructor/{nombre}")
	public String reservasInstructor(@PathVariable String nombre, HttpSession session, Model model) {
	   List<Reserva> reservas = reservadao.getReservaNombre(nombre);
	   model.addAttribute("reserva", reservas);
	   return "reserva/reservasActividadInstructor"; 
	}
	
}

