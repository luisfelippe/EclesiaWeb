/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControleMembros.CLN.CDP;

/**
 *
 * @author luisfelippe
 */
public enum FaixaEtaria {
    ADULTO("Adulto",0),
    CRIANCA("Criança",1),
    JOVEM("Jovem",2);
    
    private final int id;
    private final String descricao;
    
    FaixaEtaria(String descricao, int id) {
        this.descricao = descricao;
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public String getDescricao() {
        return this.descricao;
    }
}
