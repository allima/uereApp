package br.edu.ifmg.polo.pedidovenda.service;

import java.io.Serializable;

import javax.inject.Inject;

import br.edu.ifmg.polo.pedidovenda.model.Empresa;
import br.edu.ifmg.polo.pedidovenda.repository.Empresas;
import br.edu.ifmg.polo.pedidovenda.util.jpa.Transactional;

public class CadastroEmpresaService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Empresas empresas;
	
	@Transactional
	public Empresa salvar(Empresa empresa) throws NegocioException {
		
		return empresas.guardar(empresa);
	}
	
	
	public Empresa buscarEmpresa() throws NegocioException {
		
		return empresas.buscarEmpresa();
	}	
	
	
}
