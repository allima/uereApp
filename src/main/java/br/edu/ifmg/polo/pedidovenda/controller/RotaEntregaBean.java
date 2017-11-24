package br.edu.ifmg.polo.pedidovenda.controller;

import java.io.Serializable;
import java.util.ArrayList;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.context.RequestContext;
import br.edu.ifmg.polo.pedidovenda.model.Coordenadas;
import br.edu.ifmg.polo.pedidovenda.model.Empresa;
import br.edu.ifmg.polo.pedidovenda.model.Entrega;
import br.edu.ifmg.polo.pedidovenda.model.ItemEntrega;
import br.edu.ifmg.polo.pedidovenda.model.TempoEntrega;
import br.edu.ifmg.polo.pedidovenda.repository.Empresas;
import br.edu.ifmg.polo.pedidovenda.repository.Entregas;
import br.edu.ifmg.polo.pedidovenda.service.DistanciaService;
import br.edu.ifmg.polo.pedidovenda.service.GoogleGeocodingService;
import br.edu.ifmg.polo.pedidovenda.util.jsf.FacesUtil;

@Named
@ViewScoped
public class RotaEntregaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Entregas entregas;

	@Inject
	private Empresas empresas;

	// @Produces
	// @EntregaEdicao
	private Entrega entrega;

	private Coordenadas coord;
	private ArrayList<Coordenadas> coordenadas = new ArrayList<Coordenadas>();
	private CommandButton c = new CommandButton();
	private Long id_entrega;
	private boolean retornaPos = false;
	private String ordemEntregas = "";
	private DistanciaService distancia = new DistanciaService();
	private ArrayList<TempoEntrega> tempoEntregas;
	private boolean tabelaDistancia = false;

	public RotaEntregaBean() {

	}

	public void inicializar() {

		// pesquisa no banco de dados as entregas por id_entrega
		entrega = entregas.porId(getId_entrega());

		// ira receber as coordenadas
		coord = new Coordenadas();

		// preenche o array de coordenadas, de acordo com cada item
		for (ItemEntrega i : entrega.getItens()) {
			coord.setLatitude(i.getPedido().getEnderecoEntrega().getLatitude());
			coord.setLongitude(i.getPedido().getEnderecoEntrega().getLongitude());
			coordenadas.add(coord);
			coord = new Coordenadas();
		}

	}

	public void gerarMapa() {

		Empresa emp = empresas.buscarEmpresa();
		Coordenadas coordInicial = new Coordenadas();
		// se existe empresa cadastrada, passo as coordenadas dela
		// caso nao exista, o JS pegará a localização do navegador
		if (emp != null) {			
			coordInicial.setLatitude(emp.getLatitude());
			coordInicial.setLongitude(emp.getLongitude());
		}

		// é passado as coordenadas da entrega para a função javascript e gerado o mapa
		RequestContext context = RequestContext.getCurrentInstance();
		context.addCallbackParam("coord", new org.primefaces.json.JSONArray(coordenadas));
		context.addCallbackParam("localizacao", coordInicial);
	}

	public void atualizaPosicao() {

		// inicializa a lista que irá contem o tempo e distancia de cada item
		tempoEntregas = new ArrayList<>();

		// recebe a localização do entregador no momento atual
		Coordenadas loc_entregador = entregas.getEntrega(entrega.getId());

		// caso não haja coordenadas do entregador
		// busca a coordenada da empresa
		if (loc_entregador == null) {

			// recebe a origem, local da empresa
			Empresa emp = this.empresas.buscarEmpresa();

			if (emp != null) {
				loc_entregador.setLatitude(emp.getLatitude());
				loc_entregador.setLongitude(emp.getLongitude());
			} else {
				FacesUtil.addErrorMessage(
						"Impossível gerar Rota: Localização do Entregador ou da Empresa Indisponivel!!");
				return;
			}
		}

		// envia a localização para o javascript atualizar a posição
		// do entregador no mapa
		RequestContext context = RequestContext.getCurrentInstance();
		context.addCallbackParam("coord", loc_entregador);

		// pesquisa no banco de dados as entregas por id_entrega
		entrega = entregas.porId(getId_entrega());

		// aqui contem a ordem que a rota foi gerada na função geraMapa
		// está rota é gerada pelo javaScript, então pegamos a ordem por esta string
		// que está separado por ',' - 0,1,3,2 (essas são as ordens do iten_entregas
		// recuperados no BD)
		String ordem[] = ordemEntregas.split(",");

		if (ordem[0] == "") {
			FacesUtil.addErrorMessage("A rota não foi gerada!!");
		} else {

			// cria uma nova Classe que está sendo usada no RotaEntrega.xhtml para preencher
			// a tabela
			TempoEntrega aux = new TempoEntrega();

			// percorre a ordem
			for (int i = 0; i < ordem.length; i++) {

				// set os dados de acordo com a ordem dos itens

				// letra que é gerado no Mapa
				aux.setPontoMapa(Integer.toHexString(11 + i));
				// coordenadas para buscar a distancia e tempo do item
				aux.setCoordenadas(coordenadas.get(Integer.parseInt(ordem[i])));
				// nome do cliente de acordo com a ordem de rota
				aux.setNomeCliente(
						entrega.getItens().get(Integer.parseInt(ordem[i])).getPedido().getCliente().getNome());
				// ordem de entrega
				aux.setOrdemMapa(i);
				// status do item
				aux.setStatus(entrega.getItens().get(Integer.parseInt(ordem[i])).getStatus());
				// add na lista que é usada no RotaEntrega.xhtml para preencher a tabela
				tempoEntregas.add(aux);

				aux = new TempoEntrega();
			}

			// irá receber e preencher as distancias e tempos recebidos
			// envia a localização do entregador e a lista de coordenas de entrega
			distancia.retornaDistancia(loc_entregador, tempoEntregas);
			// flag para mostrar a tabela de dados
			this.tabelaDistancia = true;

			// atualizo a duracao e distancia caso a entrega ja tenha sido realizada
			for (int i = 0; i < tempoEntregas.size(); i++) {

				if ((tempoEntregas.get(i).getStatus().equals("DEVOLVIDA"))
						|| (tempoEntregas.get(i).getStatus().equals("ENTREGUE"))
						|| (tempoEntregas.get(i).getStatus().equals("ENCERRADO"))) {
					tempoEntregas.get(i).setDistancia("--");
					tempoEntregas.get(i).setDistancia("--");
				}

			}

		}

	}

	/**
	 * Método criado para buscar as entregas cadastradas no banco de dados. A partir
	 * dos endereços das entregas, é consultado a classe GoogleGeocodingService para
	 * obter as coordenadas em Latidude e Longitude
	 * 
	 * @author Bruno Eustáquio Enes Cardoso
	 * 
	 * @param getId_entrega
	 *            - id da entrega que será pesquisada
	 * 
	 * @return ArrayList - Lista contendo os dados entrega juntamente com suas
	 *         coordenadas
	 * 
	 */
	public void recuperaEntregas() {

	}

	public Entrega getEntrega() {
		return entrega;
	}

	public void setEntrega(Entrega entrega) {
		this.entrega = entrega;
	}

	public Long getId_entrega() {
		return id_entrega;
	}

	public void setId_entrega(Long id_entrega) {
		this.id_entrega = id_entrega;
	}

	public Entregas getEntregas() {
		return entregas;
	}

	public void setEntregas(Entregas entregas) {
		this.entregas = entregas;
	}

	public Coordenadas getCoord() {
		return coord;
	}

	public void setCoord(Coordenadas coord) {
		this.coord = coord;
	}

	public ArrayList<Coordenadas> getCoordenadas() {
		return coordenadas;
	}

	public void setCoordenadas(ArrayList<Coordenadas> coordenadas) {
		this.coordenadas = coordenadas;
	}

	public CommandButton getC() {
		return c;
	}

	public void setC(CommandButton c) {
		this.c = c;
	}

	public boolean isRetornaPos() {
		return retornaPos;
	}

	public void setRetornaPos(boolean retornaPos) {
		this.retornaPos = retornaPos;
	}

	public String getOrdemEntregas() {
		return ordemEntregas;
	}

	public void setOrdemEntregas(String ordemEntregas) {
		this.ordemEntregas = ordemEntregas;
	}

	public ArrayList<TempoEntrega> getTempoEntrega() {
		return tempoEntregas;
	}

	public void setTempoEntrega(ArrayList<TempoEntrega> tempoEntrega) {
		this.tempoEntregas = tempoEntrega;
	}

	public DistanciaService getDistancia() {
		return distancia;
	}

	public void setDistancia(DistanciaService distancia) {
		this.distancia = distancia;
	}

	public ArrayList<TempoEntrega> getTempoEntregas() {
		return tempoEntregas;
	}

	public void setTempoEntregas(ArrayList<TempoEntrega> tempoEntregas) {
		this.tempoEntregas = tempoEntregas;
	}

	public boolean isTabelaDistancia() {
		return tabelaDistancia;
	}

	public void setTabelaDistancia(boolean tabelaDistancia) {
		this.tabelaDistancia = tabelaDistancia;
	}

}