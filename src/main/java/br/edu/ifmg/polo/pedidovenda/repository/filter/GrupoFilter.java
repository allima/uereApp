package br.edu.ifmg.polo.pedidovenda.repository.filter;

import java.io.Serializable;

import br.edu.ifmg.polo.pedidovenda.validation.SKU;

public class GrupoFilter implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String nome;

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id == null ? null : id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}