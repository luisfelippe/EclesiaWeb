/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlefinanceiro.view;

import controlefinanceiro.cln.cdp.TipoSaida;
import controlefinanceiro.cln.cgt.TipoSaidaNegocio;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

@FacesConverter(value="tipoSaidaConverter", forClass=TipoSaida.class)
public class TipoSaidaConverter implements Converter {

    public static List<TipoSaida> tipos;

    static {
        try {
            tipos = new TipoSaidaNegocio().getLista(null);
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

                for (TipoSaida t : tipos) {
                    if (t.getId() == number) {
                        return t;
                    }
                }
                
            } catch(NumberFormatException exception) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro de conversão", "Tipo de Saída inválida!"));
            }
        }

        return null;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
        if (value == null || value.equals("")) {
            return "";
        } else {
            return String.valueOf(((TipoSaida) value).getId());
        }
    }
}
