/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ControleMembros.CLN.CDP;

import java.io.Serializable;
import java.util.Date;
import util.Entidade;

/**
 *
 * @author LuísFelippe
 */
public class Parente extends Entidade implements Serializable{
    private GrauParentesco parentesco;
    private Profissao profissao;
    private char masculino = 'M';
    private String nome;
    private Date dtNasc;
    private Membro parenteMembro;

    public GrauParentesco getParentesco() {
        return parentesco;
    }

    public void setParentesco(GrauParentesco parentesco) {
        this.parentesco = parentesco;
    }

    public Profissao getProfissao() {
        return profissao;
    }

    public void setProfissao(Profissao profissao) {
        this.profissao = profissao;
    }

    public char getMasculino() {
        return masculino;
    }

    public void setMasculino(char masculino) {
        this.masculino = masculino;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getDtNasc() {
        return dtNasc;
    }

    public void setDtNasc(Date dtNasc) {
        this.dtNasc = dtNasc;
    }

    public Membro getParenteMembro() {
        return parenteMembro;
    }

    public void setParenteMembro(Membro parenteMembro) {
        this.parenteMembro = parenteMembro;
    }    
}
