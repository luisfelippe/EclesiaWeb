package ControleMembros.CLN.CDP;

import java.io.Serializable;
import util.Entidade;

public class Usuario extends Entidade implements Serializable { 
    private String Login;	 
    private String Senha;
    private Membro membro;

    public String getLogin() {
        return Login;
    }

    public void setLogin(String Login) {
        this.Login = Login;
    }

    public String getSenha() {
        return Senha;
    }

    public void setSenha(String Senha) {
        this.Senha = Senha;
    }

    public Membro getMembro() {
        return this.membro;
    }

    public void setMembro(Membro membro) {
        this.membro = membro;
    }
}
 
