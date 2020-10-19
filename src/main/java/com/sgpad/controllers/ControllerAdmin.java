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
import org.springframework.web.servlet.ModelAndView;
import com.sgpad.modelos.Processo;
import com.sgpad.modelos.Usuario;
import com.sgpad.repositorios.RepositorioProcesso;
import com.sgpad.repositorios.RepositorioUsuario;
import com.sgpad.servicos.ServicoNotificacao;
import com.sgpad.servicos.ServicoUsuario;

@Controller
public class ControllerAdmin {
	
	@Autowired private RepositorioUsuario repositorioUsuario;
	@Autowired private RepositorioProcesso repositorioProcesso;
	@Autowired private BCryptPasswordEncoder passwordEncoder;
	boolean boolean_alterar;
	String primeiroEmail;
	String matricula;
	@Autowired private ServicoNotificacao servicoNotificacao;
	@Autowired private ServicoUsuario servicoUsuario;
	String numeroProcessoOriginal;
	
	@ModelAttribute("usuarioLogado")
	public Usuario usuarioLogado(Model model, @Valid Usuario usuario) {
		Authentication usuarioLogado = SecurityContextHolder.getContext().getAuthentication();
		String nomeUsuario = usuarioLogado.getName();
		usuario = repositorioUsuario.findByEmail(nomeUsuario);
		model.addAttribute("usuarioLogado", usuario);
		return usuario;
	}
	
	@GetMapping("/area_admin")
	public String areaAdmin() {
		return "admin/area_admin";
	}
	
	@GetMapping("/listar_usuarios")
	public ModelAndView listarUsuarios(HttpServletRequest request) {
		 ModelAndView mv = new ModelAndView("admin/listar_usuarios");
	     Iterable <Usuario> usuarios = repositorioUsuario.carregarUsuariosNaoExcluidos();
	     mv.addObject("usuario", usuarios);	
	     return mv;
	}

