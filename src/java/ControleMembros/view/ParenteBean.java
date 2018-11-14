/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ControleMembros.view;

import corporativo.ProfissaoConverter;
import corporativo.ManageBean;
import ControleMembros.CLN.CDP.GrauParentesco;
import ControleMembros.CLN.CDP.Membro;
import ControleMembros.CLN.CDP.Pacote;
import ControleMembros.CLN.CDP.Parente;
import ControleMembros.CLN.CDP.Profissao;
import ControleMembros.CLN.CGT.GrauParentescoNegocio;
import ControleMembros.CLN.CGT.MembroNegocio;
import ControleMembros.CLN.CGT.ParenteNegocio;
import exception.NegocioException;
import java.io.Serializable;
import java.util.LinkedList;
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
public class ParenteBean extends ManageBean implements Serializable{

    private long idMembroParente;
    private Parente parente;
    
    private Membro membro;
    
    private List<Profissao> profissoes;
    private long profissaoParenteSel;
    
    private long parentescoParenteSel;    
    private List<GrauParentesco> parentescos;
    
    /**
     * Creates a new instance of ParenteBean
     */
    public ParenteBean() {
        
        try 
        {
            this.parente = new ParenteNegocio().getNovoParente();
            this.profissoes = ProfissaoConverter.profissoes;
            this.parentescos = new GrauParentescoNegocio().getLista();
        } 
        catch (Exception ex) 
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, null, ex.getMessage()));
        }
    }
    
//    public List<Membro> getMembrosPacote () {
//        Membro mem = (Membro) super.getObjetcSession("usr");
//        Pacote pkt = mem.getPacote();
//        
//        List<Membro> lista = new LinkedList<Membro>();
//        
//        for(int i = 0; i < pkt.getConteudo().size(); i++)
//        {
//            if(pkt.getConteudo().get(i) instanceof Membro)
//                lista.add((Membro) pkt.getConteudo().get(i));
//        }
//        
//        return lista;
//    }

    public long getIdMembroParente() {
        return idMembroParente;
    }

    public void setIdMembroParente(long idMembroParente) {
        this.idMembroParente = idMembroParente;
    }

    public Parente getParente() {
        return parente;
    }

    public void setParente(Parente parente) {
        this.parente = parente;
    }     

    public long getProfissaoParenteSel() {
        return profissaoParenteSel;
    }

    public void setProfissaoParenteSel(long profissaoParenteSel) {
        this.profissaoParenteSel = profissaoParenteSel;
    }

    public long getParentescoParenteSel() {
        return parentescoParenteSel;
    }

    public void setParentescoParenteSel(long parentescoParenteSel) {
        this.parentescoParenteSel = parentescoParenteSel;
    }

    public List<GrauParentesco> getParentescos() {
        return parentescos;
    }

    public void setParentescos(List<GrauParentesco> parentescos) {
        this.parentescos = parentescos;
    }

    public List<Profissao> getProfissoes() {
        return profissoes;
    }

    public void setProfissoes(List<Profissao> profissoes) {
        this.profissoes = profissoes;
    }    
    
    public List<Membro> getMembrosPacote () {
        Membro mem = (Membro) super.getObjetcSession("usr");
        Pacote pkt = mem.getPacote();
        
        List<Membro> lista = new LinkedList<Membro>();
        
        for(int i = 0; i < pkt.getConteudo().size(); i++)
        {
            if(pkt.getConteudo().get(i) instanceof Membro)
                lista.add((Membro) pkt.getConteudo().get(i));
        }
        
        return lista;
    }

    public Membro getMembro() {
        return membro;
    }

    public void setMembro(Membro membro) {
        this.membro = membro;
    }
    
    public void limparAction() {
        this.parente = new Parente();
        this.idMembroParente = 0;
        this.parentescoParenteSel = 0;
        this.profissaoParenteSel = 0;        
    }
    
    //método responsável de pegar os dados do membro selecionado no pacote e atribuir os respectivos valores ao parente
    public void atualizaParente() {
        if(this.idMembroParente > 0) {
            Membro mem = Util.getMembroPktSelecionado(this.getMembrosPacote(), this.idMembroParente);
            
            this.parente.setDtNasc(mem.getDataNasc());
            this.parente.setMasculino(mem.getMasculino());
            this.parente.setNome(mem.getNome());
            this.parente.setParentesco(null);
        }
    }
    
    public void salvarAction() {
        
        try
        {
            this.parente.setParentesco( (GrauParentesco) Util.getItemSelecionado(this.parentescos, (int) this.parentescoParenteSel));
            this.parente.setProfissao(Util.getProfissaoSelecionado(this.profissoes, this.profissaoParenteSel));
            
            //informa que o parente é um membro já cadastrado
            this.parente.setParenteMembro(Util.getMembroPktSelecionado(this.getMembrosPacote(), this.idMembroParente));
            
            /**
             * @TODO: informar ao membro adicionado como parente que ele é parente do membro atual
             */
            
            //adiciona o parente a lista de parentes do membro
            this.membro.getParentes().add(this.parente);

            new MembroNegocio().salvarMembro(this.membro);            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, null, "Membro salvo com sucesso!"));
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
