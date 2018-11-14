
package ControleMembros.CLN.CDP;

import java.io.Serializable;
import java.util.Date;
import util.Entidade;

/**
 *
 * @author LuísFelippe
 */
public class DadosMinisteriais extends Entidade implements Serializable {
    private Date dataInicial;
    private Date dataFinal;
    private Cargo cargo;
    private Ministerio ministerio;
    private Membro membro;
    private String obs;

    public Date getDataInicial() {
        return dataInicial;
    }

    public void setDataInicial(Date dataInicial) {
        this.dataInicial = dataInicial;
    }

    public Date getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(Date dataFinal) {
        this.dataFinal = dataFinal;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public Ministerio getMinisterio() {
        return ministerio;
    }

    public void setMinisterio(Ministerio ministerio) {
        this.ministerio = ministerio;
    }

    public Membro getMembro() {
        return membro;
    }

    public void setMembro(Membro membro) {
        this.membro = membro;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }
    
}
