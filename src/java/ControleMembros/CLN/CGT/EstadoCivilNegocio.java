/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ControleMembros.CLN.CGT;

import ControleMembros.CGD.EstadoCivilDAO;
import ControleMembros.CLN.CDP.EstadoCivil;
import exception.NegocioException;
import java.util.List;
import util.Util;

/**
 *
 * @author Luis
 */
public class EstadoCivilNegocio {
    public List<EstadoCivil> getLista(String descricao) throws NegocioException, Exception{
        List<EstadoCivil> estados = null;
        
        if(Util.isPreenchidoPadrao(descricao)) {            
            estados = new EstadoCivilDAO().getLista(descricao);
        }
        else
        {
            estados = new EstadoCivilDAO().getLista(null);
        }
        
        if(estados == null || estados.isEmpty())
            throw new NegocioException("Não foram encontrados registros");
            
        return estados;            
    }

    public EstadoCivil getEstadoCivil(long estadoCivil) throws NegocioException, Exception {
        if(estadoCivil < 0)
            throw new NegocioException("Favor informar um estado civil!");
        
        return new EstadoCivilDAO().getEstadoCivil(estadoCivil);
    }
}
