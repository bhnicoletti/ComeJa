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
import model.Tamanho;

/**
 * <p>
 * ProdutoConverter class.</p>
 *
 * @author Nicoletti
 * @version $Id: $Id
 */
@FacesConverter(value = "tamanhoConverter", forClass = Tamanho.class)
public class TamanhoConverter implements Converter {

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getAsObject(FacesContext ctx, UIComponent component,
            String value) {
        if (value != null) {
            return this.getAttributesFrom(component).get(value);
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getAsString(FacesContext ctx, UIComponent component,
            Object value) {

        if (value != null && !"".equals(value)) {
            Tamanho entity = (Tamanho) value;

            if (entity.getIdTamanho() != null) {
                this.addAttribute(component, entity);

                if (entity.getIdTamanho() != null) {
                    return String.valueOf(entity.getIdTamanho());
                }
                return (String) value;
            }
        }
        return "";
    }

    private void addAttribute(UIComponent component, Tamanho o) {
        this.getAttributesFrom(component).put(o.getIdTamanho().toString(), o);
    }

    private Map<String, Object> getAttributesFrom(UIComponent component) {
        return component.getAttributes();
    }

}
