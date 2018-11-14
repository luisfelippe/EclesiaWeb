/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ControleMembros.CLN.CGT;

import ControleMembros.CGD.VisitanteDAO;
import ControleMembros.CLN.CDP.Visitante;
import exception.NegocioException;
import java.util.Date;
import java.util.List;
import util.Util;

/**
 *
 * @author Luis
 */
public class VisitanteNegocio {
    public Visitante getNovoVisitante() {
        return new Visitante();
    }
    
    public void salvarVisitante(Visitante visitante) throws NegocioException, Exception {
        if( ! Util.isPreenchidoPadrao( visitante.getNome() ) )
            throw new NegocioException("O nome deve conter pelo menos 03 digitos!");
                
        if( visitante.getTelefone().size() < 1 )
            throw new NegocioException("Deve ser informado pelo menos um telefone!");
        
        if( visitante.getEmail().length() > 0 )
            if( ! Util.isMailValido(visitante.getEmail()) )
                throw new NegocioException("Endereço de e-mail inválido!");
        
        new VisitanteDAO().salvar(visitante);
    }
    
    public List<Visitante> getLista(String descricao) throws NegocioException, Exception{
        List<Visitante> visitantes = null;
        
        if(Util.isPreenchidoPadrao(descricao)) {            
            visitantes = new VisitanteDAO().getLista(descricao);
        }
        else
        {
            visitantes = new VisitanteDAO().getLista(null);
        }
        
        if(visitantes == null || visitantes.isEmpty())
            throw new NegocioException("Não foram encontrados registros");
            
        return visitantes;            
    }

    public List<Visitante> getLista(Date dataIni, Date dataFim) throws NegocioException, Exception {
         List<Visitante> visitante = null;
        
        visitante = new VisitanteDAO().getLista(dataIni, dataFim); 
        
        if(visitante == null || visitante.isEmpty())
            throw new NegocioException("Não foram encontrados registros");
            
        return visitante; 
    }   
}
