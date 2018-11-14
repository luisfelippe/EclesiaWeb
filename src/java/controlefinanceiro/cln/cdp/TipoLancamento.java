
package controlefinanceiro.cln.cdp;

/**
 *
 * @author luisfelippe
 */
public enum TipoLancamento {
    ENTRADA("Entrada",0),
    SAIDA("Saída",1);
    
    private final int id;
    private final String descricao;
    
    TipoLancamento(String descricao, int id) {
        this.descricao = descricao;
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public String getDescricao() {
        return this.descricao;
    }
}
