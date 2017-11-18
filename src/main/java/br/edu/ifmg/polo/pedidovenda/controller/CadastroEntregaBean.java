package br.edu.ifmg.polo.pedidovenda.controller;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.validator.constraints.NotBlank;
import org.primefaces.event.SelectEvent;

import br.edu.ifmg.polo.pedidovenda.model.Entrega;
import br.edu.ifmg.polo.pedidovenda.model.FormaPagamento;
import br.edu.ifmg.polo.pedidovenda.model.ItemEntrega;
import br.edu.ifmg.polo.pedidovenda.model.Pedido;
import br.edu.ifmg.polo.pedidovenda.model.Usuario;
import br.edu.ifmg.polo.pedidovenda.repository.Pedidos;
import br.edu.ifmg.polo.pedidovenda.repository.Usuarios;
import br.edu.ifmg.polo.pedidovenda.service.CadastroEntregaService;
import br.edu.ifmg.polo.pedidovenda.service.NegocioException;
import br.edu.ifmg.polo.pedidovenda.util.jsf.FacesUtil;

@Named
@ViewScoped
public class CadastroEntregaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Usuarios usuarios;
	
	@Inject
	private Pedidos pedidos;	
	
	@Inject
	private CadastroEntregaService cadastroEntregaService;
	
	private Long id_pedido;//id do pedido
	
	@Produces
	@EntregaEdicao
	private Entrega entrega;
	
	private List<Usuario> entregadores;
	
	private Pedido pedidoLinhaEditavel;
	
	private ItemEntrega itemSelecionado;
	
		
	public CadastroEntregaBean() {
		limpar();
	}
	
	public void inicializar() {
		if (this.entrega == null) {
			limpar();
		}
		
		this.entregadores = this.usuarios.vendedores();
		
		this.entrega.adicionarItemVazio();
		
		//this.recalcularPedido();
	}

	
	private void limpar() {
		entrega = new Entrega();
		//pedido.setEnderecoEntrega(new EnderecoEntrega());
	}
	
	
	public void entregadorSelecionado(SelectEvent event) {
		entrega.setEntregador((Usuario) event.getObject());
	}	
	
	public void pedidoSelecionado(SelectEvent event) {
		pedidoLinhaEditavel = ((Pedido) event.getObject());
		id_pedido = pedidoLinhaEditavel.getId();
		this.carregarPedidoLinhaEditavel();
	}
	
	
	public void entredaAlterada(@Observes EntregaAlteradaEvent event) {
		this.entrega = event.getEntrega();
	}
	
	public void salvar() {
		this.entrega.removerItemVazio();
		
		try {
			this.entrega = this.cadastroEntregaService.salvar(this.entrega);
		
			FacesUtil.addInfoMessage("Entrega salva com sucesso!");
		} catch (NegocioException ne) {
			FacesUtil.addErrorMessage(ne.getMessage());
		} finally {
			this.entrega.adicionarItemVazio();
		}
	}
	
	public void recalcularEntrega() {
		if (this.entrega != null) {
			this.entrega.recalcularValorTotal();
		}
	}
	
	public void carregarPedidoPorId() {
		if (this.id_pedido != null) {
			this.pedidoLinhaEditavel = this.pedidos.porId(this.id_pedido);
			this.carregarPedidoLinhaEditavel();
		}
	}
	
	public void carregarPedidoLinhaEditavel() {
		ItemEntrega item = this.entrega.getItens().get(0);
		
		if (this.pedidoLinhaEditavel != null) {
			if (this.existeItemComPedido(this.pedidoLinhaEditavel)) {
				FacesUtil.addErrorMessage("Já existe um item na entrega com esse pedido informado.");
			} else {
				item.setPedido(this.pedidoLinhaEditavel);
				//item.setValorUnitario(this.pedidoLinhaEditavel.getValorUnitario());
				
				this.entrega.adicionarItemVazio();
				this.pedidoLinhaEditavel = null;
				this.id_pedido = null;
				
				this.entrega.recalcularValorTotal();
			}
		}
	}
	
	private boolean existeItemComPedido(Pedido pedido) {
		boolean existeItem = false;
		
		for (ItemEntrega item : this.getEntrega().getItens()) {
			if (pedido.equals(item.getPedido())) {
				existeItem = true;
				break;
			}
		}
		
		return existeItem;
	}

	//public List<Pedido> completarPedido(String nome) {
	//	return this.entregas.porNome(nome);
	//}
	
	
	//public FormaPagamento[] getFormasPagamento() {
	//	return FormaPagamento.values();
	//}
	
	public List<Usuario> completarEntregador(String nome) {
		return this.usuarios.porNome(nome);
	}

	public Entrega getEntrega() {
		return entrega;
	}
	
	public void setEntrega(Entrega entrega) {
		this.entrega = entrega;
	}

	public List<Usuario> getEntregadores() {
		return entregadores;
	}
	
	public boolean isEditando() {
		return this.entrega.getId() != null;
	}

	public Pedido getPedidoLinhaEditavel() {
		return pedidoLinhaEditavel;
	}

	public void setPedidoLinhaEditavel(Pedido pedidoLinhaEditavel) {
		this.pedidoLinhaEditavel = pedidoLinhaEditavel;
	}

	
	@NotBlank
	public String getNomeEntregador() {
		return entrega.getEntregador() == null ? null : entrega.getEntregador().getNome();
	}
	
	public void setNomeEntregador(String nome) {
	}

	
	public Long getId_pedido() {
		return id_pedido;
	}

	public void setId_pedido(Long id_pedido) {
		this.id_pedido = id_pedido;
	}

	public ItemEntrega getItemSelecionado() {
		return itemSelecionado;
	}

	public void setItemSelecionado(ItemEntrega itemSelecionado) {
		this.itemSelecionado = itemSelecionado;
	}

	public void finalizarEntrega(){
		return;
	}
	
	
	public void excluir() {
		//try {
			entrega.getItens().remove(itemSelecionado);
			recalcularEntrega();
			//produtosFiltrados.remove(itemSelecionado);
			
			//FacesUtil.addInfoMessage("Produto " + produtoSelecionado.getSku() 
			//		+ " excluído com sucesso.");
		//} catch (NegocioException ne) {
		//	FacesUtil.addErrorMessage(ne.getMessage());
		//}
	}
		
	
	
}