package br.edu.ifmg.polo.pedidovenda.controller;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.itextpdf.text.log.SysoCounter;

import br.edu.ifmg.polo.pedidovenda.model.Cliente;
import br.edu.ifmg.polo.pedidovenda.model.Endereco;
import br.edu.ifmg.polo.pedidovenda.service.CadastroClienteService;
import br.edu.ifmg.polo.pedidovenda.service.NegocioException;
import br.edu.ifmg.polo.pedidovenda.util.jsf.FacesUtil;

@Named
@ViewScoped
public class CadastroClienteBean implements Serializable {

	private static final long serialVersionUID = 1L;

	
	@Inject
	private CadastroClienteService cadastroClienteService;
	
	private Cliente cliente;
	private Endereco auxEndereco;	
	
	public CadastroClienteBean() {
		limpar();
	}
	
	public void inicializar() {
		if (this.cliente == null) {
			limpar();
		}
	}
	
	
	private void limpar() {
		cliente = new Cliente();
	}
	
	public void salvar() {
		try {
			this.cliente = cadastroClienteService.salvar(this.cliente);
			limpar();
			
			FacesUtil.addInfoMessage("Cliente salvo com sucesso!");
		} catch (NegocioException ne) {
			FacesUtil.addErrorMessage(ne.getMessage());
		}
	}

	public Cliente getCliente() {
		return cliente;
	}
	
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	public boolean isEditando() {
		return this.cliente.getId() != null;
	}

	
	public void novoEndereco(){
		auxEndereco = new Endereco();
	}
	
	public void vincularEndereco(){
		if (auxEndereco.getCliente() == null){
			auxEndereco.setCliente(cliente);
			cliente.getEnderecos().add(auxEndereco);
		}
	}
	
    public void excluirEndereco(){
    	if (auxEndereco != null){
    		System.out.println(cliente.getEnderecos());
			if (cliente.getEnderecos().remove(auxEndereco))
				System.out.println("removeu");
			System.out.println(cliente.getEnderecos());
		}
    }
	
	
	public Endereco getAuxEndereco() {
		return auxEndereco;
	}

	public void setAuxEndereco(Endereco auxEndereco) {
		this.auxEndereco = auxEndereco;
	}
	
	
}