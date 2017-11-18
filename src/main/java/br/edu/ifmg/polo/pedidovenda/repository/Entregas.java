package br.edu.ifmg.polo.pedidovenda.repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import br.edu.ifmg.polo.pedidovenda.model.Coordenadas;
import br.edu.ifmg.polo.pedidovenda.model.Entrega;
import br.edu.ifmg.polo.pedidovenda.model.ItemEntrega;
import br.edu.ifmg.polo.pedidovenda.model.StatusEntrega;
import br.edu.ifmg.polo.pedidovenda.repository.filter.EntregaFilter;
import br.edu.ifmg.polo.pedidovenda.service.NegocioException;
import br.edu.ifmg.polo.pedidovenda.util.jpa.Transactional;

public class Entregas implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	private Criteria criarCriteriaParaFiltro(EntregaFilter filtro) {
		Session session = this.manager.unwrap(Session.class);

		Criteria criteria = session.createCriteria(Entrega.class)
				// .createAlias("pedido", "p")//pedido
				.createAlias("entregador", "e");

		if (filtro.getNumeroDe() != null) {
			// id deve ser maior ou igual (ge = greater or equals) a filtro.numeroDe
			criteria.add(Restrictions.ge("id", filtro.getNumeroDe()));
		}

		if (filtro.getNumeroAte() != null) {
			// id deve ser menor ou igual (le = lower or equal) a filtro.numeroDe
			criteria.add(Restrictions.le("id", filtro.getNumeroAte()));
		}

		if (filtro.getDataDistribuicaoDe() != null) {
			criteria.add(Restrictions.ge("dataDistribuicao", filtro.getDataDistribuicaoDe()));
		}

		if (filtro.getDataDistribuicaoAte() != null) {
			criteria.add(Restrictions.le("dataDistribuicao", filtro.getDataDistribuicaoAte()));
		}

		// if (StringUtils.isNotBlank(filtro.getNomeCliente())) {
		// acessamos o nome do cliente associado ao pedido pelo alias "c", criado
		// anteriormente
		// criteria.add(Restrictions.ilike("p.cliente.nome", filtro.getNomeCliente(),
		// MatchMode.ANYWHERE));
		// }

		if (StringUtils.isNotBlank(filtro.getNomeEntregador())) {
			// acessamos o nome do entregador associado ao pedido pelo alias "e", criado
			// anteriormente
			criteria.add(Restrictions.ilike("e.nome", filtro.getNomeEntregador(), MatchMode.ANYWHERE));
		}

		if (filtro.getStatuses() != null && filtro.getStatuses().length > 0) {
			// adicionamos uma restrição "in", passando um array de constantes da enum
			// StatusEntrega
			criteria.add(Restrictions.in("status", filtro.getStatuses()));
		}

		return criteria;
	}

	@SuppressWarnings("unchecked")
	public List<Entrega> filtrados(EntregaFilter filtro) {
		Criteria criteria = criarCriteriaParaFiltro(filtro);

		criteria.setFirstResult(filtro.getPrimeiroRegistro());
		criteria.setMaxResults(filtro.getQuantidadeRegistros());

		if (filtro.isAscendente() && filtro.getPropriedadeOrdenacao() != null) {
			criteria.addOrder(Order.asc(filtro.getPropriedadeOrdenacao()));
		} else if (filtro.getPropriedadeOrdenacao() != null) {
			criteria.addOrder(Order.desc(filtro.getPropriedadeOrdenacao()));
		}

		return criteria.list();
	}

	public int quantidadeFiltrados(EntregaFilter filtro) {
		Criteria criteria = criarCriteriaParaFiltro(filtro);

		criteria.setProjection(Projections.rowCount());

		return ((Number) criteria.uniqueResult()).intValue();
	}

	public Entrega guardar(Entrega entrega) {
		return this.manager.merge(entrega);
	}

	public Entrega porId(Long id) {
		return this.manager.find(Entrega.class, id);
	}

	@Transactional
	public void remover(Entrega entrega) throws NegocioException {
		try {
			entrega = porId(entrega.getId());
			manager.remove(entrega);
			manager.flush();
		} catch (PersistenceException e) {
			throw new NegocioException("Entrega não pode ser excluída.");
		}
	}

	public List<Entrega> getListaEntregasEntregador(Long id_entregador) {
		List<Entrega> aux = new ArrayList<>();

		try {
			aux = (ArrayList<Entrega>) this.manager
					.createQuery("from Entrega where entregador_id = :id_entregador ", Entrega.class)
					.setParameter("id_entregador", id_entregador).getResultList();
			return aux;
		} catch (NoResultException e) {
			// nenhum usuário encontrado com o e-mail informado
			return null;
		}
	}

	public Entrega getListaEntregasPendenteEntregador(Long id_entrega) {

		Entrega aux = new Entrega();

		try {
			aux = (Entrega) this.manager
					.createQuery("from Entrega where id = :id_entrega and status = 'PENDENTE' ",
							Entrega.class)
					.setParameter("id_entrega", id_entrega).getSingleResult();
			return aux;
		} catch (NoResultException e) {
			// nenhum item encontrado
			return null;
		}
	}

	public void setStatusItemEntrega(Long id_item, StatusEntrega status) {
		try {

			ItemEntrega itemEntrega = null;			

			this.manager.getTransaction().begin();			
			itemEntrega = this.manager.find(ItemEntrega.class,id_item);
			itemEntrega.setStatus(status);
			this.manager.getTransaction().commit();

		} catch (NoResultException e) {
			this.manager.close();
		}
		
	}

	public void setStatusEntrega(Long id_entrega, StatusEntrega status) {
		
		try {
			Entrega entrega = null;		
			this.manager.getTransaction().begin();			
			entrega = this.manager.find(Entrega.class,id_entrega);
			entrega.setStatus(status);
			this.manager.getTransaction().commit();

		} catch (NoResultException e) {
			this.manager.close();
		}

	}
	
public void setAvaliacaoItemEntrega(Long id_itemEntrega, Integer avaliacao) {
		
		try {
			ItemEntrega item = null;		
			this.manager.getTransaction().begin();			
			item = this.manager.find(ItemEntrega.class,id_itemEntrega);
			item.setAvaliacao(avaliacao);
			this.manager.getTransaction().commit();
			

		} catch (NoResultException e) {
			this.manager.close();
		}

	}

	public void setLocalizacaoEntregador(Entrega entregador) {
		
		
		try {
			
			Entrega entrega = null;
			this.manager.getTransaction().begin();	
			entrega = this.manager.find(Entrega.class,entregador.getId()); // id_entrega
			entrega.setLatitude(entregador.getLatitude());
			entrega.setLongitude(entregador.getLongitude());
			entrega.setRegistro(entregador.getRegistro());
			this.manager.getTransaction().commit();

		} catch (NoResultException e) {
			this.manager.close();
		}
		
	}
	
	public Coordenadas getEntrega(Long id_entrega) {
		
		try {
			Coordenadas cood = new Coordenadas();		
			
			cood.setLatitude((String) this.manager
					.createQuery("select latitude from Entrega where id = :id_entrega ")
					.setParameter("id_entrega", id_entrega).getSingleResult());
			
			cood.setLongitude((String) this.manager
					.createQuery("select longitude from Entrega where id = :id_entrega ")
					.setParameter("id_entrega", id_entrega).getSingleResult());
			
			return cood;
		} catch (NoResultException e) {
			this.manager.close();
			return null;
		}
		
		
	}
	
}