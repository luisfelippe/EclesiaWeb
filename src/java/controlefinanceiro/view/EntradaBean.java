
package controlefinanceiro.view;

import ControleMembros.CLN.CDP.Membro;
import ControleMembros.CLN.CGT.MembroNegocio;
import exception.NegocioException;
import corporativo.ManageBean;
import controlefinanceiro.cln.cdp.Entrada;
import controlefinanceiro.cln.cdp.TipoEntrada;
import controlefinanceiro.cln.cgt.EntradaNegocio;
import controlefinanceiro.cln.cgt.TipoEntradaNegocio;
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
public class EntradaBean extends ManageBean{
    private Membro membro;
    private Entrada entrada;
    private List<TipoEntrada> tipos;
    private long idTipo;
    private int opcao = 1;
    
    public EntradaBean() 
    {
        this.membro = new MembroNegocio().getNovoMembro();
        this.entrada = new EntradaNegocio().getNovaEntrada();
        
        try
        {
            this.tipos = new TipoEntradaNegocio().getLista(null);
        }
        catch (NegocioException ex) 
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, null, ex.getMessage()));
        } 
        catch (Exception ex) 
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, null, ex.getMessage()));
        }
    }

    public Membro getMembro() {
        return membro;
    }

    public void setMembro(Membro membro) {
        this.membro = membro;
    }

    public Entrada getEntrada() {
        return entrada;
    }

    public void setEntrada(Entrada entrada) {
        this.entrada = entrada;
    }

    public long getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(long idTipo) {
        this.idTipo = idTipo;
        this.entrada.setTipo((TipoEntrada) Util.getItemSelecionado(this.tipos, (int) this.idTipo));
    }

    public int getOpcao() {
        return opcao;
    }

    public void setOpcao(int opcao) {
        this.opcao = opcao;
    }
    
    public void buscaMembro(){
        
        Membro memb = new MembroNegocio().getNovoMembro();

        try 
        {   
            memb = new MembroNegocio().getMembro(this.membro.getId());
            
            if(memb != null)
                this.membro = memb;
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
    
    public void addEntrada() {
        
        if(this.membro.getId() > 0)
            this.entrada.setMembro(this.membro);
        
        this.entrada.setUsuario((Membro) super.getObjetcSession("usr"));
        
        try 
        {
            new EntradaNegocio().addEntrada(this.entrada);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, null, "Entrada adicionada com sucesso!"));
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
        this.membro = new MembroNegocio().getNovoMembro();
        this.entrada = new EntradaNegocio().getNovaEntrada();
        this.tipos = TipoEntradaConverter.tipos;
        this.idTipo = 0;
    }
    
    public void cancelar(){
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

    public List<TipoEntrada> getTipos() {
        return tipos;
    }

    public void setTipos(List<TipoEntrada> tipos) {
        this.tipos = tipos;
    }
    
    
}
