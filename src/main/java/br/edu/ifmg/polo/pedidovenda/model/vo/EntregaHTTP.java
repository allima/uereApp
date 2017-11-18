package br.edu.ifmg.polo.pedidovenda.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.edu.ifmg.polo.pedidovenda.model.Coordenadas;
import br.edu.ifmg.polo.pedidovenda.model.StatusEntrega;

public class EntregaHTTP implements Serializable {
	
	private static final long serialVersionUID = 8028148456359647484L;
	
	private Long id;
	private Date dataDistribuicao = new Date();
	private StatusEntrega status;
	private BigDecimal valorTotal = BigDecimal.ZERO;
	private List<RetornoEntregaHTTP> itens = new ArrayList<>();
	private Coordenadas origem;

	public EntregaHTTP() {
		// TODO Auto-generated constructor stub
	}

	public EntregaHTTP(Long id, Date dataDistribuicao, StatusEntrega status, BigDecimal valorTotal,
			List<RetornoEntregaHTTP> itens) {
		super();
		this.id = id;
		this.dataDistribuicao = dataDistribuicao;
		this.status = status;
		this.valorTotal = valorTotal;
		this.itens = itens;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDataDistribuicao() {
		return dataDistribuicao;
	}

	public void setDataDistribuicao(Date dataDistribuicao) {
		this.dataDistribuicao = dataDistribuicao;
	}

	public StatusEntrega getStatus() {
		return status;
	}

	public void setStatus(StatusEntrega status) {
		this.status = status;
	}

	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	public List<RetornoEntregaHTTP> getItens() {
		return itens;
	}

	public void setItens(List<RetornoEntregaHTTP> itens) {
		this.itens = itens;
	}

	public Coordenadas getOrigem() {
		return origem;
	}

	public void setOrigem(Coordenadas origem) {
		this.origem = origem;
	}

}
