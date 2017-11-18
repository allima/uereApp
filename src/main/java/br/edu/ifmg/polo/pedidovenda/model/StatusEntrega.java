package br.edu.ifmg.polo.pedidovenda.model;

public enum StatusEntrega {

	PLANEJAMENTO("Planejamento"),
	PENDENTE("Pendente"),
	ACAMINHO("A caminho"),
	DEVOLVIDA("Devolvida"),
	ENTREGUE("Entregue"),
	ENCERRADO("Encerrada");
	
	private String descricao;
	
	StatusEntrega(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
}
