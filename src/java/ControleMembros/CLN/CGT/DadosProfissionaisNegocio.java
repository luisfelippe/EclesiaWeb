/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControleMembros.CLN.CGT;

import ControleMembros.CGD.DadosProfissionaisDAO;
import ControleMembros.CLN.CDP.DadosProfissionais;
import exception.NegocioException;
import util.Util;

/**
 *
 * @author LuísFelippe
 */
public class DadosProfissionaisNegocio {
    public DadosProfissionais getNovoDadosProfissionais() {
        return new DadosProfissionais();
    }
    
    public void salvar(DadosProfissionais dados) throws NegocioException, Exception {
        this.validaDadosProfissionais(dados);
        
        new DadosProfissionaisDAO().salvar(dados);
    }
    
    public void validaDadosProfissionais(DadosProfissionais dados) throws NegocioException {
        if(dados == null)
            throw new NegocioException("Favor informar um conjunto de dados profissionais válido");
        
//        if( !Util.isPreenchido(dados.getEndereco(), 3) )
//            throw new NegocioException("Favor informar um logradouro válido");
//        
//        if(dados.getBairro() == null || dados.getBairro().getId() < 1)
//            throw new NegocioException("Favor informar um bairro válido!");
        
        if(dados.getMembro() == null || dados.getMembro().getId() < 1)
            throw new NegocioException("Favor informar um membro válido!");
        
        if( !Util.isPreenchido(dados.getNome(), 3) )
            throw new NegocioException("Favor informar o nome da empresa!");
    }
}
