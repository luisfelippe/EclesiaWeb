/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package corporativo;

import ControleMembros.CLN.CDP.Membro;
import ControleMembros.CLN.CDP.Usuario;
import ControleMembros.CLN.CGT.MembroNegocio;
import ControleMembros.CLN.CGT.UsuarioNegocio;
import exception.NegocioException;
import java.io.Serializable;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author luisfelippe
 */
@ManagedBean
@ViewScoped
public class usuarioBean extends ManageBean implements Serializable{
    
    private Membro usrSelected;
    private List<Membro> lista;
    private String nome;
    private long cod;
    private Usuario usr;

    /**
     * Creates a new instance of usuarioBean
     */
    public usuarioBean() {
        this.nome = new String();
        //this.buscarAction();        
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public long getCod() {
        return cod;
    }

    public void setCod(long cod) {
        this.cod = cod;
    }

    public Membro getUsrSelected() {
        return usrSelected;
    }

    public void setUsrSelected(Membro usrSelected) {
        this.usrSelected = usrSelected;
        
        try 
        {
            this.usr = new UsuarioNegocio().getUsuario(this.usrSelected);
        } 
        catch (Exception ex) 
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, null, ex.getMessage()));
        }
    }

    public List<Membro> getLista() {
        return lista;
    }

    public void setLista(List<Membro> lista) {
        this.lista = lista;
    }

    public Usuario getUsr() {
        return usr;
    }

    public void setUsr(Usuario usr) {
        this.usr = usr;
    }
    
    public void cancelar() {
        //return "principal";
        try 
        {
            FacesContext.getCurrentInstance().getExternalContext().redirect("./principal.xhtml");
        }
        catch (Exception ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, null, ex.getMessage()));
        }
    }
    
    public void cancelar2() {
        this.usrSelected = null;
    }
    
    public void reset() {        
        this.cod = 0;
        this.nome = null;
    }
    
    public void buscarAction() {
        try 
        {                    
            if(this.nome.isEmpty() && this.cod == 0)
                this.lista = new MembroNegocio().getLista(null);
            else if(!this.nome.isEmpty() && this.cod == 0)
                this.lista = new MembroNegocio().getLista(this.cod, this.nome);
            else if(this.nome.isEmpty() && this.cod > 0 )
                this.lista = new MembroNegocio().getLista(this.cod, this.nome);
            else
                this.lista = new MembroNegocio().getLista(this.cod, this.nome);
            
            //System.out.println(this.lista.size());
        }
        catch(NegocioException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, null, ex.getMessage()));
        }
        catch(Exception ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, null, ex.getMessage()));
        }        
    }
    
    public void salvarAction(){
        this.usr.setMembro(this.usrSelected);
        
        try
        {
            new UsuarioNegocio().salvarUsuario(this.usr);
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, null, "Usuário salvo com sucesso!"));            
        } 
        catch (NegocioException ex) 
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, null, ex.getMessage()));
        }
        catch (Exception ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, null, ex.getMessage()));
        }
    }
    
    public void excluirAction(){
        try
        {
            new UsuarioNegocio().excluirUsuario(this.usr);
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, null, "Usuário excluído com sucesso!"));            
        }
        catch(Exception ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, null, ex.getMessage()));
        }
    }
}
