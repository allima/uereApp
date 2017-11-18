package br.edu.ifmg.polo.pedidovenda.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import br.edu.ifmg.polo.pedidovenda.model.Cliente;
import br.edu.ifmg.polo.pedidovenda.model.Usuario;
import br.edu.ifmg.polo.pedidovenda.repository.Usuarios;

@FacesConverter(forClass = Usuario.class)
public class UsuarioConverter implements Converter {

	@Inject
	private Usuarios usuarios;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Usuario retorno = null;

		if (StringUtils.isNotEmpty(value)) {
			retorno = this.usuarios.porId(new Long(value));
		}

		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			if (((Usuario) value).getId() != null)
			    return ((Usuario) value).getId().toString();
			else 
				return "";
		}
		return "";
	}

}