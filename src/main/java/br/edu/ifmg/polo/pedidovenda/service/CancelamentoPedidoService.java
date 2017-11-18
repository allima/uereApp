package br.edu.ifmg.polo.pedidovenda.service;

import java.io.Serializable;

import javax.inject.Inject;

import br.edu.ifmg.polo.pedidovenda.model.Pedido;
import br.edu.ifmg.polo.pedidovenda.model.StatusPedido;
import br.edu.ifmg.polo.pedidovenda.repository.Pedidos;
import br.edu.ifmg.polo.pedidovenda.util.jpa.Transactional;

public class CancelamentoPedidoService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Pedidos pedidos;
	
	@Inject
	private EstoqueService estoqueService;
	
	@Transactional
	public Pedido cancelar(Pedido pedido) throws NegocioException {
		pedido = this.pedidos.porId(pedido.getId());
		
		if (pedido.isNaoCancelavel()) {
			throw new NegocioException("Pedido não pode ser cancelado no status "
					+ pedido.getStatus().getDescricao() + ".");
		}
		
		if (pedido.isEmitido()) {
			this.estoqueService.retornarItensEstoque(pedido);
		}
		
		pedido.setStatus(StatusPedido.CANCELADO);
		
		pedido = this.pedidos.guardar(pedido);
		
		return pedido;
	}

}
