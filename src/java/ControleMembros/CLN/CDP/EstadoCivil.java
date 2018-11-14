package ControleMembros.CLN.CDP;

import java.io.Serializable;
import util.Entidade;

public class EstadoCivil extends Entidade implements Serializable{ 
    private String Descricao;

    public String getDescricao() {
        return Descricao;
    }

    public void setDescricao(String Descricao) {
        this.Descricao = Descricao;
    }
    
    
}
 
