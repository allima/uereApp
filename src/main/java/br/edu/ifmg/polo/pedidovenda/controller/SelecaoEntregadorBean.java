package br.edu.ifmg.polo.pedidovenda.controller;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import br.edu.ifmg.polo.pedidovenda.model.Usuario;
import br.edu.ifmg.polo.pedidovenda.repository.Usuarios;

@Named
@ViewScoped
public class SelecaoEntregadorBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Usuarios entregadores;
	
	private String nome;
	
	private List<Usuario> entregadoresFiltrados;
	
	public void pesquisar() {
		entregadoresFiltrados = entregadores.porNome(nome);
	}

	public void selecionar(Usuario usuario) {
		RequestContext.getCurrentInstance().closeDialog(usuario);
	}
	
	public void abrirDialogo() {
		Map<String, Object> opcoes = new HashMap<>();
		opcoes.put("modal", true);
		opcoes.put("resizable", false);
		opcoes.put("height", 470);
		
		RequestContext.getCurrentInstance().openDialog("/dialogos/SelecaoEntregador", opcoes, null);
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Usuario> getEntregadoresFiltrados() {
		return entregadoresFiltrados;
	}

}