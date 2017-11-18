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

import br.edu.ifmg.polo.pedidovenda.model.Cliente;
import br.edu.ifmg.polo.pedidovenda.repository.filter.ClienteFilter;
import br.edu.ifmg.polo.pedidovenda.service.NegocioException;
import br.edu.ifmg.polo.pedidovenda.util.jpa.Transactional;

public class Clientes implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;
	
	
	public Cliente guardar(Cliente cliente) {
		return manager.merge(cliente);
	}
	
	@Transactional
	public void remover(Cliente cliente) throws NegocioException {
		try {
			cliente = porId(cliente.getId());
			manager.remove(cliente);
			manager.flush();
		} catch (PersistenceException e) {
			throw new NegocioException("Cliente não pode ser excluído.");
		}
	}
	
	
	public List<Cliente> filtrados(ClienteFilter filtro) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Cliente> criteriaQuery = builder.createQuery(Cliente.class);
		List<Predicate> predicates = new ArrayList<>();
		
		Root<Cliente> clienteRoot = criteriaQuery.from(Cliente.class);
		
		
		if (StringUtils.isNotBlank(filtro.getDocumento())) {
			predicates.add(builder.equal(clienteRoot.get("documentoReceitaFederal"), filtro.getDocumento()));
		}
		
		if (StringUtils.isNotBlank(filtro.getNome())) {
			predicates.add(builder.like(builder.lower(clienteRoot.get("nome")), 
					"%" + filtro.getNome().toLowerCase() + "%"));
		}
		
		criteriaQuery.select(clienteRoot);
		criteriaQuery.where(predicates.toArray(new Predicate[0]));
		criteriaQuery.orderBy(builder.asc(clienteRoot.get("nome")));
		
		TypedQuery<Cliente> query = manager.createQuery(criteriaQuery);
		return query.getResultList();
	}
	
	
	
	public Cliente porId(Long id) {
		return this.manager.find(Cliente.class, id);
	}
	
	public List<Cliente> porNome(String nome) {
		return this.manager.createQuery("from Cliente " +
				"where upper(nome) like :nome", Cliente.class)
				.setParameter("nome", nome.toUpperCase() + "%")
				.getResultList();
	}
	
	
	public Cliente porEmail(String email) {
		return this.manager.createQuery("from Cliente " +
				"where upper(email) = :email", Cliente.class)
				.setParameter("email", email.toUpperCase())
				.getSingleResult();
	}	
	
	
}