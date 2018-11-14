/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ControleMembros.CLN.CGT;

import ControleMembros.CGD.UsuarioDAO;
import ControleMembros.CLN.CDP.Membro;
import ControleMembros.CLN.CDP.Usuario;
import exception.NegocioException;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.Util;

/**
 *
 * @author Luis
 */
public class UsuarioNegocio {
    public Usuario getNovoUsuario() {
        return new Usuario();
    }
    public Membro efetuarLogin(Usuario usr) throws Exception
    {
        if(usr.getLogin().length() < 3)
            throw new Exception("O nome de usuário não deve ser nulo tão pouco conter menos que 3 caracteres!");
        
        if(usr.getSenha().length() < 3)
            throw new Exception("A senha deve conter pelo menos 3 caracteres!");
        
        Membro usrLogin = new UsuarioDAO().efetuarLogin(usr);
        
        return usrLogin;
    }

    public void salvarUsuario(Usuario usr) throws NegocioException, Exception {
        if(usr.getMembro() == null)
            throw new NegocioException("Toda conta de usuário deve pertencer a um membro!");
        
        if(!Util.isPreenchidoPadrao(usr.getLogin()))
            throw new NegocioException("O login deve conter pelo menos 3 caracters!");
        
        if(!Util.isPreenchido(usr.getSenha(), 6))
            throw new NegocioException("A senha deve conter no mínimo 6 caracteres!");
        
        new UsuarioDAO().salvar(usr);
    }

    public Usuario getUsuario(Membro membro) throws Exception {
        Usuario usr;
        
        usr = new UsuarioDAO().getUsuario(membro);
        
        return usr;
    }

    public void excluirUsuario(Usuario usr) throws Exception {
        new UsuarioDAO().Excluir(usr);
    }
}
