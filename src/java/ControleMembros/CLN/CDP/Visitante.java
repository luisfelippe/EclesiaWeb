package ControleMembros.CLN.CDP;

import java.io.Serializable;
import java.util.Date;
import util.Entidade;

public class Visitante extends Pessoa implements Serializable{
    private String Igreja;
    private int Idade;
    private Date dataVisita;

    public int getIdade() {
        return Idade;
    }

    public void setIdade(int Idade) {
        this.Idade = Idade;
    }

    public String getIgreja() {
        return Igreja;
    }

    public void setIgreja(String Igreja) {
        this.Igreja = Igreja;
    }

    public Date getDataVisita() {
        return dataVisita;
    }

    public void setDataVisita(Date dataVisita) {
        this.dataVisita = dataVisita;
    }
}
 
