
package controlefinanceiro.cln.cgt;

import controlefinanceiro.cgd.LancamentoDAO;
import controlefinanceiro.cln.cdp.Lancamento;
import controlefinanceiro.cln.cdp.TipoLancamento;
import exception.NegocioException;
import java.util.Date;
import java.util.List;

/**
 *
 * @author luisfelippe
 */
public class LancamentoNegocio {
    public Lancamento getNovoLancamento() {
        return new Lancamento();
    }
    
    public void addLancamento(Lancamento l) throws NegocioException, Exception {
        if(l.getData() == null)
            throw new NegocioException("Favor informar uma data válida!");
        
        //verifica se é uma data futura (maior que hj)
        if(l.getData().after( new Date(System.currentTimeMillis())))
            throw new NegocioException("A data não deve ser maior que a atual!");
        
        if(l.getValor() == 0.0 || l.getValor() < 0.0)
            throw new NegocioException("Favor informar um valor válido!");
        
        if(l.getTipo() == null)
            throw new NegocioException("Favor informar um tipo de lancamento válido!");
        
        if(l.getTipo() == TipoLancamento.SAIDA && l.getDescricao().isEmpty())
            throw new NegocioException("Favor informar uma descrição/motivo para saída!");
        
        new LancamentoDAO().salvar(l);
    }    

    public List<Lancamento> getListaDizimista(Date dataIni, Date dataFim) throws NegocioException, Exception {
        if(dataIni == null)
            throw new NegocioException("Favor informar uma data inicial válida!");
        
        if(dataIni.after(new Date(System.currentTimeMillis())))
            throw new NegocioException("A data inicial não deve ser maior que a atual!");
        
        if(dataFim != null && dataFim.after(new Date(System.currentTimeMillis())))
            throw new NegocioException("A data final não deve ser maior que a atual!");
        
        return new LancamentoDAO().getListaDizimista(dataIni, dataFim);
    }
    
    public List<Lancamento> getLista(Date dataIni, Date dataFim) throws NegocioException, Exception {
        if(dataIni == null)
            throw new NegocioException("Favor informar uma data inicial válida!");
        
        if(dataIni.after(new Date(System.currentTimeMillis())))
            throw new NegocioException("A data inicial não deve ser maior que a atual!");
        
        if(dataFim != null && dataFim.after(new Date(System.currentTimeMillis())))
            throw new NegocioException("A data final não deve ser maior que a atual!");
        
        return new LancamentoDAO().getListaLancamento(dataIni, dataFim);
    }
    
    public List<Lancamento> getLista(Date dataIni, Date dataFim, TipoLancamento t) throws NegocioException, Exception {
        if(t == null)
            throw new NegocioException("Favor informar um tipo de lancamento válido!");
        
        if(dataIni == null)
            throw new NegocioException("Favor informar uma data inicial válida!");
        
        if(dataIni.after(new Date(System.currentTimeMillis())))
            throw new NegocioException("A data inicial não deve ser maior que a atual!");
        
        if(dataFim != null && dataFim.after(new Date(System.currentTimeMillis())))
            throw new NegocioException("A data final não deve ser maior que a atual!");
        
        return new LancamentoDAO().getLista(dataIni, dataFim, t);
    }
    
    public long getQtdContribuintes() throws Exception {
        return new LancamentoDAO().getQtdContribuintes();
    }
    
    public double getValorMedioPorContribuinte() throws Exception {
        return new LancamentoDAO().getValorMedioPorContribuinte();
    }
    
    public double getValorEntradaMes() throws Exception {
        return new LancamentoDAO().getValorEntradaMes();
    }
    
    public double getValorEntradaMesAnterior() throws Exception {
        return new LancamentoDAO().getValorEntradaMesAnterior();
    }
    
    public double getValorSaidaMes() throws Exception {
        return new LancamentoDAO().getValorSaidaMes();
    }
    
    public double getValorSaidaMesAnterior() throws Exception {
        return new LancamentoDAO().getValorSaidaMesAnterior();
    }
}
