package corporativo;

import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import util.Entidade;

/**
 *
 * @author andre.pentago
 */
public class ManageBean implements Serializable {
    
    public void setObjetcSession(String nome, Object object){
        this.getSession().setAttribute(nome, object);
    }
    
    public Object getObjetcSession(String nome){
        return this.getSession().getAttribute(nome);
    }
    
    public FacesContext getFacesContext() {
        return FacesContext.getCurrentInstance();
    }

    public ExternalContext getExternalContext() {
        return FacesContext.getCurrentInstance().getExternalContext();
    }

    public HttpServletRequest getRequest() {
        return (HttpServletRequest) this.getExternalContext().getRequest();
    }

    public HttpServletResponse getResponse() {
        return (HttpServletResponse) this.getExternalContext().getResponse();
    }

    public HttpSession getSession() {
        return (HttpSession) this.getExternalContext().getSession(true);
    }
    
    public void excluir(Entidade item){
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, null, "Método excluir não implementado!"));
    }
    
    public boolean isFormPrincipal() {
        FacesContext fc = FacesContext.getCurrentInstance(); 
        HttpServletRequest request = (HttpServletRequest) fc.getExternalContext().getRequest(); 
        String url = request .getRequestURI(); 
        
        return url.contains("principal");
    }
    
    public boolean isFormSecretaria() {
        FacesContext fc = FacesContext.getCurrentInstance(); 
        HttpServletRequest request = (HttpServletRequest) fc.getExternalContext().getRequest(); 
        String url = request .getRequestURI(); 
        
        return url.contains("membros");
    }

    public boolean isFormFinanceiro() {
        FacesContext fc = FacesContext.getCurrentInstance(); 
        HttpServletRequest request = (HttpServletRequest) fc.getExternalContext().getRequest(); 
        String url = request .getRequestURI(); 
        
        return url.contains("financeiro");
    }
    
    public boolean isFormConfiguracoes() {
        FacesContext fc = FacesContext.getCurrentInstance(); 
        HttpServletRequest request = (HttpServletRequest) fc.getExternalContext().getRequest(); 
        String url = request .getRequestURI(); 
        
        return url.contains("configuracoes");
    }
}
