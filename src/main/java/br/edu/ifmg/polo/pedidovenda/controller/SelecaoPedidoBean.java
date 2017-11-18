package br.edu.ifmg.polo.pedidovenda.controller;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import br.edu.ifmg.polo.pedidovenda.model.Pedido;
import br.edu.ifmg.polo.pedidovenda.model.StatusPedido;
import br.edu.ifmg.polo.pedidovenda.repository.Pedidos;
import br.edu.ifmg.polo.pedidovenda.repository.filter.PedidoFilter;

@Named
@ViewScoped
public class SelecaoPedidoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Pedidos pedidos;
	
	private PedidoFilter filtro = new PedidoFilter();
	
	private List<Pedido> pedidosFiltrados;
	
	public void pesquisar() {
		StatusPedido[] emitidos = {StatusPedido.EMITIDO};
		filtro.setStatuses(emitidos);
		
		pedidosFiltrados = pedidos.filtrados(filtro);
	}

	public void selecionar(Pedido pedido) {
		RequestContext.getCurrentInstance().closeDialog(pedido);
	}
	
	public void abrirDialogo() {
		Map<String, Object> opcoes = new HashMap<>();
		opcoes.put("modal", true);
		opcoes.put("resizable", false);
		opcoes.put("height", 470);
		
		RequestContext.getCurrentInstance().openDialog("/dialogos/SelecaoPedido", opcoes, null);
	}
	
	public PedidoFilter getFiltro() {
		return filtro;
	}

	public void setFiltro(PedidoFilter filtro) {
		this.filtro = filtro;
	}

	public List<Pedido> getPedidosFiltrados() {
		return pedidosFiltrados;
	}

}