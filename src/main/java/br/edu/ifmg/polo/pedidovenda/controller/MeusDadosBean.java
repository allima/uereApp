package br.edu.ifmg.polo.pedidovenda.controller;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;

import br.edu.ifmg.polo.pedidovenda.model.Usuario;
import br.edu.ifmg.polo.pedidovenda.security.Seguranca;
import br.edu.ifmg.polo.pedidovenda.service.CadastroUsuarioService;
import br.edu.ifmg.polo.pedidovenda.service.NegocioException;
import br.edu.ifmg.polo.pedidovenda.util.jsf.FacesUtil;

@Named
@ViewScoped
public class MeusDadosBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Usuario usuario;
	
	@Inject
	private CadastroUsuarioService cadastroUsuarioService;

		
    @Inject
    private Seguranca seguranca;
    
	public MeusDadosBean() {

	}
	
	public void inicializar() {
		
		usuario = seguranca.getUsuarioLogado().getUsuario();
				
	}
	
	
	public String salvar() throws ServletException, IOException {
		try {
			
			this.usuario = cadastroUsuarioService.salvar(this.usuario);

			FacesUtil.addInfoMessage("Alterações salvas com sucesso!");
			
			return null;
			
		} catch (NegocioException ne) {
			FacesUtil.addErrorMessage(ne.getMessage());
		}
		return null;
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

	//public void novoGrupo(){
	//	auxGrupo = new Grupo();
	//}
	/*
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
*/
}