	@GetMapping("/adicionar_usuario")
	public ModelAndView adicionarUsuario() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("admin/adicionar_usuario");
		mv.addObject("usuario", new Usuario());
		return mv;
	}
	
	@PostMapping("/adicionar_usuario")
	public ModelAndView adicionarUsuario(@Valid Usuario usuario, BindingResult resultado, HttpServletRequest request, boolean acesso_admin) {
	    
		ModelAndView mv = new ModelAndView();
		Usuario usrEmail = repositorioUsuario.findByEmail(usuario.getEmail());
		Usuario usrMatricula = repositorioUsuario.findByMatricula(usuario.getMatricula());
		
		if((usrMatricula != null) && (usrEmail == null)) {
			resultado.rejectValue("matricula", "", "Já existe um usuário cadastrado com esse número de matrícula.");
		
		}else if((usrMatricula == null) && (usrEmail != null)) {	
			resultado.rejectValue("email", "", "Já existe um usuário cadastrado com esse endereço de email.");
			
		}else if((usrMatricula != null) && (usrEmail != null)) {	
			resultado.rejectValue("email", "", "Já existe um usuário cadastrado com esse endereço de email.");
			resultado.rejectValue("matricula", "", "Já existe um usuário cadastrado com esse número de matrícula.");
		}
		
		String regex = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
		if(!(usuario.getEmail().matches(regex))) {
			resultado.rejectValue("email", "", "Este endereço de email não é válido.");
		}
		
		if(resultado.hasErrors()) {
			mv.setViewName("admin/adicionar_usuario");
			mv.addObject("usuario", usuario);
			
		}else {		
			try{
				enviarEmail(usuario, request);
				repositorioUsuario.save(usuario);
				repositorioUsuario.adicionarRoleUsuario(usuario.getMatricula());
				permitirAcessoAdmin(usuario, acesso_admin);
				mv.setViewName("redirect:/listar_usuarios");
				
			} catch(Exception ex){
				System.out.println("Não foi possível adicionar");
			}
	    }
		return mv;
	}
	
	public void enviarEmail(@Valid Usuario usuario, HttpServletRequest request) {
		SimpleMailMessage email = new SimpleMailMessage();
		email.setTo(usuario.getEmail());
		String senha = servicoUsuario.gerarSenha(usuario);
		String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/login";
		email.setSubject("Inserção");
		System.out.println("servicoUsuario.getGeneratedString(): " + servicoUsuario.getGeneratedString());
		email.setText("Você, "+ usuario.getNome() + ", acabou de ser adicionado ao SGPAD!. Utilize as seguintes credenciais para realizar o login:\n\n\nEmail: " + usuario.getEmail() + "\nSenha: " + senha + ".\n\nFaça login por aqui: " + url);
		servicoNotificacao.enviarNotificacao(email);
		
		usuario.setSenha(passwordEncoder.encode(senha));
		repositorioUsuario.save(usuario);
	}
	
	public void setPrimeiroEmail(String primeiroEmail) {
		this.primeiroEmail = primeiroEmail;
	}

	public void permitirAcessoAdmin(@Valid Usuario usuario, boolean acesso_admin) {
		if(acesso_admin) {	
			repositorioUsuario.permitirAcessoDeAdministrador(usuario.getMatricula());
		}
	}
	
	@GetMapping("/alterar_dados_usuario/{matricula}")
	public ModelAndView alterarDadosUsuario(@PathVariable("matricula") String matricula) {
		ModelAndView mv = new ModelAndView("admin/alterar_dados_usuario");
		Usuario usuario = repositorioUsuario.getOne(matricula);
		mv.addObject("usuario", usuario);
		mv.setViewName("admin/alterar_dados_usuario");
		primeiroEmail = usuario.getEmail();
		setPrimeiroEmail(usuario.getEmail());
		
	//	int t = repositorioUsuario.checarSeEhAdmin(usuario.getMatricula());
		String t = repositorioUsuario.checarSeEhAdmin(usuario.getMatricula());//no heroku...
		
	//	System.out.println("Valor da String: " + t);
		
		//if(t == 0)
		if(t == "0")//no heroku...
		{
			boolean_alterar = false;
			
		} else 
			if(t == "1")//no heroku...
			{
			boolean_alterar = true;
		}
		
//		String booleanEmString = "" + isBoolean_alterar();
//		mv.addObject("booleanEmString", booleanEmString);
		
		System.out.println("booleanEmString: " + t);//no heroku...
		mv.addObject("booleanEmString", t);//no heroku...
		
		return mv;
	}
	
	@PostMapping("/alterar_dados_usuario/{matricula}")
	public ModelAndView alterarDadosUsuario(@Valid Usuario usuario, HttpServletRequest request, BindingResult resultado, boolean boolean_alterar) {
		ModelAndView mv = new ModelAndView();
		boolean existe = repositorioUsuario.carregarEmails().contains(usuario.getEmail());
		
		if(repositorioUsuario.carregarEmailsMenosUm(primeiroEmail).contains(usuario.getEmail())) {
			resultado.rejectValue("email", "", "Já existe um usuário cadastrado com esse email.");
		}
		
		String regex = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
		if(!(usuario.getEmail().matches(regex))) {
			resultado.rejectValue("email", "", "Este endereço não é válido.");
		}
		
		if(resultado.hasErrors()) {
			mv.setViewName("admin/alterar_dados_usuario");
			mv.addObject("usuario", usuario);
			
		} else {
			if(existe == true){
				try {
					servicoUsuario.salvarMantendoASenha(usuario);
				} catch (Exception e) {
					mv.setViewName("erro");
				}
				
			} else {
				try {
					enviarEmail(usuario, request);
					servicoUsuario.salvar(usuario);
				} catch (Exception e) {
					mv.setViewName("erro");
				}
			}
			
			repositorioUsuario.adicionarRoleUsuario(usuario.getMatricula());
			permitirAcessoAdmin(usuario, boolean_alterar);
			mv.setViewName("redirect:/listar_usuarios");
		}
		return mv;
	}
		
	public boolean isBoolean_alterar() {
		return boolean_alterar;
	}
	
	@GetMapping("/excluir_usuario/{matricula}")
	public String excluirUsuario(@PathVariable("matricula") String matricula) {
		try {
			repositorioUsuario.deletarUsuario(matricula);
			repositorioUsuario.deletarRoleUsuario(matricula);
			return "redirect:/listar_usuarios";
			
		}catch(Exception ex) {
			return "error";
		}
	}
	
	@GetMapping("/listar_todos_os_processos")
	public ModelAndView listarTodosOsProcessos(HttpServletRequest request) {
		 ModelAndView mv = new ModelAndView("listar_todos_os_processos");
	     mv.setViewName("admin/listar_todos_os_processos");
	     Iterable <Processo> processos = repositorioProcesso.findAll();
	     mv.addObject("processo", processos);
	     return mv;
	}
	
	@GetMapping("/detalhar_processo_1/{id}")
	public ModelAndView detalharProcesso(@PathVariable("id") Long id) {
		ModelAndView mv = new ModelAndView();
		Processo processo = repositorioProcesso.getOne(id);
		mv.addObject("processo", processo);
		mv.setViewName("admin/detalhar_processo");
		return mv;
	}
	
	@GetMapping("/alterar_processo_1/{id}")
	public ModelAndView alterarProcesso(@PathVariable("id") Long id, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		
		Iterable<Usuario> usuarios = repositorioUsuario.carregarUsuariosNaoExcluidos();
		mv.addObject("usuarios", usuarios);
		
		Processo processo = repositorioProcesso.getOne(id);
		mv.addObject("processo", processo);
		mv.setViewName("admin/alterar_processo");
		
		numeroProcessoOriginal = processo.getNumeroProcesso();
		setNumeroProcessoOriginal(processo.getNumeroProcesso());
		System.out.println(numeroProcessoOriginal);
		
		return mv;
	}
	
	public String getNumeroProcessoOriginal() { return numeroProcessoOriginal; }

	public void setNumeroProcessoOriginal(String numeroProcessoOriginal) { this.numeroProcessoOriginal = numeroProcessoOriginal; }

	@PostMapping("/alterar_processo_1/{numero_processo}")
	public ModelAndView alterarProcesso(@Valid Processo processo, BindingResult resultado, HttpServletRequest request) {

		ModelAndView mv = new ModelAndView();
		
		if(repositorioProcesso.carregarTodosOsProcessosMenosUm(numeroProcessoOriginal).contains(processo.getNumeroProcesso())) {
			resultado.rejectValue("numeroProcesso", "", "Já existe um processo cadastrado com esse número.");
		}
		
		if(resultado.hasErrors()) {
			mv.setViewName("admin/alterar_processo");
			mv.addObject("processo", processo);
			
		} else {
			repositorioProcesso.save(processo);
			mv.setViewName("redirect:/listar_todos_os_processos");
		}
		return mv;
	}
	
	@GetMapping("/excluir_processo/{numero_processo}")
	public String excluirProcesso(@PathVariable("numero_processo") Long id) {
		try {
			repositorioProcesso.deleteById(id);
			return "redirect:/listar_todos_os_processos";
		}catch(Exception ex) {
			return "error";
		}
	}
}
