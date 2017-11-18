package br.edu.ifmg.polo.pedidovenda.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.edu.ifmg.polo.pedidovenda.model.Categoria;
import br.edu.ifmg.polo.pedidovenda.service.CadastroCategoriaService;
import br.edu.ifmg.polo.pedidovenda.service.NegocioException;
import br.edu.ifmg.polo.pedidovenda.util.jsf.FacesUtil;

@Named
@ViewScoped
public class CadastroCategoriaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	
	@Inject
	private CadastroCategoriaService cadastroCategoriaService;
	
	private Categoria categoria;
	private List<Categoria> categoriasDisponiveis;
	
	public CadastroCategoriaBean() {
		limpar();
	}
	
	public void inicializar() {
		if (this.categoria == null) {
			limpar();
		}
		//todas as categorias menos a atual
		carregarCategoriasDisponiveis(); 
		
	}
	
	public void carregarCategoriasDisponiveis() {		
		categoriasDisponiveis = cadastroCategoriaService.categoriasDisponiveis(categoria);
	}
	
	
	
	private void limpar() {
		categoria = new Categoria();
	}
	
	public void salvar() {
		try {
			this.categoria = cadastroCategoriaService.salvar(this.categoria);
			limpar();
			
			FacesUtil.addInfoMessage("Categoria salva com sucesso!");
		} catch (NegocioException ne) {
			FacesUtil.addErrorMessage(ne.getMessage());
		}
	}

	public Categoria getCategoria() {
		return categoria;
	}
	
	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	
	public List<Categoria> getCategoriasDisponiveis() {
		return categoriasDisponiveis;
	}

	public void setCategoriasDisponiveis(List<Categoria> categoriasDisponiveis) {
		this.categoriasDisponiveis = categoriasDisponiveis;
	}

	public boolean isEditando() {
		return this.categoria.getId() != null;
	}
	
	
}