/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlefinanceiro.cln.cdp;

import util.Entidade;
import ControleMembros.CLN.CDP.Membro;
import java.io.Serializable;
import java.util.Date;

/**
 * Implementa uma Saida no Caixa
 * @author LuísFelippe
 */
public class Saida extends Entidade implements Serializable{
    private TipoSaida tipo;
    private Membro usuario;
    private double valor;
    private String descricao;
    private Date data;

    public TipoSaida getTipo() {
        return tipo;
    }

    public void setTipo(TipoSaida tipo) {
        this.tipo = tipo;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }  

    public Membro getUsuario() {
        return usuario;
    }

    public void setUsuario(Membro usuario) {
        this.usuario = usuario;
    }    
}
