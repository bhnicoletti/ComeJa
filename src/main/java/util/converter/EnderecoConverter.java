/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.converter;

import java.util.Map;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import model.Endereco;


/**
 * <p>EnderecoConverter class.</p>
 *
 * @author Nicoletti
 * @version $Id: $Id
 */
@FacesConverter(value = "enderecoConverter", forClass = Endereco.class)
public class EnderecoConverter implements Converter{
    
   /** {@inheritDoc} */
   @Override
   public Object getAsObject(FacesContext ctx, UIComponent component,
			String value) {
		if (value != null) {
			return this.getAttributesFrom(component).get(value);
		}
		return null;
	}

   /** {@inheritDoc} */
   @Override
	public String getAsString(FacesContext ctx, UIComponent component,
			Object value) {

		if (value != null && ! "".equals(value)) {
			Endereco entity = (Endereco) value;

			if (entity.getIdEndereco()!= null) {
				this.addAttribute(component, entity);

				if (entity.getIdEndereco() != null) {
					return String.valueOf(entity.getIdEndereco());
				}
				return (String) value;
			}
		}
		return "";
	}

	private void addAttribute(UIComponent component, Endereco o) {
		this.getAttributesFrom(component).put(o.getIdEndereco().toString(), o);
	}

	private Map<String, Object> getAttributesFrom(UIComponent component) {
		return component.getAttributes();
	}
    
}
