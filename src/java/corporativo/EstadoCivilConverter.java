/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package corporativo;

import ControleMembros.CLN.CDP.EstadoCivil;
import ControleMembros.CLN.CGT.EstadoCivilNegocio;
import exception.NegocioException;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

@FacesConverter(value="estadoCivilConverter", forClass=EstadoCivil.class)
public class EstadoCivilConverter implements Converter {

    public static List<EstadoCivil> estados;

    static {
        try {
            estados = new EstadoCivilNegocio().getLista(null);
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

                for (EstadoCivil p : estados) {
                    if (p.getId() == number) {
                        return p;
                    }
                }
                
            } catch(NumberFormatException exception) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro de conversão", "Estado Civil inválido"));
            }
        }

        return null;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
        if (value == null || value.equals("")) {
            return "";
        } else {
            return String.valueOf(((EstadoCivil) value).getId());
        }
    }
}
