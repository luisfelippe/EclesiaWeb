/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ControleMembros.view;

import corporativo.ManageBean;
import ControleMembros.CLN.CDP.Endereco;
import ControleMembros.CLN.CDP.EstadoCivil;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import ControleMembros.CLN.CDP.Profissao;
import ControleMembros.CLN.CDP.Telefone;
import ControleMembros.CLN.CDP.Visitante;
import ControleMembros.CLN.CGT.EnderecoNegocio;
import ControleMembros.CLN.CGT.TelefoneNegocio;
import ControleMembros.CLN.CGT.VisitanteNegocio;
import exception.NegocioException;
import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author Luis
 */
@ManagedBean
@ViewScoped
public class VisitanteBean extends ManageBean implements Serializable{
    private Visitante visitante;
    private Endereco endereco;
    private List<Profissao> profissoes;
    private List<EstadoCivil> estadosCivis;
    private Telefone telefone1;
    private Telefone telefone2;
    private Telefone telefone3;
       
    /** Creates a new instance of MembroBean */
    public VisitanteBean() {
        try
        {
            this.visitante = new VisitanteNegocio().getNovoVisitante();
            this.telefone1 = new TelefoneNegocio().getNovoTelefone();
            this.telefone2 = new TelefoneNegocio().getNovoTelefone();
            this.telefone3 = new TelefoneNegocio().getNovoTelefone();
            this.endereco = new EnderecoNegocio().getNovoEndereco();
        }
        catch(Exception e)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, null, e.getMessage()));
        }
    }
    
    public void reset() {
        
        if(this.visitante.getId() < 1) {
             this.visitante = new VisitanteNegocio().getNovoVisitante();
             this.telefone1 = new TelefoneNegocio().getNovoTelefone();
             this.telefone2 = new TelefoneNegocio().getNovoTelefone();
             this.telefone3 = new TelefoneNegocio().getNovoTelefone();
             this.endereco = new EnderecoNegocio().getNovoEndereco();
        }
    }

    public Visitante getVisitante() {
        return visitante;
    }

    public void setMembro(Visitante visitante) {
        this.visitante = visitante;
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

    public List<Profissao> getProfissoes() {
        return profissoes;
    }

    public void setProfissoes(List<Profissao> profissoes) {
        this.profissoes = profissoes;
    }

    public List<EstadoCivil> getEstadosCivis() {
        return estadosCivis;
    }

    public void setEstadosCivis(List<EstadoCivil> estadosCivis) {
        this.estadosCivis = estadosCivis;
    }

    public Telefone getTelefone1() {
        return telefone1;
    }

    public void setTelefone1(Telefone telefone1) {
        this.telefone1 = telefone1;
    }

    public Telefone getTelefone2() {
        return telefone2;
    }

    public void setTelefone2(Telefone telefone2) {
        this.telefone2 = telefone2;
    }

    public Telefone getTelefone3() {
        return telefone3;
    }

    public void setTelefone3(Telefone telefone3) {
        this.telefone3 = telefone3;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }
    
    public void salvarAction() {
        List<Telefone> list = new LinkedList<Telefone>(); //reseta a lista de telefones ao criar uma nova e adiciona-la ao membro

        if(!this.telefone1.getNumero().isEmpty())
        {
            list.add(this.telefone1);                
        }

        if(!this.telefone2.getNumero().isEmpty())
        {
            list.add(this.telefone2);                
        }

        if(!this.telefone3.getNumero().isEmpty())
        {
            list.add(this.telefone3);                
        }
            
        this.visitante.setTelefone(list);                
        
        if(!this.endereco.getLogradouro().isEmpty()) {
            List<Endereco> listEnd = new LinkedList<Endereco>();
            listEnd.add(this.endereco);
            
            this.visitante.setEndereco(listEnd);
        }
        
        try 
        {
            new VisitanteNegocio().salvarVisitante(this.visitante);
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, null, "Visitante salvo com sucesso!"));
            
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
}
