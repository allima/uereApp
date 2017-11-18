package br.edu.ifmg.polo.pedidovenda.repository.filter;

import java.io.Serializable;
import java.util.Date;

import br.edu.ifmg.polo.pedidovenda.model.StatusEntrega;

public class EntregaFilter implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long numeroDe;
	private Long numeroAte;
	private Date dataDistribuicaoDe;
	private Date dataDistribuicaoAte;
	private String nomeEntregador;
	private String nomeCliente;
	private StatusEntrega[] statuses;
	
	private int primeiroRegistro;
	private int quantidadeRegistros;
	private String propriedadeOrdenacao;
	private boolean ascendente;

	public Long getNumeroDe() {
		return numeroDe;
	}

	public void setNumeroDe(Long numeroDe) {
		this.numeroDe = numeroDe;
	}

	public Long getNumeroAte() {
		return numeroAte;
	}

	public void setNumeroAte(Long numeroAte) {
		this.numeroAte = numeroAte;
	}

	public Date getDataDistribuicaoDe() {
		return dataDistribuicaoDe;
	}

	public void setDataDistribuicaoDe(Date dataDistribuicaoDe) {
		this.dataDistribuicaoDe = dataDistribuicaoDe;
	}

	public Date getDataDistribuicaoAte() {
		return dataDistribuicaoAte;
	}

	public void setDataDistribuicaoAte(Date dataDistribuicaoAte) {
		this.dataDistribuicaoAte = dataDistribuicaoAte;
	}

	public String getNomeEntregador() {
		return nomeEntregador;
	}

	public void setNomeEntregador(String nomeEntregador) {
		this.nomeEntregador = nomeEntregador;
	}

	public String getNomeCliente() {
		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}

	public StatusEntrega[] getStatuses() {
		return statuses;
	}

	public void setStatuses(StatusEntrega[] statuses) {
		this.statuses = statuses;
	}

	public int getPrimeiroRegistro() {
		return primeiroRegistro;
	}

	public void setPrimeiroRegistro(int primeiroRegistro) {
		this.primeiroRegistro = primeiroRegistro;
	}

	public int getQuantidadeRegistros() {
		return quantidadeRegistros;
	}

	public void setQuantidadeRegistros(int quantidadeRegistros) {
		this.quantidadeRegistros = quantidadeRegistros;
	}

	public String getPropriedadeOrdenacao() {
		return propriedadeOrdenacao;
	}

	public void setPropriedadeOrdenacao(String propriedadeOrdenacao) {
		this.propriedadeOrdenacao = propriedadeOrdenacao;
	}

	public boolean isAscendente() {
		return ascendente;
	}

	public void setAscendente(boolean ascendente) {
		this.ascendente = ascendente;
	}
	
}