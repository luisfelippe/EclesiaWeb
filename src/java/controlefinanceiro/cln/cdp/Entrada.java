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
 * Implementa uma Entrada no Caixa
 * @author LuísFelippe
 */
public class Entrada extends Entidade implements Serializable{
    private TipoEntrada tipo;
    private Membro usuario;
    private Membro membro;
    private Date data;
    private double valor;

    public TipoEntrada getTipo() {
        return tipo;
    }

    public void setTipo(TipoEntrada tipo) {
        this.tipo = tipo;
    }

    public Membro getMembro() {
        return membro;
    }

    public void setMembro(Membro membro) {
        this.membro = membro;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public Membro getUsuario() {
        return usuario;
    }

    public void setUsuario(Membro usuario) {
        this.usuario = usuario;
    }   
}
