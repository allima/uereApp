package br.edu.ifmg.polo.pedidovenda.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import br.edu.ifmg.polo.pedidovenda.model.Entrega;
import br.edu.ifmg.polo.pedidovenda.repository.Entregas;

@FacesConverter(forClass = Entrega.class)
public class EntregaConverter implements Converter {

	@Inject
	private Entregas entregas;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Entrega retorno = null;
		
		if (StringUtils.isNotEmpty(value)) {
			Long id = new Long(value);
			retorno = entregas.porId(id);
		}
		
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Entrega entrega = (Entrega) value;
			return entrega.getId() == null ? null : entrega.getId().toString();
		}
		
		return "";
	}

}
