package br.edu.ifmg.polo.pedidovenda.controller;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import br.edu.ifmg.polo.pedidovenda.model.Entrega;
import br.edu.ifmg.polo.pedidovenda.model.StatusEntrega;
import br.edu.ifmg.polo.pedidovenda.repository.Entregas;
import br.edu.ifmg.polo.pedidovenda.repository.filter.EntregaFilter;
import br.edu.ifmg.polo.pedidovenda.service.NegocioException;
import br.edu.ifmg.polo.pedidovenda.util.jsf.FacesUtil;

@Named
@ViewScoped
public class PesquisaEntregasBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private Entregas entregas;
	
	private EntregaFilter filtro;
	private LazyDataModel<Entrega> model;
	
	private Entrega entregaSelecionada;
	
	
	public PesquisaEntregasBean() {
		filtro = new EntregaFilter();
		
		model = new LazyDataModel<Entrega>() {

			private static final long serialVersionUID = 1L;
			
			@Override
			public List<Entrega> load(int first, int pageSize, String sortField, SortOrder sortOrder, 
					Map<String, Object> filters) {
				
				filtro.setPrimeiroRegistro(first);
				filtro.setQuantidadeRegistros(pageSize);
				filtro.setPropriedadeOrdenacao(sortField);
				filtro.setAscendente(SortOrder.ASCENDING.equals(sortOrder));
				
				setRowCount(entregas.quantidadeFiltrados(filtro));
				
				return entregas.filtrados(filtro);
			}
			
		};
	}

	
	public void excluir() {
		try {
			entregas.remover(entregaSelecionada);
			//entregasFiltrados.remove(clienteSelecionado);
			
			FacesUtil.addInfoMessage("Entrega " + entregaSelecionada.getId() 
					+ " excluída com sucesso.");
		} catch (NegocioException ne) {
			FacesUtil.addErrorMessage(ne.getMessage());
		}
	}	
	
	
	public void posProcessarXls(Object documento) {
		HSSFWorkbook planilha = (HSSFWorkbook) documento;
		HSSFSheet folha = planilha.getSheetAt(0);
		HSSFRow cabecalho = folha.getRow(0);
		HSSFCellStyle estiloCelula = planilha.createCellStyle();
		Font fonteCabecalho = planilha.createFont();
		
		fonteCabecalho.setColor(IndexedColors.WHITE.getIndex());
		fonteCabecalho.setBold(true);
		fonteCabecalho.setFontHeightInPoints((short) 16);
		
		estiloCelula.setFont(fonteCabecalho);
		estiloCelula.setFillForegroundColor(IndexedColors.BLACK.getIndex());
		estiloCelula.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		
		for (int i = 0; i < cabecalho.getPhysicalNumberOfCells(); i++) {
			cabecalho.getCell(i).setCellStyle(estiloCelula);
		}
	}
	
	public StatusEntrega[] getStatuses() {
		return StatusEntrega.values();
	}
	
	public EntregaFilter getFiltro() {
		return filtro;
	}

	public LazyDataModel<Entrega> getModel() {
		return model;
	}

	public Entrega getEntregaSelecionada() {
		return entregaSelecionada;
	}

	public void setEntregaSelecionada(Entrega entregaSelecionada) {
		this.entregaSelecionada = entregaSelecionada;
	}
	
}