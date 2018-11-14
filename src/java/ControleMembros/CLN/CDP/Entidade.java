/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ControleMembros.CLN.CDP;

import java.io.Serializable;

/**
 *
 * @author Luis
 */
public class Entidade implements Serializable{
    private long id;

    /**
     * @return the id
     */
    public long getId() {
            return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
            this.id = id;
    }
}
