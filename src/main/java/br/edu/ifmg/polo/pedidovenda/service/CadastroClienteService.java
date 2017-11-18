package br.edu.ifmg.polo.pedidovenda.service;

import java.io.Serializable;

import javax.inject.Inject;

import br.edu.ifmg.polo.pedidovenda.model.Cliente;
import br.edu.ifmg.polo.pedidovenda.repository.Clientes;
import br.edu.ifmg.polo.pedidovenda.util.jpa.Transactional;

public class CadastroClienteService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Clientes clientes;
	
	@Transactional
	public Cliente salvar(Cliente cliente) throws NegocioException {
		
		//Cliente clienteExistente = null;
		//if (cliente.getId() > 0)
		// clienteExistente = clientes.porId(cliente.getId());
		
		//if (clienteExistente != null && !clienteExistente.equals(cliente)) {
		//	throw new NegocioException("Já existe um cliente com o código informado.");
		//}
		
		return clientes.guardar(cliente);
	}
	
}
