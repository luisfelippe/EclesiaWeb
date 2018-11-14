
package corporativo;

import ControleMembros.CLN.CDP.Membro;
import ControleMembros.CLN.CDP.Usuario;
import ControleMembros.CLN.CGT.MembroNegocio;
import ControleMembros.CLN.CGT.UsuarioNegocio;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Luis
 */
@ManagedBean
@ViewScoped
public class LoginBean extends ManageBean implements Serializable{
    
    private Usuario usr;
    private String msg;
    
    /** Creates a new instance of LoginBean */
    public LoginBean() {
        this.usr = new UsuarioNegocio().getNovoUsuario();
    }

    public Usuario getUsr() {
        return usr;
    }
    
    public void efetuarLogin(){
        
       try
       {
           Membro memb = new UsuarioNegocio().efetuarLogin(this.usr);
           super.setObjetcSession("usr", memb);
           
           //return "sistema/principal.xhtml";
           FacesContext.getCurrentInstance().getExternalContext().redirect("sistema/principal.xhtml");
       }
       catch(Exception e)
       {
           FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, null, e.getMessage()));
       }
       
       //return null;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }   
}
