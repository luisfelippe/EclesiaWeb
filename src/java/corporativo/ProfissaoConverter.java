package corporativo;

import ControleMembros.CLN.CDP.Profissao;
import ControleMembros.CLN.CGT.ProfissaoNegocio;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

@FacesConverter(value="profissaoConverter", forClass=Profissao.class)
public class ProfissaoConverter implements Converter {

    public static List<Profissao> profissoes;

    static {
        try {
            profissoes = new ProfissaoNegocio().getLista(null);
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

                for (Profissao p : profissoes) {
                    if (p.getId() == number) {
                        return p;
                    }
                }
                
            } catch(NumberFormatException exception) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro de conversão", "Profissão inválida"));
            }
        }

        return null;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
        if (value == null || value.equals("")) {
            return "";
        } else {
            return String.valueOf(((Profissao) value).getId());
        }
    }
}
