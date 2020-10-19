package com.sgpad;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sgpad.modelos.Usuario;
import com.sgpad.repositorios.RepositorioUsuario;

@Service
@Transactional
public class DetalhesUsuario implements UserDetailsService {
	
	@Autowired
	private RepositorioUsuario repositorioUsuario;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Usuario usuario = repositorioUsuario.findByEmail(email);
		
		if(usuario == null){
			throw new UsernameNotFoundException("Usuario não encontrado!");
		}
		return new User(usuario.getUsername(), usuario.getPassword(), true, true, true, true, usuario.getAuthorities());
	}
}