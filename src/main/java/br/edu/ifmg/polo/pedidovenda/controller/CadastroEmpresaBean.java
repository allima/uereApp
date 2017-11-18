package br.edu.ifmg.polo.pedidovenda.controller;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.edu.ifmg.polo.pedidovenda.model.Coordenadas;
import br.edu.ifmg.polo.pedidovenda.model.Empresa;
import br.edu.ifmg.polo.pedidovenda.model.EnderecoEntrega;
import br.edu.ifmg.polo.pedidovenda.service.CadastroEmpresaService;
import br.edu.ifmg.polo.pedidovenda.service.GoogleGeocodingService;
import br.edu.ifmg.polo.pedidovenda.service.NegocioException;
import br.edu.ifmg.polo.pedidovenda.util.jsf.FacesUtil;

@Named
@ViewScoped
public class CadastroEmpresaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	
	@Inject
	private CadastroEmpresaService cadastroEmpresaService;
	
	private Empresa empresa;	
	
	public CadastroEmpresaBean() {
		/*
		try {
			//busca sempre a primeira empresa cadastrada.
			empresa = cadastroEmpresaService.buscarEmpresa();
			
		} catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		} */
		limpar();
	}
	
	public void inicializar() {
		
		try {
			//busca sempre a primeira empresa cadastrada.
			empresa = cadastroEmpresaService.buscarEmpresa();
			
		} catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}
		
		
		if (this.empresa == null) {
			limpar();
		}
		
	}
	
	
	private void limpar() {
		empresa = new Empresa();
	}
	
	public void salvar() {
		try {
			
			//cria objeto Endereco para obter coordenadas
			EnderecoEntrega end = new EnderecoEntrega();
			
			// atualiza com o endereco da empresa
			end.setCep(empresa.getCep());
			end.setCidade(empresa.getCidade());
			end.setLogradouro(empresa.getLogradouro());
			end.setNumero(empresa.getNumero());
			end.setUf(empresa.getUf());
			
			// solicita as coordenadas
			Coordenadas coord_empresa = null;
			GoogleGeocodingService serviceEndereco = new GoogleGeocodingService();
			coord_empresa = serviceEndereco.obterCoordenadas(end);
			
			if(coord_empresa == null) {
				FacesUtil.addErrorMessage("Endereço não Encontrado!");
			}else {
				this.empresa.setLatitude(coord_empresa.getLatitude());
				this.empresa.setLongitude(coord_empresa.getLongitude());
				this.empresa = cadastroEmpresaService.salvar(this.empresa);
				//limpar();
				FacesUtil.addInfoMessage("Empresa salva com sucesso!");
			}
		
			
		} catch (NegocioException ne) {
			FacesUtil.addErrorMessage(ne.getMessage());
		}
	}

	public Empresa getEmpresa() {
		return empresa;
	}
	
	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
	
	public boolean isEditando() {
		return this.empresa.getId() != null;
	}
	
}