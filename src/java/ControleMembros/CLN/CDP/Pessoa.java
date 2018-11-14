package ControleMembros.CLN.CDP;

import ControleMembros.CLN.CGT.FotoNegocio;
import java.io.Serializable;
import java.util.List;
import util.Entidade;

public abstract class Pessoa extends Entidade implements Serializable { 
    private String Nome;
    private char masculino = 'M';
    private List<Endereco> endereco;
//    private List<EMail> email;
    private String email;
    private List<Telefone> telefone;
    
    private List<Foto> fotos;

    public String getNome() {
        return Nome;
    }

    public void setNome(String Nome) {
        this.Nome = Nome.toUpperCase();
    }

    public List<Endereco> getEndereco() {
        return endereco;
    }

    public void setEndereco(List<Endereco> endereco) {
        this.endereco = endereco;
    }

//    public List<EMail> getEmail() {
//        return email;
//    }
//
//    public void setEmail(List<EMail> email) {
//        this.email = email;
//    }

    public List<Telefone> getTelefone() {
        return telefone;
    }

    public void setTelefone(List<Telefone> telefone) {
        this.telefone = telefone;
    }

    public char getMasculino() {
        return masculino;
    }

    public void setMasculino(char masculino) {
        this.masculino = masculino;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Foto> getFotos() {
        return fotos;
    }

    public void setFotos(List<Foto> fotos) {
        this.fotos = fotos;
    }    
    
    public Foto getFotoPrincipal()
    {
        Foto f = new FotoNegocio().getNovaFoto();
        
        if (getId() > 0) 
        {
            if (getFotos().size() > 0) {
                f = (Foto)getFotos().get(0);
            }
        }
        
        return f;
    }
}

 
