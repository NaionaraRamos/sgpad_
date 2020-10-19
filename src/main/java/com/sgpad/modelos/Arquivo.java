package com.sgpad.modelos;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "arquivo")
public class Arquivo {
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;
	
	private String nome;
	
	private String tipo;
	
	@Lob
	private byte[] dados;
	
	public Arquivo() {}
	
	public Arquivo(String nome, String tipo, byte[] dados) {
		this.nome = nome;
		this.tipo = tipo;
		this.dados = dados;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public byte[] getDados() {
		return dados;
	}

	public void setDados(byte[] dados) {
		this.dados = dados;
	}
}