package com.sgpad.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.sgpad.modelos.Usuario;
import com.sgpad.repositorios.RepositorioUsuario;
import com.sgpad.servicos.ServicoNotificacao;
import com.sgpad.servicos.ServicoUsuario;

@Controller
public class ControllerUsuario {
	
	@Autowired RepositorioUsuario repositorioUsuario;
	@Autowired ServicoUsuario servicoUsuario;
	@Autowired ServicoNotificacao servicoNotificacao;
	@Autowired ControllerAdmin controllerAdmin;
	@Autowired private BCryptPasswordEncoder passwordEncoder;
	
	@GetMapping("/login")
	public String login() {
		return "usuario/login_usuario";
	}
	
	@GetMapping("/")
	public String index() {
		return "index";
	}
	
	@ModelAttribute("usuarioLogado")
	public Usuario usuarioLogado(Model model, @Valid Usuario usuario) {
		Authentication usuarioLogado = SecurityContextHolder.getContext().getAuthentication();
		String nomeUsuario = usuarioLogado.getName();
		usuario = repositorioUsuario.findByEmail(nomeUsuario);
		model.addAttribute("usuarioLogado", usuario);
		return usuario;
	}
	
	@GetMapping("/reportar_problema")
	public String reportarProblema() {
		return "usuario/reportar_problema";
	}
	
	@PostMapping("/reportar_problema")
	public ModelAndView reportarProblema(@RequestParam("assunto") String assunto, @RequestParam("problema") String problema, @Valid Usuario usuario, BindingResult resultado) {
		ModelAndView mv = new ModelAndView("usuario/reportar_problema");
		SimpleMailMessage email = new SimpleMailMessage();
		email.setTo("nramosmaceda@gmail.com");
		email.setSubject(assunto);
		email.setText(problema);
		servicoNotificacao.enviarNotificacao(email);
		mv.setViewName("redirect:/");
		return mv;
	}
	
	@GetMapping("/perfil/{matricula}")
	public ModelAndView perfil(@PathVariable("matricula") String matricula) {
		ModelAndView mv = new ModelAndView();
		Usuario usuario = repositorioUsuario.getOne(matricula);
		mv.addObject("usuario", usuario);
		mv.setViewName("usuario/perfil");
		return mv;
	}
	
	@GetMapping("/alterar_dados/{matricula}")
	public ModelAndView alterarDados(@PathVariable("matricula") String matricula) {
		ModelAndView mv = new ModelAndView("usuario/alterar_dados");
		Usuario usuario = repositorioUsuario.getOne(matricula);
		mv.addObject("usuario", usuario);
		mv.setViewName("usuario/alterar_dados");
		return mv;
	}
	
	@PostMapping("/alterar_dados/{matricula}")
	public ModelAndView alterarDados(@Valid Usuario usuario, BindingResult resultado, boolean acesso_admin) {
		ModelAndView mv = new ModelAndView();
		
		String matriculaUsuario = usuario.getMatricula();
		//int i = repositorioUsuario.checarSeEhAdmin(matriculaUsuario);
		String i = repositorioUsuario.checarSeEhAdmin(matriculaUsuario);
		if(resultado.hasErrors()) {
			
			mv.setViewName("usuario/alterar_dados");
			mv.addObject(usuario);
			
		} else {
			
			mv.setViewName("redirect:/perfil/{matricula}");
			System.out.println(usuario.getSenha());
			usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
			servicoUsuario.salvarMantendoEmail(usuario);
			repositorioUsuario.adicionarRoleUsuario(usuario.getMatricula());
			
			if(i == "1") {
			//if(i == 1) {
				repositorioUsuario.permitirAcessoDeAdministrador(matriculaUsuario);
			}
		}
		return mv;
	}
	
	@GetMapping("/recuperar_senha")
	public String recuperarSenha() {
		return "usuario/recuperar_senha";
	}
	
	@PostMapping("/recuperar_senha")
	public ModelAndView recuperarSenha(@RequestParam("email") String email, @Valid Usuario usuario, BindingResult resultado, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("usuario/recuperar_senha");
		
		if(!(repositorioUsuario.carregarEmails().contains(email))){
			resultado.rejectValue("email", "", "O email informado não foi encontrado na base de dados. Tente novamente.");
		}
		
		if(resultado.hasErrors()) {
			mv.setViewName("usuario/recuperar_senha");
			mv.addObject("usuario", usuario);
			
		} else {
			if(repositorioUsuario.carregarEmails().contains(email)) {
				
				enviarEmail(usuario, email, request);
				mv.setViewName("redirect:/login");
		 }
		}
		return mv;
	}
	
	public void enviarEmail(@Valid Usuario usuario, @RequestParam("email") String email, HttpServletRequest request) {
		
		String emailUsuario = repositorioUsuario.findByEmail(email).getEmail();
		String nomeUsuario = repositorioUsuario.findByEmail(email).getNome();
		String matriculaUsuario = repositorioUsuario.findByEmail(email).getMatricula();
		//int i = repositorioUsuario.checarSeEhAdmin(matriculaUsuario);
		String i = repositorioUsuario.checarSeEhAdmin(matriculaUsuario);
		//System.out.println("i: " + i);
		String senha = servicoUsuario.gerarSenha(usuario);
		usuario.setEmail(emailUsuario);
		usuario.setMatricula(matriculaUsuario);
		usuario.setNome(nomeUsuario);
		usuario.setSenha(passwordEncoder.encode(senha));
		repositorioUsuario.save(usuario);
		repositorioUsuario.adicionarRoleUsuario(matriculaUsuario);
		
		if(i == "1") {
		//if(i == 1) {
			repositorioUsuario.permitirAcessoDeAdministrador(matriculaUsuario);
		}
		
		String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/login";
		 
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(emailUsuario);
		mail.setSubject("Recuperação de senha");
		mail.setText("Uma senha de recuperação foi solicitada para o usuário " + nomeUsuario + " e matrícula " + matriculaUsuario + ". Utilize as credenciais para um novo login:\n\n\nEmail: " + emailUsuario + "\nSenha: " + senha + ".\n\nFaça login por aqui: " + url);
		servicoNotificacao.enviarNotificacao(mail);
	}
}