
package controlefinanceiro.view;

import controlefinanceiro.cln.cdp.CategoriaLancamento;
import controlefinanceiro.cln.cdp.Lancamento;
import controlefinanceiro.cln.cdp.TipoLancamento;
import controlefinanceiro.cln.cgt.CategoriaLancamentoNegocio;
import controlefinanceiro.cln.cgt.LancamentoNegocio;
import corporativo.ManageBean;
import exception.NegocioException;
import java.util.Date;
import java.util.Iterator;
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
public class RelFinanceiroBean extends ManageBean {
    private List<Lancamento> lista;
    private List<CategoriaLancamento> categorias;
    private long idCategoriaSel;
    private TipoLancamento[] opcoes = TipoLancamento.values();
    private TipoLancamento opcao;
    
    private Date dataIni;
    private Date dataFim;
    
    private double totalIn;
    private double totalOut;
    
    public RelFinanceiroBean() {
        try
        {
            this.limpar();
        }        
        catch (Exception ex) 
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, null, ex.getMessage()));
        }
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
    }

    public TipoLancamento getOpcao() {
        return opcao;
    }

    public void setOpcao(TipoLancamento opcao) {
        this.opcao = opcao;        
    }

    public TipoLancamento[] getOpcoes() {
        return opcoes;
    }

    public void setOpcoes(TipoLancamento[] opcoes) {
        this.opcoes = opcoes;
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

    public double getTotalIn() {
        return totalIn;
    }

    public void setTotalIn(double totalIn) {
        this.totalIn = totalIn;
    }

    public double getTotalOut() {
        return totalOut;
    }

    public void setTotalOut(double totalOut) {
        this.totalOut = totalOut;
    }

    public List<Lancamento> getLista() {
        return lista;
    }

    public void setLista(List<Lancamento> lista) {
        this.lista = lista;
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
    
    public void buscar() {
        try
        {
            if(this.idCategoriaSel == 1)
                this.lista = new LancamentoNegocio().getListaDizimista(dataIni, dataFim);
            else if(this.idCategoriaSel == 2)
                this.lista = new LancamentoNegocio().getLista(dataIni, dataFim);
                
            if(this.lista == null || this.lista.isEmpty())
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, null, "Não há registro a serem exibidos!"));
            else
            {
                totalIn = 0;
                totalOut = 0;

                Iterator<Lancamento> it = this.lista.iterator();
                while (it.hasNext()) {
                    Lancamento next = it.next();

                    if(next.getTipo() == TipoLancamento.ENTRADA)
                        totalIn += next.getValor();
                    else if(next.getTipo() == TipoLancamento.SAIDA)
                        totalOut += next.getValor();
                }
            }
        }
        catch(NegocioException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, null, ex.getMessage()));
            ex.printStackTrace();
        }
        catch(Exception ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, null, ex.getMessage()));
            ex.printStackTrace();
        }
    } 
    
    public void limpar() {
        this.opcao = TipoLancamento.ENTRADA; //padrão é entrada
        this.idCategoriaSel = 0;      
        this.totalIn = 0;
        this.totalOut = 0;
        this.dataFim = null;
        this.dataIni = null;
        
        this.atualizaListaCategoria();
    }
}
