
package com.sgpad.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ControllerHome {
	
	@GetMapping("/erro")
	public String erro() {
		return "404";
	}
	
	@GetMapping("/error")
	public String error() {
		return "404";
	}
	
	@GetMapping("/404")
	public String paginaInexistente() {
		return "404";
	}
	
	@GetMapping("/403")
	public String acessoNegado() {
		return "403";
	}
}