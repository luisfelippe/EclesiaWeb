/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlefinanceiro.cln.cdp;

import java.io.Serializable;
import util.Entidade;

/**
 * Implementa um Tipo de Saída do Caixa
 * @author LuísFelippe
 */
public class TipoSaida extends Entidade implements Serializable{
    private String descricao;

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }    
}
