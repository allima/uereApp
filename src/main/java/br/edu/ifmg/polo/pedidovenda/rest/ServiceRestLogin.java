package br.edu.ifmg.polo.pedidovenda.rest;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.edu.ifmg.polo.pedidovenda.model.Usuario;
import br.edu.ifmg.polo.pedidovenda.repository.Usuarios;

/**
 * Essa classe vai expor os nossos métodos para serem acessasdos via http
 * 
 * @Path - Caminho para a chamada da classe que vai representar o nosso serviço
 */

@Path("/login")
public class ServiceRestLogin {

	// para considerar caracteres especiais
	private static final String CHARSET_UTF8 = ";charset=utf-8";

	@Inject
	private Usuarios usuarios;

	/** Método para verificar se o login do usuário esta correto
	 * 
	 * @Consumes - determina o formato dos dados que vamos postar
	 * @Produces - determina o formato dos dados que vamos retornar
	 * 
	 * @author bruno
	 * @category POST
	 * 
	 * @param Usuario - classe usuario que contem email e senha para verificação
	 * 
	 *  exemplo de entrada:
	 *  
	 *  {
	 *    "email" : "b@b.com" ,
	 *	  "senha" : "1234"
	 *  }
	 * 
	 * @return Usuario - caso o login esteja correto, será devolvido a classe usuario
	 *                   com todos os dados, caso esteja errado, retornará null
	 *                   
	 *   exemplo de requisição ao serviço:
	 *   http://localhost:8080/uereapp/rest/login
	 */	
	@POST
	@Produces(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	@Consumes(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	public Usuario logar(Usuario usu_login) {

		// cria um Usuario para receber login e senha
		Usuario entity = new Usuario();

		try {

			// pega a informação passada
			entity.setEmail(usu_login.getEmail());
			entity.setSenha(usu_login.getSenha());	
			
			// função para logar o usuario
			entity = usuarios.logar(usu_login.getEmail(),usu_login.getSenha());
			
			// retorna o usuario que foi logado
			return entity;

		} catch (Exception e) {
			// Caso ocorra algum erro retorna uma resposta com o status 401 UNAUTHORIZED
			return null;
		}
	}

	public static String getCharsetUtf8() {
		return CHARSET_UTF8;
	}

}
