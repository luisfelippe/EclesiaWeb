
package controlefinanceiro.cln.cdp;

import ControleMembros.CLN.CDP.Membro;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import util.Entidade;

@Entity
@Table(name = "lancamento")
public class Lancamento extends Entidade implements Serializable {
    private TipoLancamento tipo;
    private Double valor;
    private String descricao;
    private Membro autor;
    private Membro membro;
    private Date data;
    private CategoriaLancamento categoria;

    @Override
    @Id
    @Column(name = "id_lancamento")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return super.getId(); //To change body of generated methods, choose Tools | Templates.
    }

    @Column(name="tipo_lancamento")
    @Enumerated(EnumType.STRING)
    public TipoLancamento getTipo() {
        return tipo;
    }

    public void setTipo(TipoLancamento tipo) {
        this.tipo = tipo;
    }

    @Column(name = "valor", nullable = false)
    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    @Column(name = "descricao")
    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_autor")
    public Membro getAutor() {
        return autor;
    }

    public void setAutor(Membro autor) {
        this.autor = autor;
    }

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_membro")
    public Membro getMembro() {
        return membro;
    }

    public void setMembro(Membro membro) {
        this.membro = membro;
    }

    @Column(name = "data_lancamento")
    @Temporal(TemporalType.DATE)
    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="id_categoria")
    public CategoriaLancamento getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaLancamento categoria) {
        this.categoria = categoria;
    }
}
