package com.sgpad.modelos;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import org.springframework.security.core.GrantedAuthority;

@Entity
public class Role implements GrantedAuthority {
	
	private static final long serialVersionUID = 1L;

	@Id
	private Integer id;
	
	private String nomeRole;

	@ManyToMany(mappedBy = "roles")
    private List<Usuario> usuarios;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNomeRole() {
		return nomeRole;
	}

	public void setNomeRole(String nomeRole) {
		this.nomeRole = nomeRole;
	}
	
	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	@Override
	public String getAuthority() {
		return this.nomeRole;
	}
}