package br.edu.ifmg.polo.pedidovenda.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HTTP;
import org.primefaces.json.JSONObject;


public class EnviaRequisicao {

	public EnviaRequisicao() {
		// TODO Auto-generated constructor stub
	}

	/** Metodo que enviará uma mensagem para um determinado app
	 * 
	 * @author bruno
	 * 
	 * @category POST
	 * 
	 * @param token - token do app em questao
	 * @param mensagem - mensagem que será enviada para o app
	 * @param key - chave do servidor firebase
	 * 
	 *  exemplo JSON
	 * { 
	 * 		"to": "bk3RNwTe3H0:CI2k_HHwgIpoDKCIZvvDMExUdFQ3P1"
	 * 		"notification": { 
	 * 							"title": "Pedido", 
	 * 							"body": "menssagem"
	 * 						},
	 * }
	 * 
	 * 
	 */
	public void enviaPost(String token, String menssagem, String key) {

		// url que será enviado os dados
		String postUrl = "https://fcm.googleapis.com/fcm/send";

		// metodo para enviar a requisicao
		HttpClient client = HttpClients.createDefault();
		HttpResponse response;

		// objeto json com os dados
		JSONObject json = new JSONObject();

		try {
			// será enviado via post
			HttpPost post = new HttpPost(postUrl);
			// cabeçalho do post - tipo de envio
			post.addHeader(HTTP.CONTENT_TYPE, "application/json");
			// cabeçalho do post - chave do servidor
			post.addHeader("Authorization", "key=" + key);

			// preenchendo o body json - token do app que ira receber a notificação
			json.put("to", token);

			// preenchendo o body json aux - mensagem
			JSONObject aux = new JSONObject();
			aux.put("title", "Pedido");
			aux.put("body", menssagem);
			
			// json que será enviado
			json.put("notification", aux);

			// converte o objeto json
			StringEntity params = new StringEntity(json.toString());

			// add a requisicao
			post.setEntity(params);

			// envia a requisicao
			response = client.execute(post);

			if (response != null) {
				System.out.println(response.getEntity().toString());
			}

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
