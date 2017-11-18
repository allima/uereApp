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

import br.edu.ifmg.polo.pedidovenda.model.Empresa;
import br.edu.ifmg.polo.pedidovenda.repository.filter.EmpresaFilter;
import br.edu.ifmg.polo.pedidovenda.service.NegocioException;
import br.edu.ifmg.polo.pedidovenda.util.jpa.Transactional;

public class Empresas implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;
	
	
	public Empresa guardar(Empresa empresa) {
		return manager.merge(empresa);
	}
	
	@Transactional
	public void remover(Empresa empresa) throws NegocioException {
		try {
			empresa = porId(empresa.getId());
			manager.remove(empresa);
			manager.flush();
		} catch (PersistenceException e) {
			throw new NegocioException("Empresa não pode ser excluído.");
		}
	}
	
	
	public List<Empresa> filtrados(EmpresaFilter filtro) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Empresa> criteriaQuery = builder.createQuery(Empresa.class);
		List<Predicate> predicates = new ArrayList<>();
		
		Root<Empresa> empresaRoot = criteriaQuery.from(Empresa.class);
		
		
		if (StringUtils.isNotBlank(filtro.getDocumento())) {
			predicates.add(builder.equal(empresaRoot.get("documentoReceitaFederal"), filtro.getDocumento()));
		}
		
		if (StringUtils.isNotBlank(filtro.getNome())) {
			predicates.add(builder.like(builder.lower(empresaRoot.get("nome")), 
					"%" + filtro.getNome().toLowerCase() + "%"));
		}
		
		criteriaQuery.select(empresaRoot);
		criteriaQuery.where(predicates.toArray(new Predicate[0]));
		criteriaQuery.orderBy(builder.asc(empresaRoot.get("nome")));
		
		TypedQuery<Empresa> query = manager.createQuery(criteriaQuery);
		return query.getResultList();
	}
	
	
	
	public Empresa porId(Long id) {
		return this.manager.find(Empresa.class, id);
	}
	
	public List<Empresa> porNome(String nome) {
		return this.manager.createQuery("from Empresa " +
				"where upper(nome) like :nome", Empresa.class)
				.setParameter("nome", nome.toUpperCase() + "%")
				.getResultList();
	}
	
	
	public Empresa porEmail(String email) {
		return this.manager.createQuery("from Empresa " +
				"where upper(email) = :email", Empresa.class)
				.setParameter("email", email.toUpperCase())
				.getSingleResult();
	}

	public Empresa buscarEmpresa() {

		//Retorna null ou sempre a primeira empresa cadastrada.
		List<Empresa> empresas = 
				this.manager.createQuery("from Empresa ", Empresa.class)
				.getResultList();
		
		if (empresas == null || empresas.size() <= 0 )
		    return null;
		else
		    return empresas.get(0);
	}	
	
	
}