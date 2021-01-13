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
import model.Categoria;
import model.Cidade;
import model.FormaPagamento;

/**
 * <p>CategoriaConverter class.</p>
 *
 * @author Nicoletti
 * @version $Id: $Id
 */
@FacesConverter(value = "formaPagamentoConverter", forClass = FormaPagamento.class)
public class FormaPagamentoConverter implements Converter{
    
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
			FormaPagamento entity = (FormaPagamento) value;

			if (entity.getIdFormaPagamento()!= null) {
				this.addAttribute(component, entity);

				if (entity.getIdFormaPagamento()!= null) {
					return String.valueOf(entity.getIdFormaPagamento());
				}
				return (String) value;
			}
		}
		return "";
	}

	private void addAttribute(UIComponent component, FormaPagamento o) {
		this.getAttributesFrom(component).put(o.getIdFormaPagamento().toString(), o);
	}

	private Map<String, Object> getAttributesFrom(UIComponent component) {
		return component.getAttributes();
	}
    
}
