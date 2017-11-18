package br.edu.ifmg.polo.pedidovenda.service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

import br.edu.ifmg.polo.pedidovenda.model.Coordenadas;
import br.edu.ifmg.polo.pedidovenda.model.EnderecoEntrega;
import br.edu.ifmg.polo.pedidovenda.util.RemoveAcentosUtil;

public class GoogleGeocodingService {

	public GoogleGeocodingService() {
		// TODO Auto-generated constructor stub
	}
	
	/** Método criado para buscar um JSON da api google GEOCODE.
	 * O JSON contêm as coordenadas recebidas estraves de endereços passados
	 * como parametros
	 * 
	 * @author Bruno Eustáquio Enes Cardoso
	 * 
	 * @param endereço - Classe de endereço contendo rua, cidade, estado,cep,numero
	 * 
	 * @return Coordenadas - Classe que contem latitude e longitude do endereço
	 * 
	 * */
	public Coordenadas obterCoordenadas(EnderecoEntrega endereco) {

		// classe para receber coordenadas
		Coordenadas coordenadas = new Coordenadas();

		// coloca o endereço em uma só String
		String end = endereco.getLogradouro() + ',' + endereco.getNumero() + "," + endereco.getCidade();

		// Substitui os espaços no endereço informado pelo cliente pelo operador +
		end = end.replace(" ", "+");

		// Realiza a chamada ao método removerAcentos para retirada de
		// acentos e cedilhas da String
		end = RemoveAcentosUtil.removerAcentos(end);

		URL url;

		try {
			// Realiza o acesso a URL do serviço
			//url = new URL("http://maps.googleapis.com/maps/api/geocode/json?address=" + end
			//		+ "&sensor=false");
			
			url = new URL("https://maps.googleapis.com/maps/api/geocode/json?address=" + end
					+ "&key=AIzaSyCugyv_y2hvwopuahCPejbiZ8MuUFRfRds");

			// Cria o Objeto JsonReader e envia como parâmetro o stream de dados obtidos
			// pela classe URL
			JsonReader jsonReader = Json.createReader(new InputStreamReader(url.openStream()));

			// Realiza a leitura e retorna um JsonObject para manipulação inicial
			JsonObject jsonObject = jsonReader.readObject();

			// Após a leitura total da estrutura JSON, realiza o fechamento do Reader
			jsonReader.close();

			String status = jsonObject.getJsonString("status").toString();
			if (status.equals("\"OK\"")) {

				// Obtém o Array de Resultados da consulta ao serviço do Google
				JsonArray arrayResults = jsonObject.getJsonArray("results");

				// Converte o Array de resultados em uma lista de JsonObject
				List<JsonObject> results = arrayResults.getValuesAs(JsonObject.class);

				// Acessa a raiz de conteúdo, dentro do Array de resultados
				JsonObject result = results.get(0);

				// Acessa o objeto Geometry, que detém os dados de latitude e longitude
				// Obtém os dados de latitude e longitude através do objeto location
				JsonObject location = result.getJsonObject("geometry").getJsonObject("location");

				// Atribui o valor do atributo lat(Latitude) ao atributo latitude do
				// objeto coordenadaTO
				coordenadas.setLatitude(location.getJsonNumber("lat").toString());

				// Atribui o valor do atributo lng(Longitude) ao atributo longitude do
				// objeto coordenadaTO
				coordenadas.setLongitude(location.getJsonNumber("lng").toString());
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

		return coordenadas;
	}

}
