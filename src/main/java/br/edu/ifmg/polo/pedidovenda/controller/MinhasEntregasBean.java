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

import br.edu.ifmg.polo.pedidovenda.model.Cliente;
import br.edu.ifmg.polo.pedidovenda.model.Pedido;
import br.edu.ifmg.polo.pedidovenda.model.StatusPedido;
import br.edu.ifmg.polo.pedidovenda.model.Usuario;
import br.edu.ifmg.polo.pedidovenda.repository.Clientes;
import br.edu.ifmg.polo.pedidovenda.repository.Pedidos;
import br.edu.ifmg.polo.pedidovenda.repository.filter.PedidoFilter;
import br.edu.ifmg.polo.pedidovenda.security.Seguranca;

@Named
@ViewScoped
public class MinhasEntregasBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private Pedidos pedidos;
	
    @Inject
    private Seguranca seguranca;	
	
    @Inject
    private Clientes clientes;
    
    
	private Usuario usuario;
    
	private PedidoFilter filtro;
	private LazyDataModel<Pedido> model;
	
	public MinhasEntregasBean() {
		filtro = new PedidoFilter();
		
		model = new LazyDataModel<Pedido>() {

			private static final long serialVersionUID = 1L;
			
			@Override
			public List<Pedido> load(int first, int pageSize, String sortField, SortOrder sortOrder, 
					Map<String, Object> filters) {
				
				filtro.setPrimeiroRegistro(first);
				filtro.setQuantidadeRegistros(pageSize);
				filtro.setPropriedadeOrdenacao(sortField);
				filtro.setAscendente(SortOrder.ASCENDING.equals(sortOrder));
				
				setRowCount(pedidos.quantidadeFiltrados(filtro));
				
				return pedidos.filtrados(filtro);
			}
			
		};
	}

	public void inicializar() {
		
		//Busca os pedidos que tem o mesmo email entre o destinatário e os clientes
		usuario = seguranca.getUsuarioLogado().getUsuario();
		Cliente cli = clientes.porEmail(usuario.getEmail());

		if (cli != null)
		    filtro.setNomeCliente(cli.getNome());		
		else
			filtro.setNomeCliente("Não existe pedidos para o email "
					+usuario.getEmail());
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
	
	public StatusPedido[] getStatuses() {
		return StatusPedido.values();
	}
	
	public PedidoFilter getFiltro() {
		return filtro;
	}

	public LazyDataModel<Pedido> getModel() {
		return model;
	}
	
}