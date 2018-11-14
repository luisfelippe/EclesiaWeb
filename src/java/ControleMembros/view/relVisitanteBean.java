/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ControleMembros.view;

import corporativo.ManageBean;
import ControleMembros.CLN.CDP.Visitante;
import ControleMembros.CLN.CGT.VisitanteNegocio;
import exception.NegocioException;
import java.io.Serializable;
import java.util.Date;
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
public class relVisitanteBean extends ManageBean implements Serializable{
    
    private Date dataIni;
    private Date dataFim;
    private List<Visitante> lista;
        
    /**
     * Creates a new instance of aniversarianteBean
     */
    public relVisitanteBean() {
        
    }

    public Date getDataIni() {
        return dataIni;
    }

    public void setDataIni(Date dataIni) {
        this.dataIni = dataIni;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }

    public List<Visitante> getLista() {
        return lista;
    }

    public void setLista(List<Visitante> lista) {
        this.lista = lista;
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
    
    public void reset() {        
        this.dataIni = null;
        this.dataFim = null;
    }
    
    public void buscarAction() {
        this.lista = null;
        
        try 
        {
            this.lista = new VisitanteNegocio().getLista(this.dataIni, this.dataFim);
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
}
