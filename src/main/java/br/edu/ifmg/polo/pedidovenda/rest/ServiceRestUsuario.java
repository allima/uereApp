package br.edu.ifmg.polo.pedidovenda.rest;


import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import br.edu.ifmg.polo.pedidovenda.model.Usuario;
import br.edu.ifmg.polo.pedidovenda.repository.Usuarios;
import br.edu.ifmg.polo.pedidovenda.service.CadastroUsuarioService;
import br.edu.ifmg.polo.pedidovenda.service.NegocioException;

/**
 * Essa classe vai expor os nossos métodos para serem acessasdos via http
 * 
 * @Path - Caminho para a chamada da classe que vai representar o nosso serviço
 */

@Path("/usuario")
public class ServiceRestUsuario {

	private static final String CHARSET_UTF8 = ";charset=utf-8";

	@Inject
	private CadastroUsuarioService cadastroUsuario;
	
	@Inject
	private Usuarios usuarios;

	/** Método para criar no BD um novo usuario
	 * 
	 * @Consumes - determina o formato dos dados que vamos postar - JSON
	 * 
	 * @author bruno
	 * @category PUT
	 * 
	 * @param Usuario - classe usuario que contem email e senha para verificação
	 * 
	 *  exemplo de entrada:
	 *  
	 *  {
     *		"email": "b@b.com",
     *		"grupos": [
     *   		{
     *       		"descricao": "ENTREGADOR",  
     *       		"id": 5,
     *       		"nome": "ENTREGADOR"
     *  	    }
     *		],
     *		"nome": "lucas",
     *		"senha": "123"
     *	}
	 * 
	 *                    
	 *   exemplo de requisição ao serviço:
	 *   http://localhost:8080/uereapp/rest/usuario
	 */	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	public void cadastraCliente(Usuario usuario) {		
		try {
			cadastroUsuario.salvar(usuario);
		} catch (NegocioException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
	}
	
	/**Método para receber dados do usuário do sistema caso aconteça algum problema
	 * na hora de sua entrega
	 * 
	 * @Consumes - determina o formato dos dados que vamos postar - JSON
	 * 
	 * @author bruno
	 * @category GET
	 * 
	 * @param Usuario - classe usuario que contem email e senha para verificação
	 * 
	 *   exemplo de entrada:
	 *  
	 *  {
     *		"email": "b@b.com",
     *		"grupos": [
     *   		{
     *       		"descricao": "ENTREGADOR",  
     *       		"id": 5,
     *       		"nome": "ENTREGADOR"
     *  	    }
     *		],
     *		"nome": "lucas",
     *		"senha": "123"
     *	}
     *
     *   exemplo de requisição ao serviço:
	 *   http://localhost:8080/uereapp/rest/usuario
	 * 
	 * */
	@GET
	@Consumes(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	@Path("/perigo")
	public void botaoDoPanico(Usuario usuario) {
		
		// gravar problema no banco de dados		
		
	}
	
	/**Método para atualizar o token do usuario
	 * token que é gerado assim que ele entra no aplicativo	 
	 * 
	 * @author bruno
	 * @category POST
	 * 
	 * @param id_usuario - id do usuario que foi gerado o token
	 * @param token - token gerado assim que ele entra no aplicativo
	 * 
	 *  
     *   exemplo de requisição ao serviço:
	 *   http://localhost:8080/uereapp/rest/usuario/token/2/Asae23As
	 * 
	 * */
	@POST
	@Path("/token/{id_usuario}/{token}")
	public void cadastraToken(@PathParam("id_usuario") Long id_usuario, @PathParam("token") String token) {
		this.usuarios.atualizaToken(id_usuario, token);		
	}

	public static String getCharsetUtf8() {
		return CHARSET_UTF8;
	}

}
