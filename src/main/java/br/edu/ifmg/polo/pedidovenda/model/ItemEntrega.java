package br.edu.ifmg.polo.pedidovenda.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "item_entrega")
public class ItemEntrega implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private Entrega entrega;
	private Pedido pedido;
	private Integer avaliacao=0;
	private Date dataEntrega;
	private StatusEntrega status = StatusEntrega.PENDENTE;
	private String observacao;
	
	public ItemEntrega() {
 
	}

	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@ManyToOne
	@JoinColumn(name = "entrega_id", nullable = false)
	public Entrega getEntrega() {
		return entrega;
	}

	public void setEntrega(Entrega entrega) {
		this.entrega = entrega;
	}
	
	@ManyToOne
	@JoinColumn(name = "pedido_id", nullable = false)
	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	@Min(0)
	@Max(5)
	public Integer getAvaliacao() {
		return avaliacao;
	}

	public void setAvaliacao(Integer avaliacao) {
		this.avaliacao = avaliacao;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_entrega")
	public Date getDataEntrega() {
		return dataEntrega;
	}

	public void setDataEntrega(Date dataEntrega) {
		this.dataEntrega = dataEntrega;
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

	@Column(columnDefinition = "text")
	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	
	@Transient
	public boolean isPedidoAssociado() {
		return this.getPedido() != null && this.getPedido().getId() != null;
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
		if (!(obj instanceof ItemEntrega))
			return false;
		ItemEntrega other = (ItemEntrega) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ItemEntrega [id=" + id + ", entrega=" + entrega + ", pedido=" + pedido + ", avaliacao=" + avaliacao
				+ ", dataEntrega=" + dataEntrega + ", status=" + status + "]";
	}
		
}
