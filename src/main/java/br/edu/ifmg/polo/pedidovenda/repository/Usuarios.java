package br.edu.ifmg.polo.pedidovenda.repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;

import br.edu.ifmg.polo.pedidovenda.model.Usuario;
import br.edu.ifmg.polo.pedidovenda.repository.filter.UsuarioFilter;
import br.edu.ifmg.polo.pedidovenda.service.NegocioException;
import br.edu.ifmg.polo.pedidovenda.util.jpa.Transactional;

public class Usuarios implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public Usuario guardar(Usuario usuario) {
		return manager.merge(usuario);
	}

	@Transactional
	public void remover(Usuario usuario) throws NegocioException {
		try {
			usuario = porId(usuario.getId());
			manager.remove(usuario);
			manager.flush();
		} catch (PersistenceException e) {
			throw new NegocioException("Usuario não pode ser excluído.");
		}
	}

	public List<Usuario> filtrados(UsuarioFilter filtro) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Usuario> criteriaQuery = builder.createQuery(Usuario.class);
		List<Predicate> predicates = new ArrayList<>();

		Root<Usuario> usuarioRoot = criteriaQuery.from(Usuario.class);

		if (StringUtils.isNotBlank(filtro.getEmail())) {
			predicates.add(
					builder.like(builder.lower(usuarioRoot.get("email")), "%" + filtro.getNome().toLowerCase() + "%"));
		}

		if (StringUtils.isNotBlank(filtro.getNome())) {
			predicates.add(
					builder.like(builder.lower(usuarioRoot.get("nome")), "%" + filtro.getNome().toLowerCase() + "%"));
		}

		criteriaQuery.select(usuarioRoot);
		criteriaQuery.where(predicates.toArray(new Predicate[0]));
		criteriaQuery.orderBy(builder.asc(usuarioRoot.get("nome")));

		TypedQuery<Usuario> query = manager.createQuery(criteriaQuery);
		return query.getResultList();
	}

	public Usuario porId(Long id) {
		return this.manager.find(Usuario.class, id);
	}

	public List<Usuario> vendedores() {

		return this.manager
				.createQuery("select u from Usuario u " + "inner join u.grupos g     " + "where g.nome = 'VENDEDOR' ",
						Usuario.class)
				.getResultList();

	}

	public List<Usuario> entregadores() {

		return this.manager
				.createQuery("select u from Usuario u " + "inner join u.grupos g      " + "where g.nome = 'ENTREGADOR'",
						Usuario.class)
				.getResultList();
	}

	public List<Usuario> porNome(String nome) {
		return this.manager
				.createQuery("select u from Usuario u     " + "inner join u.grupos g       "
						+ "where g.nome = 'ENTREGADOR' " + "and upper(u.nome) like :nome", Usuario.class)
				.setParameter("nome", nome.toUpperCase() + "%").getResultList();

		/*
		 * return this.manager.createQuery("from Usuario " +
		 * "where upper(nome) like :nome", Usuario.class) .setParameter("nome",
		 * nome.toUpperCase() + "%") .getResultList();
		 */

	}

	public Usuario porEmail(String email) {
		Usuario usuario = null;

		try {
			usuario = this.manager.createQuery("from Usuario where lower(email) = :email", Usuario.class)
					.setParameter("email", email.toLowerCase()).getSingleResult();
		} catch (NoResultException e) {
			// nenhum usuário encontrado com o e-mail informado
		}

		return usuario;
	}

	public Usuario logar(String email, String senha) {
		Usuario aux = null;
		try {
			aux = this.manager
					.createQuery("from Usuario where lower(email) = :email and lower(senha) = :senha", Usuario.class)
					.setParameter("email", email.toLowerCase()).setParameter("senha", senha.toLowerCase())
					.getSingleResult();
			return aux;
		} catch (NoResultException e) {
			// nenhum usuário encontrado com o e-mail informado
			return null;
		}
	}

	public void atualizaToken(Long id_usuario, String token) {

		try {
			Usuario aux = new Usuario();
			this.manager.getTransaction().begin();
			aux = this.manager.find(Usuario.class, id_usuario);
			aux.setToken(token);
			this.manager.getTransaction().commit();
		} catch (NoResultException e) {
			this.manager.close();
		}
	}

}