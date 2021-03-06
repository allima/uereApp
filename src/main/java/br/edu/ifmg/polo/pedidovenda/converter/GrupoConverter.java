package br.edu.ifmg.polo.pedidovenda.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import br.edu.ifmg.polo.pedidovenda.model.Grupo;
import br.edu.ifmg.polo.pedidovenda.model.Produto;
import br.edu.ifmg.polo.pedidovenda.repository.Grupos;

@FacesConverter(forClass = Grupo.class)
public class GrupoConverter implements Converter {

	@Inject
	private Grupos grupos;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Grupo retorno = null;
		
		if (StringUtils.isNotEmpty(value)) {
			Long id = new Long(value);
			retorno = grupos.porId(id);
		}
		
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			if (((Grupo) value).getId() != null){
			  Grupo grupo = (Grupo) value;
			  return grupo.getId() == null ? null : grupo.getId().toString();
			}
			else return "";
		}
		
		return "";
	}

}
