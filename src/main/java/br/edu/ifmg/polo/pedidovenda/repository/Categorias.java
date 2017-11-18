package br.edu.ifmg.polo.pedidovenda.repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;

import br.edu.ifmg.polo.pedidovenda.model.Categoria;
import br.edu.ifmg.polo.pedidovenda.repository.filter.CategoriaFilter;
import br.edu.ifmg.polo.pedidovenda.service.NegocioException;
import br.edu.ifmg.polo.pedidovenda.util.jpa.Transactional;

public class Categorias implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;
	
	
	public Categoria guardar(Categoria categoria) {
		return manager.merge(categoria);
	}
	
	@Transactional
	public void remover(Categoria categoria) throws NegocioException {
		try {
			categoria = porId(categoria.getId());
			manager.remove(categoria);
			manager.flush();
		} catch (PersistenceException e) {
			throw new NegocioException("Categoria não pode ser excluída.");
		}
	}
	
	
	public List<Categoria> filtrados(CategoriaFilter filtro) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Categoria> criteriaQuery = builder.createQuery(Categoria.class);
		List<Predicate> predicates = new ArrayList<>();
		
		Root<Categoria> categoriaRoot = criteriaQuery.from(Categoria.class);
		
		
		if (filtro.getId() != null) {
			predicates.add(builder.equal(categoriaRoot.get("id"), filtro.getId()));
		}
		
		if (StringUtils.isNotBlank(filtro.getDescricao())) {
			predicates.add(builder.like(builder.lower(categoriaRoot.get("descricao")), 
					"%" + filtro.getDescricao().toLowerCase() + "%"));
		}
		
		criteriaQuery.select(categoriaRoot);
		criteriaQuery.where(predicates.toArray(new Predicate[0]));
		criteriaQuery.orderBy(builder.asc(categoriaRoot.get("descricao")));
		
		TypedQuery<Categoria> query = manager.createQuery(criteriaQuery);
		return query.getResultList();
	}
	
	
	public List<Categoria> porNome(String nome) {
		return this.manager.createQuery("from Categoria " +
				"where upper(descricao) like :nome", Categoria.class)
				.setParameter("nome", nome.toUpperCase() + "%")
				.getResultList();
	}
	
	
	public Categoria porId(Long id) {
		return manager.find(Categoria.class, id);
	}
	
	public List<Categoria> raizes() {
		return manager.createQuery("from Categoria where categoriaPai is null", 
				Categoria.class).getResultList();
	}
	
	public List<Categoria> subcategoriasDe(Categoria categoriaPai) {
		return manager.createQuery("from Categoria where categoriaPai = :raiz", 
				Categoria.class).setParameter("raiz", categoriaPai).getResultList();
	}
	
	
	public List<Categoria> categoriasDisponiveis(Long id) {
		return manager.createQuery("from Categoria c where c.id != :id", 
				Categoria.class).setParameter("id", id).getResultList();
	}	
	
}