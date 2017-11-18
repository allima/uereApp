package br.edu.ifmg.polo.pedidovenda.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import br.edu.ifmg.polo.pedidovenda.model.Categoria;
import br.edu.ifmg.polo.pedidovenda.repository.Categorias;

@FacesConverter(forClass = Categoria.class)
public class CategoriaConverter implements Converter{

	@Inject
	private Categorias categorias;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Categoria retorno = null;
		
		if (StringUtils.isNotEmpty(value)) {
			Long id = new Long(value);
			retorno = categorias.porId(id);
		}
		
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			
			if (((Categoria) value).getId() != null)
			    return ((Categoria) value).getId().toString();
			else
				return "";
		}
		
		return "";
	}

}