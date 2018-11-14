
package corporativo;

import ControleMembros.CLN.CDP.Membro;
import ControleMembros.CLN.CDP.Profissao;
import ControleMembros.CLN.CGT.MembroNegocio;
import exception.NegocioException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value="MembroConverter", forClass=Membro.class)
public class MembroConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext ctx, UIComponent component, String value) {
        if (value.trim().equals("")) {
            return null;            
        }
        else
        {
            try 
            {
                int number = Integer.valueOf(value);
                Membro p = null;
                List lista = new ArrayList();
                lista.add(0, null);                        
                
                try
                {
                    lista = new MembroNegocio().getLista(number, null);
                    
                    p = (Membro) lista.get(0);
                }
                catch (NegocioException ex) 
                {
                    ex.printStackTrace();
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Erro:", "Não consegui retornar nada."));                
                }
                catch (Exception ex) 
                {
                    ex.printStackTrace();
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro:", "Não consegui retornar nada."));                
                }

                return p;
            }
            catch(NumberFormatException e)
            {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Valor recebido com formato invalido"));
            }
        }
        
        return null;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
        if (value == null || value.equals("")) {
            return "";
        } else {
            return String.valueOf(((Membro) value).getId());
        }
    }
}
