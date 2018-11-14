/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlefinanceiro.view;

import controlefinanceiro.cln.cdp.TipoEntrada;
import controlefinanceiro.cln.cgt.TipoEntradaNegocio;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

@FacesConverter(value="tipoEntradaConverter", forClass=TipoEntrada.class)
public class TipoEntradaConverter implements Converter {

    public static List<TipoEntrada> tipos;

    static {
        try {
            tipos = new TipoEntradaNegocio().getLista(null);
        } catch (Exception ex) {
            throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, null, ex.getMessage()));
        }        
    }

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {
        if (submittedValue.trim().equals("")) {
            return null;
        } else {
            try {
                int number = Integer.parseInt(submittedValue);

                for (TipoEntrada t : tipos) {
                    if (t.getId() == number) {
                        return t;
                    }
                }
                
            } catch(NumberFormatException exception) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro de conversão", "Tipo de Entrada inválida!"));
            }
        }

        return null;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
        if (value == null || value.equals("")) {
            return "";
        } else {
            return String.valueOf(((TipoEntrada) value).getId());
        }
    }
}
