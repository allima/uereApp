package br.edu.ifmg.polo.pedidovenda.controller;

import br.edu.ifmg.polo.pedidovenda.model.Entrega;


public class EntregaAlteradaEvent {

	private Entrega entrega;
	
	public EntregaAlteradaEvent(Entrega entrega) {
		this.entrega = entrega;
	}

	public Entrega getEntrega() {
		return entrega;
	}
	
}
