package br.edu.ifmg.polo.pedidovenda.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "entrega")
public class Entrega implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Date dataDistribuicao = new Date();
	private Usuario entregador;
	private StatusEntrega status = StatusEntrega.PENDENTE;
	private List<ItemEntrega> itens = new ArrayList<>();
	private BigDecimal valorTotal = BigDecimal.ZERO;
	private String latitude;
	private String longitude;
	@Temporal(TemporalType.TIMESTAMP)
	private Date registro;
	
	public Entrega() {
	}

	
	@Id
	@GeneratedValue	
	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}


	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_distribuicao", nullable = false)
	public Date getDataDistribuicao() {
		return dataDistribuicao;
	}



	public void setDataDistribuicao(Date dataDistribuicao) {
		this.dataDistribuicao = dataDistribuicao;
	}


	@NotNull
	@ManyToOne
	@JoinColumn(name = "entregador_id", nullable = false)
	public Usuario getEntregador() {
		return entregador;
	}



	public void setEntregador(Usuario entregador) {
		this.entregador = entregador;
	}


	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	public StatusEntrega getStatus() {
		return status;
	}



	public void setStatus(StatusEntrega status) {
		this.status = status;
	}


	@OneToMany(mappedBy = "entrega", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	public List<ItemEntrega> getItens() {
		return itens;
	}



	public void setItens(List<ItemEntrega> itens) {
		this.itens = itens;
	}


	public BigDecimal getValorTotal() {
		return valorTotal;
	}


	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	@Column(nullable = false, length = 80)
	public String getLatitude() {
		return latitude;
	}


	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	@Column(nullable = false, length = 80)
	public String getLongitude() {
		return longitude;
	}


	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}


	public Date getRegistro() {
		return registro;
	}


	public void setRegistro(Date registro) {
		this.registro = registro;
	}


	@Transient
	public boolean isPlanejamento() {
		return StatusEntrega.PLANEJAMENTO.equals(this.getStatus());
	}
	
	@Transient
	public boolean isPendente() {
		return StatusEntrega.PENDENTE.equals(this.getStatus());
	}

	@Transient
	public boolean isEncerrada() {
		return StatusEntrega.ENCERRADO.equals(this.getStatus());
	}

	
	public void adicionarItemVazio() {
		if (this.isPendente()) {
			Pedido pedido = new Pedido();
			
			ItemEntrega item = new ItemEntrega();
			item.setPedido(pedido);
			item.setEntrega(this);
			this.getItens().add(0, item);
		}
	}

	
	public void recalcularValorTotal() {
		BigDecimal total = BigDecimal.ZERO;
		
		//total = total.add(this.getValorFrete()).subtract(this.getValorDesconto());
		
		for (ItemEntrega item : this.getItens()) {
			if (item.getPedido() != null && item.getPedido().getId() != null) {
				total = total.add(item.getPedido().getValorTotal());
			}
		}
		
		this.setValorTotal(total);
	}	
	
	public void removerItemVazio() {
		
		ItemEntrega primeiroItem = null;
		if (this.getItens().size()>0)
		  primeiroItem = this.getItens().get(0);
		
		if (primeiroItem != null && primeiroItem.getPedido().getId() == null) {
			this.getItens().remove(0);
		}
	}	
	
	@Transient
	public boolean isNovo() {
		return getId() == null;
	}
	
	@Transient
	public boolean isExistente() {
		return !isNovo();
	}	
	
	
	@Transient
	public boolean isAlteravel() {
		return this.isPlanejamento() || this.isPendente();
	}
	
	@Transient
	public boolean isNaoAlteravel() {
		return !this.isAlteravel();
	}	
	
	@Override
	public String toString() {
		return "Entrega [id=" + id + ", dataDistribuicao=" + dataDistribuicao + ", entregador=" + entregador + "]";
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Entrega))
			return false;
		Entrega other = (Entrega) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
