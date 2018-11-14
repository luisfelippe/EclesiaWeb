/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ControleMembros.CLN.CDP;

import java.io.Serializable;
import util.Entidade;

/**
 *
 * @author LuísFelippe
 */
public class Cidade extends Entidade implements Serializable {
    private String nome;
    private Estado estado;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }
}
