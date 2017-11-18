package br.edu.ifmg.polo.pedidovenda.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import br.edu.ifmg.polo.pedidovenda.model.Grupo;
import br.edu.ifmg.polo.pedidovenda.service.NegocioException;
import br.edu.ifmg.polo.pedidovenda.util.jpa.Transactional;

public class Grupos implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public Grupo guardar(Grupo grupo) {
		return manager.merge(grupo);
	}
	
	@Transactional
	public void remover(Grupo grupo) throws NegocioException {
		try {
			grupo = porId(grupo.getId());
			manager.remove(grupo);
			manager.flush();
		} catch (PersistenceException e) {
			throw new NegocioException("Grupo não pode ser excluído.");
		}
	}

	public Grupo porId(Integer id) {
		try {
			return manager.createQuery("from Grupo where upper(id) = :id", Grupo.class)
				.setParameter("id", id)
				.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	/*
	public List<Grupo> filtrados(GrupoFilter filtro) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Grupo> criteriaQuery = builder.createQuery(Grupo.class);
		List<Predicate> predicates = new ArrayList<>();
		
		Root<Grupo> grupoRoot = criteriaQuery.from(Grupo.class);
		Fetch<Grupo, Categoria> categoriaJoin = grupoRoot.fetch("categoria", JoinType.INNER);
		categoriaJoin.fetch("categoriaPai", JoinType.INNER);
		
		if (StringUtils.isNotBlank(filtro.getSku())) {
			predicates.add(builder.equal(grupoRoot.get("sku"), filtro.getSku()));
		}
		
		if (StringUtils.isNotBlank(filtro.getNome())) {
			predicates.add(builder.like(builder.lower(grupoRoot.get("nome")), 
					"%" + filtro.getNome().toLowerCase() + "%"));
		}
		
		criteriaQuery.select(grupoRoot);
		criteriaQuery.where(predicates.toArray(new Predicate[0]));
		criteriaQuery.orderBy(builder.asc(grupoRoot.get("nome")));
		
		TypedQuery<Grupo> query = manager.createQuery(criteriaQuery);
		return query.getResultList();
	}
	*/
	public Grupo porId(Long id) {
		return manager.find(Grupo.class, id);
	}

	public List<Grupo> porNome(String nome) {
		return this.manager.createQuery("from Grupo where upper(nome) like :nome", Grupo.class)
				.setParameter("nome", nome.toUpperCase() + "%").getResultList();
	}

	
	public List<Grupo> grupos() {
		
		return this.manager.createQuery("from Grupo", Grupo.class)
				.getResultList();
	}

}