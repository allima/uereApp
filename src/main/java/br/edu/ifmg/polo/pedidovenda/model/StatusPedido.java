package br.edu.ifmg.polo.pedidovenda.model;

public enum StatusPedido {

	ORCAMENTO("Orçamento"), 
	EMITIDO("Emitido"), 
	CANCELADO("Cancelado"),
	DISTRIBUIDO("Distribuído"),
	ENVIADO("Enviado"),
	ENTREGUE("Entregue");
	
	private String descricao;
	
	StatusPedido(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
	
}
