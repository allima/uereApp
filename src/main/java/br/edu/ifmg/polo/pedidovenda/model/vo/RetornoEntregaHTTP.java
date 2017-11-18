package br.edu.ifmg.polo.pedidovenda.model.vo;

import java.math.BigDecimal;
import br.edu.ifmg.polo.pedidovenda.model.EnderecoEntrega;
import br.edu.ifmg.polo.pedidovenda.model.FormaPagamento;
import br.edu.ifmg.polo.pedidovenda.model.StatusEntrega;


public class RetornoEntregaHTTP {

	private Long id;
	private BigDecimal valorTotal = BigDecimal.ZERO;
	private StatusEntrega status;
	private FormaPagamento formaPagamento;
	private String nomeCliente;
	private String documentoReceitaFederalCliente;
	private EnderecoEntrega enderecoEntrega;
	private String latitude;
	private String longitude;

	public RetornoEntregaHTTP() {
		// TODO Auto-generated constructor stub
	}
	
	

	public RetornoEntregaHTTP(Long id, BigDecimal valorTotal, StatusEntrega status, FormaPagamento formaPagamento,
			String nomeCliente, String documentoReceitaFederalCliente, EnderecoEntrega enderecoEntrega) {
		super();
		this.id = id;
		this.valorTotal = valorTotal;
		this.status = status;
		this.formaPagamento = formaPagamento;
		this.nomeCliente = nomeCliente;
		this.documentoReceitaFederalCliente = documentoReceitaFederalCliente;
		this.enderecoEntrega = enderecoEntrega;
		this.latitude = enderecoEntrega.getLatitude();
		this.longitude = enderecoEntrega.getLongitude();
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	public StatusEntrega getStatus() {
		return status;
	}

	public void setStatus(StatusEntrega status) {
		this.status = status;
	}

	public FormaPagamento getFormaPagamento() {
		return formaPagamento;
	}

	public void setFormaPagamento(FormaPagamento formaPagamento) {
		this.formaPagamento = formaPagamento;
	}

	public String getNomeCliente() {
		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}

	public String getDocumentoReceitaFederalCliente() {
		return documentoReceitaFederalCliente;
	}

	public void setDocumentoReceitaFederalCliente(String documentoReceitaFederalCliente) {
		this.documentoReceitaFederalCliente = documentoReceitaFederalCliente;
	}

	public EnderecoEntrega getEnderecoEntrega() {
		return enderecoEntrega;
	}

	public void setEnderecoEntrega(EnderecoEntrega enderecoEntrega) {
		this.enderecoEntrega = enderecoEntrega;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

}	