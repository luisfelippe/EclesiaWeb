/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlefinanceiro.cln.cgt;

import exception.NegocioException;
import controlefinanceiro.cgd.SaidaDAO;
import controlefinanceiro.cln.cdp.Saida;
import java.util.Date;
import java.util.List;

/**
 * Implementa a Camada de negocio de uma saida
 * @author LuísFelippe
 */
public class SaidaNegocio {
    public Saida getNovaSaida() {
        return new Saida();
    }
    
    public void addSaida(Saida saida) throws NegocioException, Exception {
        if(saida.getData() == null)
            throw new NegocioException("Favor informar uma data válida!");
        
        //verifica se é uma data futura (maior que hj)
        if(saida.getData().after( new Date(System.currentTimeMillis())))
            throw new NegocioException("A data não deve ser maior que a atual!");
        
        if(saida.getValor() == 0.0 || saida.getValor() < 0.0)
            throw new NegocioException("Favor informar um valor válido!");
        
        if(saida.getDescricao().isEmpty())
            throw new NegocioException("Favor informar uma descrição/motivo para saída!");
        
        new SaidaDAO().salvar(saida);
    }
    
    public List<Saida> getLista(Date dataIni, Date dataFim) throws NegocioException, Exception {
        if(dataIni == null)
            throw new NegocioException("Favor informar uma data inicial válida!");
        
        if(dataIni.after(new Date(System.currentTimeMillis())))
            throw new NegocioException("A data inicial não deve ser maior que a atual!");
        
        if(dataFim != null && dataFim.after(new Date(System.currentTimeMillis())))
            throw new NegocioException("A data final não deve ser maior que a atual!");
        
        return new SaidaDAO().getLista(dataIni, dataFim);
    }
}
