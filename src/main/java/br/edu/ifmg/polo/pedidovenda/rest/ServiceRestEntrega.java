package br.edu.ifmg.polo.pedidovenda.rest;

import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import br.edu.ifmg.polo.pedidovenda.model.vo.EntregaHTTP;
import br.edu.ifmg.polo.pedidovenda.model.vo.RetornoEntregaHTTP;
import br.edu.ifmg.polo.pedidovenda.model.Coordenadas;
import br.edu.ifmg.polo.pedidovenda.model.Empresa;
import br.edu.ifmg.polo.pedidovenda.model.Entrega;
import br.edu.ifmg.polo.pedidovenda.model.ItemEntrega;
import br.edu.ifmg.polo.pedidovenda.model.StatusEntrega;
import br.edu.ifmg.polo.pedidovenda.repository.Empresas;
import br.edu.ifmg.polo.pedidovenda.repository.Entregas;

/**
 * Essa classe vai expor os nossos métodos para serem acessasdos via http
 * 
 * @Path - Caminho para a chamada da classe que vai representar o nosso serviço
 */

@Path("/entrega")
public class ServiceRestEntrega {

	private static final String CHARSET_UTF8 = ";charset=utf-8";

	@Inject
	private Entregas entregas;
	
	@Inject
	private Empresas empresas;
	
