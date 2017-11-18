package br.edu.ifmg.polo.pedidovenda.service;

import java.io.Serializable;
import java.util.Date;

import javax.inject.Inject;

import br.edu.ifmg.polo.pedidovenda.model.Pedido;
import br.edu.ifmg.polo.pedidovenda.model.StatusPedido;
import br.edu.ifmg.polo.pedidovenda.repository.Pedidos;
import br.edu.ifmg.polo.pedidovenda.util.jpa.Transactional;

public class CadastroPedidoService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Pedidos pedidos;
	
	@Transactional
	public Pedido salvar(Pedido pedido) throws NegocioException {
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
		
		pedido = this.pedidos.guardar(pedido);
		return pedido;
	}
	
}
