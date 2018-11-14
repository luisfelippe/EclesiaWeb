package ControleMembros.CLN.CDP;

import java.io.Serializable;
import util.Entidade;

public class EMail extends Entidade implements Serializable{
    private String Endereco;

    public String getEndereco() {
        return Endereco;
    }

    public void setEndereco(String Endereco) {
        this.Endereco = Endereco;
    }
    
}
 
