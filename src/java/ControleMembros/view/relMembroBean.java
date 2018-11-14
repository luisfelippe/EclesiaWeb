/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ControleMembros.view;

import corporativo.ManageBean;
import ControleMembros.CLN.CDP.Membro;
import ControleMembros.CLN.CGT.MembroNegocio;
import exception.NegocioException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import util.Util;

/**
 *
 * @author luisfelippe
 */
@ManagedBean
@ViewScoped
public class relMembroBean extends ManageBean implements Serializable{
    
    private Membro membroSelected;
    private long cod;
    private String nome;
    private String telefone;
    private String cpf;
    private List<Membro> lista;
    private Date dataIni;
    private Date dataFim;
        
    /**
     * Creates a new instance of aniversarianteBean
     */
    public relMembroBean() {
        
    }
    public List<Membro> getLista() {
        return lista;
    }

    public void setLista(List<Membro> lista) {
        this.lista = lista;
    }

    public long getCod() {
        return cod;
    }

    public void setCod(long cod) {
        this.cod = cod;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }    

    public Membro getMembroSelected() {
        return membroSelected;
    }

    public void setMembroSelected(Membro membroSelected) {
        this.membroSelected = membroSelected;
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
        this.cod = 0;
        this.cpf = null;
        this.nome = null;
        this.telefone = null;
        this.lista = null;
        this.dataFim = null;
        this.dataIni = null;
    }
    
    public void buscarAction() {
        this.lista = null;
        
        try 
        {
            this.lista = new MembroNegocio().getLista(this.cod, this.nome, this.cpf, this.telefone, this.dataIni, this.dataFim);
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
    
    public void editar() {
        String ret = null;
        try
        {
            if(this.membroSelected == null)
                throw new Exception("Nenhum membro selecionado!");   
            
            super.setObjetcSession("membro", this.membroSelected);
            
            //ret = "membros.xhtml";
            FacesContext.getCurrentInstance().getExternalContext().redirect("./membros.xhtml");

        }
        catch(Exception ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, null, ex.getMessage()));
        }
        
        //return ret;
    }
    
    public void excluir(Membro m)
    {        
        try
        {
            new MembroNegocio().excluirMembro(m);    
            FacesContext.getCurrentInstance().getExternalContext().redirect("./relatorioMembro.xhtml");
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
    
    public void addPktAction (Membro membro) {
        Membro mem = (Membro) super.getObjetcSession("usr");
        
        Util.addItemPkt(mem, membro);
        
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, null, "Membro adicionado ao pacote com sucesso!"));

    }
}
