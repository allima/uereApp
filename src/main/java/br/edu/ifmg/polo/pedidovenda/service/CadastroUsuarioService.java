package br.edu.ifmg.polo.pedidovenda.service;

import java.io.Serializable;

import javax.inject.Inject;

import br.edu.ifmg.polo.pedidovenda.model.Usuario;
import br.edu.ifmg.polo.pedidovenda.repository.Usuarios;
import br.edu.ifmg.polo.pedidovenda.util.jpa.Transactional;

public class CadastroUsuarioService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Usuarios usuarios;
	
	@Transactional
	public Usuario salvar(Usuario usuario) throws NegocioException {
		
		//Cliente clienteExistente = null;
		//if (cliente.getId() > 0)
		// clienteExistente = clientes.porId(cliente.getId());
		
		if (usuario.getGrupos() == null || usuario.getGrupos().size()==0) {
			throw new NegocioException("O usuário deve ser associado com uma permissão.");
		}
		
		return usuarios.guardar(usuario);
	}
	
	
}
