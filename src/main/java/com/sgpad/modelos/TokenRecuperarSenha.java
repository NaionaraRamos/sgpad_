//package com.sgpad.modelos;
//
//import java.util.Date;
//import javax.persistence.Entity;
//import javax.persistence.FetchType;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.OneToOne;
//
//@Entity
//public class TokenRecuperarSenha {
//	
//	private static final int expiracao = 60 * 24;
//	
//	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
//	private Long id;
//	
//	private String token;
//	
//	@OneToOne(targetEntity = Usuario.class, fetch = FetchType.EAGER)
//	@JoinColumn(nullable = false, name = "usuario_matricula")
//	private Usuario usuario;
//	
//	private Date dataExpiracao;
//
//	public Long getId() {
//		return id;
//	}
//
//	public void setId(Long id) {
//		this.id = id;
//	}
//
//	public String getToken() {
//		return token;
//	}
//
//	public void setToken(String token) {
//		this.token = token;
//	}
//
//	public Usuario getUsuario() {
//		return usuario;
//	}
//
//	public void setUsuario(Usuario usuario) {
//		this.usuario = usuario;
//	}
//
//	public Date getDataExpiracao() {
//		return dataExpiracao;
//	}
//
//	public void setDataExpiracao(Date dataExpiracao) {
//		this.dataExpiracao = dataExpiracao;
//	}
//
//	public static int getExpiracao() {
//		return expiracao;
//	}
//}