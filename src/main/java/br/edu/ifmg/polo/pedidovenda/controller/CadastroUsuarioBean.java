package br.edu.ifmg.polo.pedidovenda.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.edu.ifmg.polo.pedidovenda.model.Grupo;
import br.edu.ifmg.polo.pedidovenda.model.Usuario;
import br.edu.ifmg.polo.pedidovenda.repository.Grupos;
import br.edu.ifmg.polo.pedidovenda.service.CadastroUsuarioService;
import br.edu.ifmg.polo.pedidovenda.service.NegocioException;
import br.edu.ifmg.polo.pedidovenda.util.jsf.FacesUtil;

@Named
@ViewScoped
public class CadastroUsuarioBean implements Serializable {

	private static final long serialVersionUID = 1L;

	
	@Inject
	private CadastroUsuarioService cadastroUsuarioService;
	
	private Usuario usuario;
	private Grupo auxGrupo;
	
    private List<Grupo> permissoes;	
    @Inject
    private Grupos grupos;
    
	public CadastroUsuarioBean() {
		limpar();
	}
	
	public void inicializar() {
		if (this.usuario == null) {
			limpar();
		}
		
		this.permissoes = grupos.grupos();
		
	}
	
	
	private void limpar() {
		usuario = new Usuario();
	}
	
	public void salvar() {
		try {
			this.usuario = cadastroUsuarioService.salvar(this.usuario);
			limpar();
			
			FacesUtil.addInfoMessage("Usuario salvo com sucesso!");
		} catch (NegocioException ne) {
			FacesUtil.addErrorMessage(ne.getMessage());
		}
	}

	public Usuario getUsuario() {
		return usuario;
	}
	
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public boolean isEditando() {
		return this.usuario.getId() != null;
	}

	public void novoGrupo(){
		auxGrupo = new Grupo();
	}
	
	public void vincularGrupo(){
		
		Boolean jaExiste=false;
		for (Grupo g : usuario.getGrupos()) {
			if (g.equals(auxGrupo)){
				jaExiste=true;
			    break;
		    }
		}
		
		if (jaExiste)
			FacesUtil.addErrorMessage("A permissão já está associada ao usuário");
		else
			usuario.getGrupos().add(auxGrupo);
	}
	
    public void excluirGrupo(){
    	if (auxGrupo != null){
    		System.out.println(usuario.getGrupos());
			if (usuario.getGrupos().remove(auxGrupo))
				System.out.println("removeu");
			System.out.println(usuario.getGrupos());
		}
    }
	
	
	public Grupo getAuxGrupo() {
		return auxGrupo;
	}

	public void setAuxGrupo(Grupo auxGrupo) {
		this.auxGrupo = auxGrupo;
	}

	public List<Grupo> getPermissoes() {
		return permissoes;
	}

}