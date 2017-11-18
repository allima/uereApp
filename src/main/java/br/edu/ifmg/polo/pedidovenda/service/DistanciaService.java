package br.edu.ifmg.polo.pedidovenda.service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import br.edu.ifmg.polo.pedidovenda.model.Coordenadas;
import br.edu.ifmg.polo.pedidovenda.model.StatusEntrega;
import br.edu.ifmg.polo.pedidovenda.model.TempoEntrega;

public class DistanciaService {

	// chave para usar a API google distancematrix
	private final String KEY = "AIzaSyCiu2eZh2v6fdgZBuvbpeOxxyW_T_e8A0k";

	public DistanciaService() {

	}

	/**
	 * Método criado para buscar um JSON da api google Distance Matrix. O JSON
	 * contêm as distâncias e o tempo de viagem de um ponto(s) a outro(s)
	 * 
	 * @author Bruno Eustáquio Enes Cardoso
	 * 
	 * @param entregador
	 *            - Coordenadas do ponto de origem
	 * @param tempoEntregas
	 *            - Lista de coodenadas com os pontos de entregas (destinos)
	 * 
	 * @return ArrayList - Lista contendo os valores de distancia e tempo de cada
	 *         entrega
	 * 
	 */
	public ArrayList<TempoEntrega> retornaDistancia(Coordenadas entregador, ArrayList<TempoEntrega> tempoEntregas) {

		// construção da url
		String origemEntregador = "&origins=" + entregador.getLatitude() + "," + entregador.getLongitude();
		String destinoEntregas = "&destinations=";

		// pega os coordenadas de destino
		for (int i = 0; i < tempoEntregas.size(); i++) {
			destinoEntregas += tempoEntregas.get(i).getCoordenadas().getLatitude();
			destinoEntregas += ",";
			destinoEntregas += tempoEntregas.get(i).getCoordenadas().getLongitude();
			destinoEntregas += "|";
		}

		URL url = null;

		try {
			// Realiza o acesso a URL do serviço da google
			url = new URL("https://maps.googleapis.com/maps/api/distancematrix/json?" + origemEntregador
					+ destinoEntregas + "&key=" + KEY);

			// Cria o Objeto JsonReader e envia como parâmetro o stream de dados obtidos
			// pela classe URL
			JsonReader jsonReader = Json.createReader(new InputStreamReader(url.openStream()));

			// Realiza a leitura e retorna um JsonObject para manipulação inicial
			JsonObject jsonObject = jsonReader.readObject();

			// Após a leitura total da estrutura JSON, realiza o fechamento do Reader
			jsonReader.close();

			// verifica se o retorno está OK, ou seja, se retornou o que pedimos
			String status = jsonObject.getJsonString("status").toString();
			if (status.equals("\"OK\"")) {

				// Obtém o Array de Resultados da consulta ao serviço do Google no parametro
				// 'rows'
				List<JsonObject> results = jsonObject.getJsonArray("rows").getValuesAs(JsonObject.class);

				// Converte para Array Json a lista que está no parametro 'elements'
				JsonArray array = results.get(0).getJsonArray("elements");

				// converte em uma lista de objeto
				List<JsonObject> result = array.getValuesAs(JsonObject.class);

				// percorre a lista que contem os resultados
				for (int i = 0; i < result.size(); i++) {

					// recebe cada resultado
					JsonObject aux = result.get(i);

					// recupera a distancia e a duracao
					tempoEntregas.get(i).setDistancia(aux.getJsonObject("distance").getString("text").toString());
					tempoEntregas.get(i).setTempo(aux.getJsonObject("duration").getString("text").toString());

				}

				// retorno a lista preenchida
				return tempoEntregas;

			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

		return null;
	}

	public String getKEY() {
		return KEY;
	}

}
