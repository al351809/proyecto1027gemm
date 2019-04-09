package es.uji.ei1027.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.uji.ei1027.dao.ReservaDao;
import es.uji.ei1027.model.Reserva;

@Controller	
@RequestMapping("/reserva")
public class ReservaController {
	
	private ReservaDao reservadao;
	@Autowired 
	public void setReservaDao(ReservaDao reservaDao) { 
	       this.reservadao=reservaDao;
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
     reservadao.addReserva(reserva);
     return "redirect:listarReservas"; 
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
         reservadao.updateReserva(reserva);
         return "redirect:../listarReservas"; 
    }
	
	@RequestMapping(value="/delete/{idReserva}")
    public String processDelete(@PathVariable Integer idReserva) {
		reservadao.deleteReserva(idReserva);
           return "redirect:../listarReservas"; 
    }
	
	@RequestMapping(value="/delete/{idReserva}", method=RequestMethod.POST) 
	public String processDeleteSubmit(@ModelAttribute("reserva") Reserva reserva,
			BindingResult bindingResult) {  
     if (bindingResult.hasErrors()) 
            return "reserva/delete";
     reservadao.deleteReserva(reserva);
     return "redirect:../listarReservas"; 
	}

}

