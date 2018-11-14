package ControleMembros.CLN.CDP;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Classe que representa um membro.
 * 
 * Membro é toda pessoa registrada no sistema que possua um Tipo de Admissao definido
 * 
 * @author luisfelippe
 */
public class Membro extends Pessoa implements Serializable {
 
    /**
     * Consolidador: PF encarredada por consolidar um novo convertido por um determinado tempo
     * Discipulador: PF encarregada por discipular outra por tempo indeterminado
     */    
    
    private boolean inativo;
    private boolean falecido;
    private boolean desempregado;
    
    private Date DataNasc;	 
    private char Sexo;	 
    private String RG;	 
    private String CPF;	 
    private EstadoCivil estado_Civil;	 
    private Profissao profissao;
    
    private DadosProfissionais dadosProfissionais;
    private Escolaridade escolaridade;
    private Usuario usuario;
    private Pacote pacote = new Pacote();
    private List<Parente> parentes = new LinkedList<Parente>();
    private List<DadosMinisteriais> dadosMinisteriais = new ArrayList<DadosMinisteriais>();
    
    private Date dataBatismo;
    private Date dataConversao;
    private Date dataAdmissao; /* data que foi recebido como membro */
    private FaixaEtaria faixaEtaria; /* Criança, adulto, jovem */
    private TipoAdmissao tipoAdmissao; /* Batismo ou aclamação */
    private TipoFrequentador tipoFrequentador; /* membro, frequentador, visitante */
    private String descCursoEscolaridade;

    public String getCPF() {
        return CPF;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
    }

    public Date getDataNasc() {
        return DataNasc;
    }

    public void setDataNasc(Date DataNasc) {
        this.DataNasc = DataNasc;
    }

    public String getRG() {
        return RG;
    }

    public void setRG(String RG) {
        this.RG = RG;
    }

    public char getSexo() {
        return Sexo;
    }

    public void setSexo(char Sexo) {
        this.Sexo = Sexo;
    }

    public Escolaridade getEscolaridade() {
        return escolaridade;
    }

    public void setEscolaridade(Escolaridade escolaridade) {
        this.escolaridade = escolaridade;
    }

    public EstadoCivil getEstado_Civil() {
        return estado_Civil;
    }

    public void setEstado_Civil(EstadoCivil estado_Civil) {
        this.estado_Civil = estado_Civil;
    }

    public Profissao getProfissao() {
        return profissao;
    }

    public void setProfissao(Profissao profissao) {
        this.profissao = profissao;
    }

    public boolean isDesempregado() {
        return desempregado;
    }

    public void setDesempregado(boolean desempregado) {
        this.desempregado = desempregado;
    }

    public DadosProfissionais getDadosProfissionais() {
        return dadosProfissionais;
    }

    public void setDadosProfissionais(DadosProfissionais dadosProfissionais) {
        this.dadosProfissionais = dadosProfissionais;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Pacote getPacote() {
        return pacote;
    }

    public void setPacote(Pacote pacote) {
        this.pacote = pacote;
    }

    public List<Parente> getParentes() {
        return parentes;
    }

    public void setParentes(List<Parente> parentes) {
        this.parentes = parentes;
    }

    public List<DadosMinisteriais> getDadosMinisteriais() {
        return dadosMinisteriais;
    }

    public void setDadosMinisteriais(List<DadosMinisteriais> dadosMinisteriais) {
        this.dadosMinisteriais = dadosMinisteriais;
    }

    public boolean isInativo() {
        return inativo;
    }

    public void setInativo(boolean inativo) {
        this.inativo = inativo;
    }

    public boolean isFalecido() {
        return falecido;
    }

    public void setFalecido(boolean falecido) {
        this.falecido = falecido;
        this.setInativo(this.falecido); //se o membro for dado como falecido automaticamente seta ele como inativo
    }

    public Date getDataBatismo() {
        return dataBatismo;
    }

    public void setDataBatismo(Date dataBatismo) {
        this.dataBatismo = dataBatismo;
    }

    public String getDescCursoEscolaridade() {
        return descCursoEscolaridade;
    }

    public void setDescCursoEscolaridade(String descCursoEscolaridade) {
        this.descCursoEscolaridade = descCursoEscolaridade;
    }

    public Date getDataConversao() {
        return dataConversao;
    }

    public void setDataConversao(Date dataConversao) {
        this.dataConversao = dataConversao;
    }

    public Date getDataAdmissao() {
        return dataAdmissao;
    }

    public void setDataAdmissao(Date dataAdmissao) {
        this.dataAdmissao = dataAdmissao;
    }

    public FaixaEtaria getFaixaEtaria() {
        return faixaEtaria;
    }

    public void setFaixaEtaria(FaixaEtaria faixaEtaria) {
        this.faixaEtaria = faixaEtaria;
    }

    public TipoAdmissao getTipoAdmissao() {
        return tipoAdmissao;
    }

    public void setTipoAdmissao(TipoAdmissao tipoAdmissao) {
        this.tipoAdmissao = tipoAdmissao;
    }

    public TipoFrequentador getTipoFrequentador() {
        return tipoFrequentador;
    }

    public void setTipoFrequentador(TipoFrequentador tipoFrequentador) {
        this.tipoFrequentador = tipoFrequentador;
    }
}
 
