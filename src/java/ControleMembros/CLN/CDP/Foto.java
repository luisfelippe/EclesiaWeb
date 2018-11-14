
package ControleMembros.CLN.CDP;

import java.io.Serializable;
import util.Entidade;

/**
 *
 * @author luisfelippe
 */
public class Foto extends Entidade implements Serializable {
    private String descricao;
    private String caminho;
    private int nrOrdem;
    private Pessoa pessoa;
    
    public Foto()
    {
        this.caminho = "bd-fotos/membros";
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCaminho() {
        return caminho;
    }

    public void setCaminho(String caminho) {
        this.caminho = caminho;
    }

    public int getNrOrdem() {
        return nrOrdem;
    }

    public void setNrOrdem(int nrOrdem) {
        this.nrOrdem = nrOrdem;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }
}
