package br.edu.ifmg.polo.pedidovenda.model;

public class TempoEntrega {
	
	private int ordemMapa;
	private Coordenadas coordenadas;
	private String nomeCliente;
	private String pontoMapa;
	private String distancia;
	private String tempo;
	private StatusEntrega status;
	
	public TempoEntrega() {
	}
	
	

	public TempoEntrega(Coordenadas coordenadas, String nomeCliente, String pontoMapa) {
		super();
		this.coordenadas = coordenadas;
		this.nomeCliente = nomeCliente;
		this.pontoMapa = pontoMapa;
	}



	public int getOrdemMapa() {
		return ordemMapa;
	}

	public void setOrdemMapa(int ordemMapa) {
		this.ordemMapa = ordemMapa;
	}

	public Coordenadas getCoordenadas() {
		return coordenadas;
	}

	public void setCoordenadas(Coordenadas coordenadas) {
		this.coordenadas = coordenadas;
	}

	public String getNomeCliente() {
		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}

	public String getPontoMapa() {
		return pontoMapa;
	}

	public void setPontoMapa(String pontoMapa) {
		this.pontoMapa = pontoMapa;
	}

	public String getDistancia() {
		return distancia;
	}

	public void setDistancia(String distancia) {
		this.distancia = distancia;
	}

	public String getTempo() {
		return tempo;
	}

	public void setTempo(String tempo) {
		this.tempo = tempo;
	}



	public StatusEntrega getStatus() {
		return status;
	}



	public void setStatus(StatusEntrega status) {
		this.status = status;
	}

}
