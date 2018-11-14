package ControleMembros.CLN.CDP;

import java.io.Serializable;
import util.Entidade;

public class Endereco extends Entidade implements Serializable{ 
    private String Logradouro;	 
    private Bairro Bairro;	 
    private String Cep;

    public Bairro getBairro() {
        return Bairro;
    }

    public void setBairro(Bairro Bairro) {
        this.Bairro = Bairro;
    }

    public String getCep() {
        return Cep;
    }

    public void setCep(String Cep) {
        this.Cep = Cep;
    }

    public String getLogradouro() {
        return Logradouro;
    }

    public void setLogradouro(String Logradouro) {
        this.Logradouro = Logradouro;
    }
    
    public String getEndCompleto() {
        return (this.Logradouro + ", " + this.Bairro.getNome() + ", " + this.Bairro.getCidade().getNome());
    }
}
 
