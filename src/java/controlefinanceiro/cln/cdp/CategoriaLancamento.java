
package controlefinanceiro.cln.cdp;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import util.Entidade;

@Entity
@Table(name = "categoria_lancamento")
public class CategoriaLancamento extends Entidade implements Serializable {
    private String descricao;
    private TipoLancamento tipo;
    
    @Override
    @Id
    @Column(name = "id_categoria_lancamento")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return super.getId(); //To change body of generated methods, choose Tools | Templates.
    }

    @Column(name="descricao")
    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Column(name="tipo_lancamento")
    @Enumerated(EnumType.STRING)
    public TipoLancamento getTipo() {
        return tipo;
    }

    public void setTipo(TipoLancamento tipo) {
        this.tipo = tipo;
    }
}
