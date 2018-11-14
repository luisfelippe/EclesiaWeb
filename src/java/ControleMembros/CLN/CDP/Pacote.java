/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ControleMembros.CLN.CDP;

import java.io.Serializable;
import util.Entidade;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author LuísFelippe
 */
public class Pacote extends Entidade implements Serializable{
    private List<Entidade> conteudo = new LinkedList<Entidade>();

    public List<Entidade> getConteudo() {
        return conteudo;
    }

    public void setConteudo(List<Entidade> conteudo) {
        this.conteudo = conteudo;
    }    
}
