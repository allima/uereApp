package br.edu.ifmg.polo.pedidovenda.controller;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;

import br.edu.ifmg.polo.pedidovenda.model.Grupo;
import br.edu.ifmg.polo.pedidovenda.model.Usuario;
import br.edu.ifmg.polo.pedidovenda.repository.Grupos;
import br.edu.ifmg.polo.pedidovenda.service.CadastroUsuarioService;
import br.edu.ifmg.polo.pedidovenda.service.NegocioException;
import br.edu.ifmg.polo.pedidovenda.util.jsf.FacesUtil;

@Named
@ViewScoped
public class CadastroSeBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Usuario usuario;
	
	@Inject
	private CadastroUsuarioService cadastroUsuarioService;
	
	//@Inject
	//private FacesContext facesContext;
	
	//@Inject
	//private HttpServletRequest request;
	
	//@Inject
	//private HttpServletResponse response;	
		
    @Inject
    private Grupos grupos;
    
	public CadastroSeBean() {
		limpar();
	}
	
	public void inicializar() {
		if (this.usuario == null) {
			limpar();
		}
		
		//this.permissoes = grupos.grupos();		
	}
	
	
	private void limpar() {
		usuario = new Usuario();
	}
	
	public String salvar() throws ServletException, IOException {
		try {
			
			//o novo usuário deve estar associado, pelo menos, ao grupo de CLIENTE.
			if (usuario.getGrupos().size() <= 0){
				Grupo grp = grupos.porNome("CLIENTE").get(0);
				if (grp != null)
					usuario.getGrupos().add(grp);
				else
					throw new NegocioException("Erro ao associar a permissão do cliente");
			}
			
			this.usuario = cadastroUsuarioService.salvar(this.usuario);

			FacesUtil.addInfoMessage("Usuario criado com sucesso!\n\n\nFaça o login e aproveite o sistema.");
			
			return "Login";
			
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