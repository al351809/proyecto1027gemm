package es.uji.ei1027.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.uji.ei1027.dao.InstructorDao;
import es.uji.ei1027.model.Instructor;

@Controller	
@RequestMapping("/instructor")
public class InstructorController {
	
	private InstructorDao instructordao;
	@Autowired 
	public void setClienteDao(InstructorDao instructorDao) { 
	       this.instructordao=instructorDao;
	   }
	
	@RequestMapping("/listarInstructores")
	public String pruevaUnInstructor(Model model) {
	   model.addAttribute("instructor", instructordao.getInstructor());
	   return "instructor/listarInstructores"; 
	}
	
	@RequestMapping(value="/add") 
    public String addInstructor(Model model) {
        model.addAttribute("instructor", new Instructor());
        return "instructor/add";
    }
	
	@RequestMapping(value="/add", method=RequestMethod.POST) 
	public String processAddSubmit(@ModelAttribute("instructor") Instructor instructor, BindingResult bindingResult) {  
     if (bindingResult.hasErrors()) 
            return "instructor/add";
     instructordao.addInstructor(instructor);
     return "redirect:listarInstructores"; 
	}
	
	@RequestMapping(value="/update/{dni}", method = RequestMethod.GET) 
    public String editCliente(Model model, @PathVariable String dni) { 
        model.addAttribute("instructor", instructordao.getInstructor(dni));
        return "instructor/update"; 
    }
	
 @RequestMapping(value="/update/{dni}", method = RequestMethod.POST) 
    public String processUpdateSubmit(@PathVariable String dni, 
                            @ModelAttribute("instructor") Instructor instructor, 
                            BindingResult bindingResult) {
         if (bindingResult.hasErrors()) 
             return "instructor/update";
         instructordao.updateInstructor(instructor);;
         return "redirect:../listarInstructores"; 
    }
	
	@RequestMapping(value="/delete/{dni}")
    public String processDelete(@PathVariable String dni) {
		instructordao.deleteInstructor(dni);
           return "redirect:../listarInstructores"; 
    }
	
	@RequestMapping(value="/delete/{dni}", method=RequestMethod.POST) 
	public String processDeleteSubmit(@ModelAttribute("instrutor") Instructor instructor,
			BindingResult bindingResult) {  
     if (bindingResult.hasErrors()) 
            return "instructor/delete";
     instructordao.deleteInstructor(instructor);
     return "redirect:../listarInstructores"; 
	}

}
