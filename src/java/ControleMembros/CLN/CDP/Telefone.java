package ControleMembros.CLN.CDP;

import java.io.Serializable;
import util.Entidade;

public class Telefone extends Entidade implements Serializable{
    private String Numero;
    private TipoTelefone tipo;

    public String getNumero() {
        return Numero;
    }

    public void setNumero(String Numero) {
        this.Numero = Numero;
    }

    public TipoTelefone getTipo() {
        return tipo;
    }

    public void setTipo(TipoTelefone tipo) {
        this.tipo = tipo;
    }
}
 
