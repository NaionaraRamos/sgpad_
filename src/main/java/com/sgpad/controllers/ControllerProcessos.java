package com.sgpad.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import com.sgpad.modelos.Processo;
import com.sgpad.modelos.Usuario;
import com.sgpad.repositorios.RepositorioProcesso;
import com.sgpad.repositorios.RepositorioUsuario;

@Controller
public class ControllerProcessos {
	
	@Autowired private RepositorioUsuario repositorioUsuario;
	@Autowired private RepositorioProcesso repositorioProcesso;
	Processo processo;
	String numeroProcessoOriginal;
	
	@ModelAttribute("usuarioLogado")
	public Usuario usuarioLogado(Model model, @Valid Usuario usuario) {
		Authentication usuarioLogado = SecurityContextHolder.getContext().getAuthentication();
		String nomeUsuario = usuarioLogado.getName();
		usuario = repositorioUsuario.findByEmail(nomeUsuario);
		model.addAttribute("usuarioLogado", usuario);
		return usuario;
	}
	
	@GetMapping("/listar_processos")
	public ModelAndView listarProcessos(HttpServletRequest request, @Valid Usuario usuario) {
	     ModelAndView mv = new ModelAndView("listarProcessos");
	     mv.setViewName("processo/listar_processos");
	     
	     String emailUsuario = request.getUserPrincipal().getName();
	     usuario = repositorioUsuario.findByEmail(emailUsuario);
	     
	     Iterable <Processo> processos = repositorioProcesso.carregarTodosOsProcessos(usuario.getMatricula());
	     
	     mv.addObject("processo", processos);
	     return mv;
	}
	
	@GetMapping("/inserir_processo")
	public ModelAndView inserirProcesso() {
	     ModelAndView mv;
		
	     Iterable <Usuario> usuarios = repositorioUsuario.carregarUsuariosNaoExcluidos();
             mv = new ModelAndView("processo/inserir_processo", "usuarios", usuarios);
	     mv.addObject("processo", new Processo());
	     return mv;
	}
	
	public String getNumeroProcessoOriginal() { return numeroProcessoOriginal; }

	public void setNumeroProcessoOriginal(String numeroProcessoOriginal) { this.numeroProcessoOriginal = numeroProcessoOriginal; }

	@PostMapping("/inserir_processo")
	public ModelAndView inserirProcesso(@Valid Processo processo, BindingResult resultado) {
		ModelAndView mv = new ModelAndView();
		
		Processo proc = repositorioProcesso.findByNumeroProcesso(processo.getNumeroProcesso());
		//System.out.println("Data Publicação: " + processo.getDataPublicacaoUltimaPortaria());
		//System.out.println("Última Portaria: " + processo.getUltimaPortariaExpedida());

		
		if(proc != null) {
			resultado.rejectValue("numeroProcesso", "", "Já existe um processo cadastrado com esse número.");
		}
		
		if(resultado.hasErrors()) {
			
			mv.setViewName("processo/inserir_processo");
			mv.addObject("processo", processo);
		}else {
			repositorioProcesso.save(processo);
			mv.setViewName("redirect:/listar_processos");
		}
		return mv;
	}
	
	@GetMapping("/detalhar_processo/{id}")
	public ModelAndView detalharProcesso(@PathVariable("id") Long id) {
		ModelAndView mv = new ModelAndView();
		Processo processo = repositorioProcesso.getOne(id);
		mv.addObject("processo", processo);
		mv.setViewName("processo/detalhar_processo");
		return mv;
	}
	
	@GetMapping("/alterar_processo/{id}")
	public ModelAndView alterarProcesso(@PathVariable("id") Long id, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		
		Iterable<Usuario> usuarios = repositorioUsuario.carregarUsuariosNaoExcluidos();
		mv.addObject("usuarios", usuarios);
		
		Processo processo = repositorioProcesso.getOne(id);
		mv.addObject("processo", processo);
		mv.setViewName("processo/alterar_processo");
		
		numeroProcessoOriginal = processo.getNumeroProcesso();
		setNumeroProcessoOriginal(processo.getNumeroProcesso());
		//System.out.println(numeroProcessoOriginal);
		
		return mv;
	}

	@PostMapping("/alterar_processo/{id}")
	public ModelAndView alterarProcesso(@Valid Processo processo, BindingResult resultado, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		
		if(repositorioProcesso.carregarTodosOsProcessosMenosUm(numeroProcessoOriginal).contains(processo.getNumeroProcesso())) {
			resultado.rejectValue("numeroProcesso", "", "Já existe um processo cadastrado com esse número.");
		}
		
		if(resultado.hasErrors()) {
			mv.setViewName("processo/alterar_processo");
			mv.addObject("processo", processo);
			
		} else {
			repositorioProcesso.save(processo);
			mv.setViewName("redirect:/listar_processos");
		}
		return mv;
	}
}