	/**Método para retornar 
	 * na hora de sua entrega
	 * 
	 * @produces - determina o formato dos dados que vamos enviar - JSON
	 * 
	 * @author bruno
	 * @category GET
	 * 
	 * @param id_entrega - id da entrega para retornar os itens da entrega
	 * 
	 *  
     *   exemplo de requisição ao serviço:
	 *   http://localhost:8080/uereapp/rest/entrega/64
	 * 
	 * */
	@GET
	@Produces(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	@Path("/{id_entrega}") // recebe o id do vendedor por parametro na url
	public EntregaHTTP getPedidosEntregador(@PathParam("id_entrega") Long id_entrega) {

		try {

			// recebe a entrega
			Entrega entrega = entregas.getListaEntregasPendenteEntregador(id_entrega);

			List<ItemEntrega> lista_itens = entrega.getItens();

			// cria uma classe EntregaHTTP para retorno JSON
			EntregaHTTP entregaHTTP = new EntregaHTTP();
			
			// buscar a origem no banco de dados
			//entregaHTTP.setOrigem(coordenadas_da_empresa);
			// ***********************************

			// converte a lista pedido para lista pedidoHTTP para o retorno
			for (ItemEntrega entity : lista_itens) {

				// add os dados para retorno
				entregaHTTP.getItens()
						.add(new RetornoEntregaHTTP(entity.getId(), entity.getPedido().getValorTotal(),
								StatusEntrega.ACAMINHO, entity.getPedido().getFormaPagamento(),
								entity.getPedido().getCliente().getNome(),
								entity.getPedido().getCliente().getDocumentoReceitaFederal(),
								entity.getPedido().getEnderecoEntrega()));

				// altera o status de cada item
				entity.setStatus(StatusEntrega.ACAMINHO);

			}

			// altera o status da entrega
			entregas.setStatusEntrega(entrega.getId(), StatusEntrega.ACAMINHO);

			// recebe os dados
			entregaHTTP.setId(entrega.getId());
			entregaHTTP.setDataDistribuicao(entrega.getDataDistribuicao());
			entregaHTTP.setStatus(entrega.getStatus());
			entregaHTTP.setValorTotal(entrega.getValorTotal());
			
			//recebe a origem, local da empresa
			Empresa emp = this.empresas.buscarEmpresa();
			
			if(emp != null) {
				Coordenadas coordenadas = new Coordenadas();
				coordenadas.setLatitude(emp.getLatitude());
				coordenadas.setLongitude(emp.getLongitude());
				entregaHTTP.setOrigem(coordenadas);
			}

			// retorna a lista JSON
			return entregaHTTP;

		} catch (Exception e) {
			return null;
		}
	}
	
	/**Método para atualizar o status da entrega
	 * 
	 * @consumes - determina o formato dos dados que vamos tratar - JSON
	 * 
	 * @author bruno
	 * @category POST
	 * 
	 * @param id_entrega - id da entrega + status (ACAMINHO, DEVOLVIDA, ENCERRADO, ENTREGUE, PENDENTE)
	 * 
	 *  
     *   exemplo de requisição ao serviço:
	 *   http://localhost:8080/uereapp/rest/entrega/statusEntrega/64/ACAMINHO
	 * 
	 * */
	@POST
	@Consumes(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	@Path("/statusEntrega/{id_entrega}/{status}") 
	public void statusEntrega(@PathParam("id_entrega") Long id_entrega,  @PathParam("status") String status) {
			
		// verifica qual é o status e atualiza pelo ID da entrega
		if(status.equals("ACAMINHO")) {
			entregas.setStatusEntrega(id_entrega, StatusEntrega.ACAMINHO);
		} else if(status.equals("DEVOLVIDA")) {
			entregas.setStatusEntrega(id_entrega, StatusEntrega.DEVOLVIDA);
		} else if(status.equals("ENCERRADO")) {
			entregas.setStatusEntrega(id_entrega, StatusEntrega.ENCERRADO);
		} else if(status.equals("ENTREGUE")) {
			entregas.setStatusEntrega(id_entrega, StatusEntrega.ENTREGUE);
		}	else if(status.equals("PENDENTE")) {
			entregas.setStatusEntrega(id_entrega, StatusEntrega.PENDENTE);
		}	

		
	}
	
	/**Método para atualizar o status do item da entrega
	 * 
	 * @consumes - determina o formato dos dados que vamos tratar - JSON
	 * 
	 * @author bruno
	 * @category POST
	 * 
	 * @param id_entrega - id do item da entrega + o status (ACAMINHO, DEVOLVIDA, ENCERRADO, ENTREGUE, PENDENTE)
	 * 
	 *  
     *   exemplo de requisição ao serviço:
	 *   http://localhost:8080/uereapp/rest/entrega/statusItemEntrega/10/ACAMINHO
	 * 
	 * */
	@POST
	@Produces(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	@Path("/statusItemEntrega/{id_itemEntrega}/{status}") 
	public void statusItemEntrega(@PathParam("id_itemEntrega") Long id_itemEntrega, @PathParam("status") String status) {
		if(status.equals("ACAMINHO")) {
			entregas.setStatusItemEntrega(id_itemEntrega, StatusEntrega.ACAMINHO);
		} else if(status.equals("DEVOLVIDA")) {
			entregas.setStatusItemEntrega(id_itemEntrega, StatusEntrega.DEVOLVIDA);
		} else if(status.equals("ENCERRADO")) {
			entregas.setStatusItemEntrega(id_itemEntrega, StatusEntrega.ENCERRADO);
		} else if(status.equals("ENTREGUE")) {
			entregas.setStatusItemEntrega(id_itemEntrega, StatusEntrega.ENTREGUE);
		} else if(status.equals("PENDENTE")) {
			entregas.setStatusItemEntrega(id_itemEntrega, StatusEntrega.PENDENTE);
		}		
	}
	
	/**Método para avaliar a entrega por item
	 * 
	 * @consumes - determina o formato dos dados que vamos tratar - JSON
	 * 
	 * @author bruno
	 * @category POST
	 * 
	 * @param id_entrega - id do item da entrega + o valor de sua avaliação de 0 a 5
	 * 
	 *  
     *   exemplo de requisição ao serviço:
	 *   http://localhost:8080/uereapp/rest/entrega/avaliacao/4/5
	 * 
	 * */
	@POST
	@Produces(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	@Path("/avaliacao/{id_itemEntrega}/{valor_avaliacao}") 
	public void avaliaEntrega(@PathParam("id_itemEntrega") Long id_itemEntrega, @PathParam("valor_avaliacao") Integer valor_avaliacao) {
		//atualiza a avaliação da entrega
		entregas.setAvaliacaoItemEntrega(id_itemEntrega, valor_avaliacao);
	}
	
	 /** Método para atualizar a posicao do entregador
	 * 
	 * @consumes - determina o formato dos dados que vamos tratar - JSON
	 * 
	 * @author bruno
	 * @category POST
	 * 
	 * @param Entrega - alguns dados da classe entrega (id_entrega, latitude,longitude, hora da localizacao (classe Date java)
	 * 
	 * {
	 *		"id": 64,
     *		"latitude": -20.4530159,
     *		"longitude": -45.4390024,
     *		"registro": "2018-11-03T15:16:13.356-02:00"
	 * }
	 *  
     *   exemplo de requisição ao serviço:
	 *   http://localhost:8080/uereapp/rest/entrega/localizacao
	 *    
	 * */
	@POST
	@Consumes(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	@Path("/localizacao") // recebe a classe entrega via JSON
	// id = id do entregador
	public void setLocalizacaoEntregador(Entrega entregador) {

		// cria um Entregador para recebre os dados
		Entrega entity = new Entrega();

		try {

			// pega a informação passada
			entity.setId(entregador.getId());
			entity.setLatitude(entregador.getLatitude());
			entity.setLongitude(entregador.getLongitude());
			entity.setRegistro(entregador.getRegistro());
			
			// atualiza a entrega com a localização
			entregas.setLocalizacaoEntregador(entity);

		} catch (Exception e) {
			// Caso ocorra algum erro retorna uma resposta com o status 401 UNAUTHORIZED
		}

	}

	public static String getCharsetUtf8() {
		return CHARSET_UTF8;
	}

}
