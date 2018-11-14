/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlefinanceiro.cln.cgt;

import exception.NegocioException;
import controlefinanceiro.cgd.EntradaDAO;
import controlefinanceiro.cln.cdp.Entrada;
import java.util.Date;
import java.util.List;

/**
 * Implementa a Camada de negocio de uma entrada
 * @author LuísFelippe
 */
public class EntradaNegocio {

    public Entrada getNovaEntrada() {
        return new Entrada();
    }
    
    public void addEntrada(Entrada entrada) throws NegocioException, Exception {
        if(entrada.getData() == null)
            throw new NegocioException("Favor informar uma data válida!");
        
        //verifica se é uma data futura (maior que hj)
        if(entrada.getData().after( new Date(System.currentTimeMillis())))
            throw new NegocioException("A data não deve ser maior que a atual!");
        
        if(entrada.getValor() == 0.0 || entrada.getValor() < 0.0)
            throw new NegocioException("Favor informar um valor válido!");
        
        /*if(entrada.getMembro() == null)
            throw new NegocioException("Favor informar um Membro cadastrado!");*/
        
        new EntradaDAO().salvar(entrada);
    }    

    public List<Entrada> getListaDizimista(Date dataIni, Date dataFim) throws NegocioException, Exception {
        if(dataIni == null)
            throw new NegocioException("Favor informar uma data inicial válida!");
        
        if(dataIni.after(new Date(System.currentTimeMillis())))
            throw new NegocioException("A data inicial não deve ser maior que a atual!");
        
        if(dataFim != null && dataFim.after(new Date(System.currentTimeMillis())))
            throw new NegocioException("A data final não deve ser maior que a atual!");
        
        return new EntradaDAO().getListaDizimista(dataIni, dataFim);
    }
    
    public List<Entrada> getLista(Date dataIni, Date dataFim) throws NegocioException, Exception {
        if(dataIni == null)
            throw new NegocioException("Favor informar uma data inicial válida!");
        
        if(dataIni.after(new Date(System.currentTimeMillis())))
            throw new NegocioException("A data inicial não deve ser maior que a atual!");
        
        if(dataFim != null && dataFim.after(new Date(System.currentTimeMillis())))
            throw new NegocioException("A data final não deve ser maior que a atual!");
        
        return new EntradaDAO().getLista(dataIni, dataFim);
    }
    
    public long getQtdContribuintes() throws Exception {
        return new EntradaDAO().getQtdContribuintes();
    }
    
    public double getValorMedioPorContribuinte() throws Exception {
        return new EntradaDAO().getValorMedioPorContribuinte();
    }
}
