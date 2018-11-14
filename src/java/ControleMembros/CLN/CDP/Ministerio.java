/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControleMembros.CLN.CDP;

import java.io.Serializable;
import util.Entidade;

/**
 *
 * @author LuísFelippe
 */
public class Ministerio  extends Entidade implements Serializable {
    private String descricao;

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
