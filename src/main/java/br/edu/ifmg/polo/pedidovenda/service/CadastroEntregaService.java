package br.edu.ifmg.polo.pedidovenda.service;

import java.io.Serializable;

import javax.inject.Inject;

import br.edu.ifmg.polo.pedidovenda.model.Entrega;
import br.edu.ifmg.polo.pedidovenda.repository.Entregas;
import br.edu.ifmg.polo.pedidovenda.util.jpa.Transactional;

public class CadastroEntregaService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Entregas entregas;
	
	@Transactional
	public Entrega salvar(Entrega entrega) throws NegocioException {
		/*
		if (pedido.isNovo()) {
			pedido.setDataCriacao(new Date());
			pedido.setStatus(StatusPedido.ORCAMENTO);
		}
		
		pedido.recalcularValorTotal();
		
		if (pedido.isNaoAlteravel()) {
			throw new NegocioException("Pedido não pode ser alterado no status "
					+ pedido.getStatus().getDescricao() + ".");
		}
		
		if (pedido.getItens().isEmpty()) {
			throw new NegocioException("O pedido deve possuir pelo menos um item.");
		}
		
		if (pedido.isValorTotalNegativo()) {
			throw new NegocioException("Valor total do pedido não pode ser negativo.");
		}
		*/
		
		  entrega = this.entregas.guardar(entrega);
		
		
		return entrega;
	}
	
}
