/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

/**
 *
 * @author luisfelippe
 */
public interface LogObserver {
    void notificarAlteracao(int alteracao);
    void resetAttributesObserver() ;
}
