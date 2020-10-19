//package com.sgpad.modelos;
//
//import javax.persistence.Entity;
//import javax.persistence.Id;
//import javax.persistence.OneToOne;
//import javax.persistence.Table;
//
//import org.joda.time.LocalDate;
//import org.joda.time.LocalTime;
//
//@Entity
//@Table(name="evento")
//public class Evento {
//	
//	@Id
//	private Long id;
//	
//	private String titulo;
//	
//	private String descricao;
//	
//	private LocalDate data;
//	
//	private LocalTime hora;
//	
//	@OneToOne
//	private Processo numeroProcesso;
//
//	public Long getId() {
//		return id;
//	}
//
//	public void setId(Long id) {
//		this.id = id;
//	}
//
//	public String getTitulo() {
//		return titulo;
//	}
//
//	public void setTitulo(String titulo) {
//		this.titulo = titulo;
//	}
//
//	public String getDescricao() {
//		return descricao;
//	}
//
//	public void setDescricao(String descricao) {
//		this.descricao = descricao;
//	}
//
//	public LocalDate getData() {
//		return data;
//	}
//
//	public void setData(LocalDate data) {
//		this.data = data;
//	}
//
//	public LocalTime getHora() {
//		return hora;
//	}
//
//	public void setHora(LocalTime hora) {
//		this.hora = hora;
//	}
//
//	public Processo getNumeroProcesso() {
//		return numeroProcesso;
//	}
//
//	public void setNumeroProcesso(Processo numeroProcesso) {
//		this.numeroProcesso = numeroProcesso;
//	}
//}