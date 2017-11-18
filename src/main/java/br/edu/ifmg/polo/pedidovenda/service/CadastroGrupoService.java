package br.edu.ifmg.polo.pedidovenda.service;

import java.io.Serializable;

import javax.inject.Inject;

import br.edu.ifmg.polo.pedidovenda.model.Grupo;
import br.edu.ifmg.polo.pedidovenda.repository.Grupos;
import br.edu.ifmg.polo.pedidovenda.util.jpa.Transactional;

public class CadastroGrupoService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Grupos grupos;
	
	@Transactional
	public Grupo salvar(Grupo grupo) throws NegocioException {
		Grupo grupoExistente = grupos.porId(grupo.getId());
		
		if (grupoExistente != null && !grupoExistente.equals(grupo)) {
			throw new NegocioException("Já existe um grupo com o Id informado.");
		}
		
		return grupos.guardar(grupo);
	}
	
}
