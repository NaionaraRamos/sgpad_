//package com.sgpad.controllers;
//
//import javax.validation.Valid;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.servlet.ModelAndView;
//
//import com.sgpad.modelos.Evento;
////import com.sgpad.repositorios.RepositorioEvento;
//
//@Controller
//public class ControllerEventos {
//	
//	//private RepositorioEvento repositorioEvento;
//	
//	@GetMapping("/agenda")
//	public String calendario() {
//		return "agenda/agenda";
//	}
//	
//	@GetMapping("/adicionar_evento")
//	public ModelAndView adicionarEvento() {
//		
//		ModelAndView mv = new ModelAndView();
//		mv.setViewName("agenda/adicionar_evento");
//		mv.addObject("evento", new Evento());
//		return mv;
//	}
//	
//	@PostMapping("/adicionar_evento")
//	public ModelAndView adicionarEvento(@Valid Evento evento, BindingResult resultado) {
//		
//		ModelAndView mv = new ModelAndView();
//		//repositorioEvento.findById(evento.getId());
//		
//		if(resultado.hasErrors()) {
//			mv.setViewName("agenda/adicionar_evento");
//			mv.addObject("evento", evento);
//		}else {
//			//repositorioEvento.save(evento);
//			mv.setViewName("redirect:/agenda");
//		}
//		return mv;
//	}
//}