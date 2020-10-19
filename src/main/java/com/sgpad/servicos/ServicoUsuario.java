package com.sgpad.servicos;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.sgpad.modelos.Usuario;
import com.sgpad.repositorios.RepositorioUsuario;

@Service
public class ServicoUsuario {
	
	@Autowired private RepositorioUsuario repositorioUsuario;
	
	//@Autowired private BCryptPasswordEncoder passwordEncoder;
	
	String generatedString;

	public Usuario encontrarPorEmail(String email) {
		return repositorioUsuario.findByEmail(email);
	}
	
	public String getGeneratedString() {
		return generatedString;
	}

	public void setGeneratedString(String generatedString) {
		this.generatedString = generatedString;
	}

	public void salvar(Usuario usuario) {
		//usuario.setSenha(passwordEncoder.encode(gerarSenha(usuario)));
		repositorioUsuario.save(usuario);
	}
	
	public void salvarMantendoEmail(Usuario usuario) {
		String email = repositorioUsuario.email(usuario.getMatricula());
		System.out.println("email: " + email);
		usuario.setEmail(email);
		repositorioUsuario.save(usuario);
	}
	
	public void salvarMantendoASenha(Usuario usuario) {
		String senha = repositorioUsuario.senha(usuario.getMatricula());
		usuario.setSenha(senha);
		repositorioUsuario.save(usuario);
	}
	
	public String gerarSenha(Usuario usuario) {
		generatedString = RandomStringUtils.randomAlphanumeric(6);
		usuario.setSenha(generatedString);
		System.out.println("generatedString: " + generatedString);
		System.out.println("getGeneratedString: " + getGeneratedString());
		return generatedString;
	}
}