package br.edu.ifmg.polo.pedidovenda.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.edu.ifmg.polo.pedidovenda.model.Cliente;
import br.edu.ifmg.polo.pedidovenda.repository.Clientes;
import br.edu.ifmg.polo.pedidovenda.repository.filter.ClienteFilter;
import br.edu.ifmg.polo.pedidovenda.service.NegocioException;
import br.edu.ifmg.polo.pedidovenda.util.jsf.FacesUtil;

@Named
@ViewScoped
public class PesquisaClientesBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private Clientes clientes;
	
	private ClienteFilter filtro;
	private List<Cliente> clientesFiltrados;
	
	private Cliente clienteSelecionado;
	
	public PesquisaClientesBean() {
		filtro = new ClienteFilter();
	}
	
	public void excluir() {
		try {
			clientes.remover(clienteSelecionado);
			clientesFiltrados.remove(clienteSelecionado);
			
			FacesUtil.addInfoMessage("Cliente " + clienteSelecionado.getNome() 
					+ " excluído com sucesso.");
		} catch (NegocioException ne) {
			FacesUtil.addErrorMessage(ne.getMessage());
		}
	}
	
	public void pesquisar() {
		clientesFiltrados = clientes.filtrados(filtro);
	}
	
	public List<Cliente> getClientesFiltrados() {
		return clientesFiltrados;
	}

	public ClienteFilter getFiltro() {
		return filtro;
	}

	public Cliente getClienteSelecionado() {
		return clienteSelecionado;
	}

	public void setClienteSelecionado(Cliente clienteSelecionado) {
		this.clienteSelecionado = clienteSelecionado;
	}
	
}