/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exception;

/**
 * Classe que implementa o lançador de excessões da camada de negócio
 * @author Luis
 */
public class NegocioException extends Exception{
    
    public NegocioException (String msgErro) {
        super(msgErro);
    }
}
