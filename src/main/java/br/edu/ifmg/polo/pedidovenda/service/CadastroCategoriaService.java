package br.edu.ifmg.polo.pedidovenda.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import br.edu.ifmg.polo.pedidovenda.model.Categoria;
import br.edu.ifmg.polo.pedidovenda.repository.Categorias;
import br.edu.ifmg.polo.pedidovenda.util.jpa.Transactional;

public class CadastroCategoriaService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Categorias categorias;
	
	@Transactional
	public Categoria salvar(Categoria categoria) throws NegocioException {
		
		//Categoria categoriaExistente = null;
		//if (categoria.getId() > 0)
		// categoriaExistente = categorias.porId(categoria.getId());
		
		//if (categoriaExistente != null && !categoriaExistente.equals(categoria)) {
		//	throw new NegocioException("Já existe um categoria com o código informado.");
		//}
		
		return categorias.guardar(categoria);
	}
	
	
	public List<Categoria> categoriasDisponiveis(Categoria categoria) {
		
		if (categoria.getId() == null)
			return	categorias.categoriasDisponiveis(Long.parseLong("-1"));
		else
		    return	categorias.categoriasDisponiveis(categoria.getId());
	}
	
}
