/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlefinanceiro.view;

import ControleMembros.CLN.CDP.Membro;
import exception.NegocioException;
import corporativo.ManageBean;
import controlefinanceiro.cln.cdp.Saida;
import controlefinanceiro.cln.cdp.TipoSaida;
import controlefinanceiro.cln.cgt.SaidaNegocio;
import controlefinanceiro.cln.cgt.TipoSaidaNegocio;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import util.Util;

/**
 *
 * @author LuísFelippe
 */
@ManagedBean
@ViewScoped
public class SaidaBean extends ManageBean{
    private Saida saida;
    private List<TipoSaida> tipos;
    private long idTipo;
    
    public SaidaBean() {
        this.saida = new SaidaNegocio().getNovaSaida();
        
        try
        {
            this.tipos = new TipoSaidaNegocio().getLista(null);
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

    public Saida getSaida() {
        return saida;
    }

    public void setSaida(Saida saida) {
        this.saida = saida;
    }   
    
    public void addSaida() {
        
        this.saida.setUsuario((Membro) super.getObjetcSession("usr"));
        
        try 
        {
            new SaidaNegocio().addSaida(this.saida);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, null, "Saída adicionada com sucesso!"));
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
    
    public void limpar() {
        this.saida = new SaidaNegocio().getNovaSaida();
        this.tipos = TipoSaidaConverter.tipos;
        this.idTipo = 0;
    }
    
    public void cancelar()
    {
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

    public List<TipoSaida> getTipos() {
        return tipos;
    }

    public void setTipos(List<TipoSaida> tipos) {
        this.tipos = tipos;
    }

    public long getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(long idTipo) {
        this.idTipo = idTipo;
        this.saida.setTipo((TipoSaida) Util.getItemSelecionado(this.tipos, (int) this.idTipo));
    }
    
    
}
