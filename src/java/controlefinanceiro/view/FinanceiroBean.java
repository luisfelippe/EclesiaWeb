
package controlefinanceiro.view;

import ControleMembros.CLN.CDP.Membro;
import ControleMembros.CLN.CGT.MembroNegocio;
import controlefinanceiro.cln.cdp.CategoriaLancamento;
import controlefinanceiro.cln.cdp.Lancamento;
import controlefinanceiro.cln.cdp.TipoLancamento;
import controlefinanceiro.cln.cgt.CategoriaLancamentoNegocio;
import controlefinanceiro.cln.cgt.LancamentoNegocio;
import corporativo.ManageBean;
import exception.NegocioException;
import java.io.Serializable;
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
public class FinanceiroBean extends ManageBean implements Serializable {
    private Membro membro;
    private List<CategoriaLancamento> categorias;
    private long idCategoriaSel;
    private TipoLancamento[] opcoes = TipoLancamento.values();
    private TipoLancamento opcao;
    private Lancamento lancamento;
    
    public FinanceiroBean() 
    {
        //this.membro = new MembroNegocio().getNovoMembro();
        
        try
        {
            this.limpar();
            this.lancamento = null;
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

    public List<CategoriaLancamento> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<CategoriaLancamento> categorias) {
        this.categorias = categorias;
    }

    public long getIdCategoriaSel() {
        return idCategoriaSel;
    }

    public void setIdCategoriaSel(long idCategoriaSel) {
        this.idCategoriaSel = idCategoriaSel;
        this.lancamento.setCategoria((CategoriaLancamento) Util.getItemSelecionado(this.categorias, (int) this.idCategoriaSel));
    }

    public TipoLancamento getOpcao() {
        return opcao;
    }

    public void setOpcao(TipoLancamento opcao) {
        this.opcao = opcao;        
        this.lancamento.setTipo(this.opcao);
        
        //System.out.println(this.lancamento.getTipo().getDescricao());
    }

    public TipoLancamento[] getOpcoes() {
        return opcoes;
    }

    public void setOpcoes(TipoLancamento[] opcoes) {
        this.opcoes = opcoes;
    }

    public Lancamento getLancamento() {
        return lancamento;
    }

    public void setLancamento(Lancamento lancamento) {
        this.lancamento = lancamento;
    }
    
    public void buscaMembro(){
        
        Membro memb;

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
    
    public void addLancamento() {
        
        if(this.membro != null && this.membro.getId() > 0 && this.opcao == TipoLancamento.ENTRADA)
            this.lancamento.setMembro(this.membro);
        
        this.lancamento.setAutor((Membro) super.getObjetcSession("usr"));
        
        try 
        {
            //System.out.println(">>>"+this.lancamento.getTipo().getDescricao());
            
            new LancamentoNegocio().addLancamento(this.lancamento);
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, null, "Lançamento adicionado com sucesso!"));
        } 
        catch (NegocioException ex) 
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, null, ex.getMessage()));
        } 
        catch (Exception ex) 
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, null, ex.getMessage()));
            ex.printStackTrace();
        }        
    }
    
    public void atualizaListaCategoria() {
        this.categorias = null;
        
        try
        {
            this.categorias = new CategoriaLancamentoNegocio().getLista(this.opcao);
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
        this.membro = null;
        this.lancamento = new LancamentoNegocio().getNovoLancamento();
        this.opcao = TipoLancamento.ENTRADA; //padrão é entrada
        this.idCategoriaSel = 0;      
        
        this.atualizaListaCategoria();
    }
    
    public void cancelar(){
        //return "principal";
        try 
        {
            this.limpar();
            
            this.lancamento = null;
//            FacesContext.getCurrentInstance().getExternalContext().redirect("./principal.xhtml");
        }
        catch (Exception ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, null, ex.getMessage()));
        }
    }
    
    public List<Membro> completaCampo(String texto)
    {
        List lista = null;                      
        
        try 
        {
            if(texto.matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+"))
            {
                lista = new MembroNegocio().getLista(Long.parseLong(texto), null);
            }
            else
            {
                lista = new MembroNegocio().getLista(0, texto);
            }         
        }
        catch (NegocioException ex) 
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", ex.getMessage()));                
        }
        catch (Exception ex) 
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Não consegui retornar nada."));                
        }
        
        return lista;
    }  
}
