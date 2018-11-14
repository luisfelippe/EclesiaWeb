package ControleMembros.view;

import corporativo.EstadoCivilConverter;
import corporativo.ProfissaoConverter;
import corporativo.ManageBean;
import ControleMembros.CLN.CDP.Bairro;
import ControleMembros.CLN.CDP.Cargo;
import ControleMembros.CLN.CDP.Cidade;
import ControleMembros.CLN.CDP.DadosMinisteriais;
import ControleMembros.CLN.CDP.DadosProfissionais;
import ControleMembros.CLN.CDP.Endereco;
import ControleMembros.CLN.CDP.Escolaridade;
import ControleMembros.CLN.CDP.Estado;
import ControleMembros.CLN.CDP.EstadoCivil;
import ControleMembros.CLN.CDP.FaixaEtaria;
import ControleMembros.CLN.CDP.Foto;
import ControleMembros.CLN.CDP.GrauParentesco;
import ControleMembros.CLN.CDP.Membro;
import ControleMembros.CLN.CDP.Ministerio;
import ControleMembros.CLN.CDP.Pacote;
import ControleMembros.CLN.CDP.Pais;
import ControleMembros.CLN.CDP.Parente;
import ControleMembros.CLN.CGT.MembroNegocio;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import ControleMembros.CLN.CDP.Profissao;
import ControleMembros.CLN.CDP.Telefone;
import ControleMembros.CLN.CDP.TipoAdmissao;
import ControleMembros.CLN.CDP.TipoFrequentador;
import ControleMembros.CLN.CDP.TipoTelefone;
import ControleMembros.CLN.CGT.BairroNegocio;
import ControleMembros.CLN.CGT.CargoNegocio;
import ControleMembros.CLN.CGT.CidadeNegocio;
import ControleMembros.CLN.CGT.DadosMinisteriaisNegocio;
import ControleMembros.CLN.CGT.DadosProfissionaisNegocio;
import ControleMembros.CLN.CGT.EnderecoNegocio;
import ControleMembros.CLN.CGT.EscolaridadeNegocio;
import ControleMembros.CLN.CGT.EstadoCivilNegocio;
import ControleMembros.CLN.CGT.EstadoNegocio;
import ControleMembros.CLN.CGT.FotoNegocio;
import ControleMembros.CLN.CGT.GrauParentescoNegocio;
import ControleMembros.CLN.CGT.MinisterioNegocio;
import ControleMembros.CLN.CGT.PaisNegocio;
import ControleMembros.CLN.CGT.ParenteNegocio;
import ControleMembros.CLN.CGT.ProfissaoNegocio;
import ControleMembros.CLN.CGT.TelefoneNegocio;
import ControleMembros.CLN.CGT.TipoTelefoneNegocio;
import exception.NegocioException;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.model.UploadedFile;
import util.Entidade;
import util.Util;

/**
 *
 * @author Luis
 */
@ManagedBean
@ViewScoped
public class MembroBean extends ManageBean implements Serializable{
    private Membro membro;
    private Endereco endereco;
    private List<Profissao> profissoes;
    private long profissao;
    
    private DadosProfissionais dadosProfissionais;
    
    private List<EstadoCivil> estadosCivis;
    private long estadoCivil;
    private Telefone telefone;
    
    private List<Escolaridade> listaEscolaridade;
    private long escolaridade;
    
    //variáveis do form profissao
    private List<Estado> estados; //Setar para ser os estados do pais padrão definido nas configurações
    private long ufPro = 2; //Espírito Santo //Setar para ser o estado padrão definido nas configurações
    private long ufEnd = 2; //Espírito Santo //Setar para ser o estado padrão definido nas configurações
    
    
    private List<Cidade> cidades; //Setar para ser as cidades do estado padrão definido nas configurações
    private long municipioPro = 3; // SERRA //Setar para ser a cidade padrão definido nas configurações
    private long municipioEnd = 3; // SERRA //Setar para ser a cidade padrão definido nas configurações
    private long naturalidade = 3; // SERRA //Setar para ser a cidade padrão definido nas configurações
    
    private List<Bairro> bairros; //Setar para ser os bairros da cidade padrão definido nas configurações
    private long bairroPro; 
    private long bairroEnd; 
    
    private long numeroPro;
    private long ramal;
    
    private int tabIndex;
    
    //variáveis para o form de parentes
    private long idMembroParente;
    private Parente parente;
    private long profissaoParenteSel;    
    private long parentescoParenteSel;    
    private List<GrauParentesco> parentescos;
    
    private List<TipoTelefone> listaTiposTel;
    private int idTipoTelSel;
    
    private List<Ministerio> listaMinisterios;
    private long idMinSel = 0;
    
    private List<Cargo> listaCargos;
    private long idCargoSel = 0;
    
    private DadosMinisteriais dadosMinisteriais;
    
    private Foto fotoPessoa;
    private int indAtualFoto;
    
    private TipoFrequentador[] tiposFrequentador = TipoFrequentador.values();
    private TipoAdmissao[] tiposAdmissao = TipoAdmissao.values();
    private FaixaEtaria[] faixasEtarias = FaixaEtaria.values();
    
    /** Creates a new instance of MembroBean */
    public MembroBean() 
    {
        this.dadosProfissionais = new DadosProfissionaisNegocio().getNovoDadosProfissionais();
        this.dadosMinisteriais = new DadosMinisteriaisNegocio().getNovoDadosMinisteriais();
        
        try
        {
            this.profissoes = ProfissaoConverter.profissoes;
            this.estadosCivis = EstadoCivilConverter.estados;

            Pais p = new PaisNegocio().getNovoPais();
            p.setId(2); //Brasil
            
            //retorna a lista dos estados brasileiros
            this.estados = new EstadoNegocio().getLista(p);
            this.cidades = new CidadeNegocio().getLista(this.ufPro);
            this.bairros = new BairroNegocio().getLista(this.municipioPro);    
            
            this.listaMinisterios = new MinisterioNegocio().getLista();
            
            this.listaTiposTel = new TipoTelefoneNegocio().getLista();
            
            this.listaEscolaridade = new EscolaridadeNegocio().getLista();
                            
            this.setMembro((Membro) super.getSession().getAttribute("membro"));            
            
            this.parentescos = new GrauParentescoNegocio().getLista();
            this.parente = new ParenteNegocio().getNovoParente();
            this.endereco = new EnderecoNegocio().getNovoEndereco();
            this.telefone = new TelefoneNegocio().getNovoTelefone();
        }
        catch(Exception e)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, null, e.getMessage()));
        }
    }
    
    public void reset() 
    {        
        if(this.membro.getId() < 1) 
        {
             this.membro = new MembroNegocio().getNovoMembro();
             this.profissao = 0;
             this.estadoCivil = 0;
             this.endereco = new EnderecoNegocio().getNovoEndereco();
        }
    }

    public Membro getMembro() {
        return membro;
    }

    public void setMembro(Membro membro) 
    {
        this.membro = membro;
        
        super.setObjetcSession("membro", this.membro);
        
        this.dadosProfissionais = new DadosProfissionaisNegocio().getNovoDadosProfissionais();
        
        this.limparDadosProfissionaisAction();
        this.limparEnderecoAction();
        this.limparDadosMinisteriais();
        this.limparParenteAction();
        this.limparTelefoneAction();
        
        if(this.membro != null && this.membro.getId() > 0)
        {
            this.profissao = (this.membro.getProfissao() != null ) ? this.membro.getProfissao().getId() : 0;                
            this.estadoCivil = (this.membro.getEstado_Civil() != null) ? this.membro.getEstado_Civil().getId() : 0;   
            this.escolaridade = (this.membro.getEscolaridade() != null) ? this.membro.getEscolaridade().getId() : 0;

            this.fotoPessoa = this.membro.getFotoPrincipal();
            
            if(this.membro.getDadosProfissionais() != null && this.membro.getDadosProfissionais().getId() > 0) {
                this.dadosProfissionais = this.membro.getDadosProfissionais();

                if(this.dadosProfissionais.getBairro() != null && this.dadosProfissionais.getBairro().getId() > 0) {
                    this.bairroPro = this.dadosProfissionais.getBairro().getId();
                    this.municipioPro = this.dadosProfissionais.getBairro().getCidade().getId();
                    this.ufPro = this.dadosProfissionais.getBairro().getCidade().getEstado().getId();
                    
                    this.atualizaListaCidades();
                    this.atualizaListaBairros();
                }
            }
            
            /**
             * @TODO carregar os dados ministeriais
             */
        }  
        else
            this.fotoPessoa = new FotoNegocio().getNovaFoto();
    }
    
    public void cancelar() 
    {
        try 
        {
            this.novoAction();
            this.membro = null;
            super.getSession().removeAttribute("membro");            
        }
        catch (Exception ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, null, ex.getMessage()));
        }
    }

    public List<Profissao> getProfissoes() {
        return profissoes;
    }

    public void setProfissoes(List<Profissao> profissoes) {
        this.profissoes = profissoes;
    }

    public List<EstadoCivil> getEstadosCivis() {
        return estadosCivis;
    }

    public void setEstadosCivis(List<EstadoCivil> estadosCivis) {
        this.estadosCivis = estadosCivis;
    }
    
    public void salvarAction() {
        
       // List<Telefone> list = new LinkedList<Telefone>(); //reseta a lista de telefones ao criar uma nova e adiciona-la ao membro

        //this.membro.setTelefone(list);                
        
        if(this.endereco != null && this.endereco.getLogradouro() != null && !this.endereco.getLogradouro().trim().isEmpty())
        {
            List<Endereco> listEnd = new LinkedList<Endereco>();//reseta a lista de telefones ao criar uma nova e adiciona-la ao membro
            listEnd.add(this.endereco);
            
            this.membro.setEndereco(listEnd);
        }
        
        try 
        {    
            if(this.estadoCivil > 0)
                this.membro.setEstado_Civil(new EstadoCivilNegocio().getEstadoCivil(this.estadoCivil));

            if(this.profissao > 0)
                this.membro.setProfissao(new ProfissaoNegocio().getProfissao(this.profissao));
            
            new MembroNegocio().salvarMembro(this.membro);
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, null, "Membro salvo com sucesso!"));
            
        } 
        catch (NegocioException ex) 
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, null, ex.getMessage()));
        }
        catch (Exception ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, null, ex.getMessage()));
        }
    }
    
    public void novoAction()
    {      
        this.membro = new MembroNegocio().getNovoMembro();
        this.profissao = 0;
        this.estadoCivil = 0;
        
        this.limparDadosProfissionaisAction();
        this.limparEnderecoAction();
        this.limparParenteAction();
        this.limparProfissaoAction();
        this.limparTelefoneAction();
        
        super.setObjetcSession("membro", this.membro);        
    }

    public DadosMinisteriais getDadosMinisteriais() {
        return dadosMinisteriais;
    }

    public void setDadosMinisteriais(DadosMinisteriais dadosMinisteriais) {
        this.dadosMinisteriais = dadosMinisteriais;
        
        if(this.dadosMinisteriais != null && this.dadosMinisteriais.getId() > 0)
        {
            this.idMinSel = this.dadosMinisteriais.getCargo().getMinisterio().getId();
            this.atualizaListaCargo();
            this.idCargoSel = this.dadosMinisteriais.getCargo().getId();
        }
    }

    public List<Ministerio> getListaMinisterios() {
        return listaMinisterios;
    }

    public void setListaMinisterios(List<Ministerio> listaMinisterios) {
        this.listaMinisterios = listaMinisterios;
    }

    public long getIdMinSel() {
        return idMinSel;
    }

    public void setIdMinSel(long idMinSel) {
        this.idMinSel = idMinSel;
    }

    public List<Cargo> getListaCargos() {
        return listaCargos;
    }

    public void setListaCargos(List<Cargo> listaCargos) {
        this.listaCargos = listaCargos;
    }

    public long getIdCargoSel() {
        return idCargoSel;
    }

    public void setIdCargoSel(long idCargoSel) {
        this.idCargoSel = idCargoSel;
        this.dadosMinisteriais.setCargo((Cargo) Util.getItemSelecionado(this.listaCargos, (int) this.idCargoSel));
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public Telefone getTelefone() {
        return telefone;
    }

    public List<TipoTelefone> getListaTiposTel() {
        return listaTiposTel;
    }

    public void setListaTiposTel(List<TipoTelefone> listaTiposTel) {
        this.listaTiposTel = listaTiposTel;
    }

    public int getIdTipoTelSel() {
        return idTipoTelSel;
    }

    public void setIdTipoTelSel(int idTipoTelSel) {
        this.idTipoTelSel = idTipoTelSel;
        
        this.telefone.setTipo( (TipoTelefone) Util.getItemSelecionado(this.listaTiposTel, this.idTipoTelSel) );
    }

    public long getProfissao() {
        return profissao;
    }

    public void setProfissao(long profissao) {
        this.profissao = profissao;
        this.membro.setProfissao((Profissao) Util.getItemSelecionado(this.profissoes, (int) this.profissao));
    }

    public long getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(long estadoCivil) {
        this.estadoCivil = estadoCivil;
    }    
    
    public void onChange(TabChangeEvent e) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, null, "Aba ativada é "+this.tabIndex));
    }
    
    //métodos para parentes
    public int getTabIndex() {
        return tabIndex;
    }

    public void setTabIndex(int tabIndex) {
        this.tabIndex = tabIndex;
    }

    public long getIdMembroParente() {
        return idMembroParente;
    }

    public void setIdMembroParente(long idMembroParente) {
        this.idMembroParente = idMembroParente;
    }

    public Parente getParente() {
        return parente;
    }

    public void setParente(Parente parente) {
        this.parente = parente;
    }

    public long getProfissaoParenteSel() {
        return profissaoParenteSel;
    }

    public void setProfissaoParenteSel(long profissaoParenteSel) {
        this.profissaoParenteSel = profissaoParenteSel;
    }

    public long getParentescoParenteSel() {
        return parentescoParenteSel;
    }

    public void setParentescoParenteSel(long parentescoParenteSel) {
        this.parentescoParenteSel = parentescoParenteSel;
    }

    public List<GrauParentesco> getParentescos() {
        return parentescos;
    }

    public void setParentescos(List<GrauParentesco> parentescos) {
        this.parentescos = parentescos;
    }
    
    public List<Membro> getMembrosPacote () {
        Membro mem = (Membro) super.getObjetcSession("usr");
        Pacote pkt = mem.getPacote();
        
        List<Membro> lista = new LinkedList<Membro>();
        
        for(int i = 0; i < pkt.getConteudo().size(); i++)
        {
            if(pkt.getConteudo().get(i) instanceof Membro)
                lista.add((Membro) pkt.getConteudo().get(i));
        }
        
        return lista;
    }
    
    public void limparParenteAction() {
        this.parente = new Parente();
        this.idMembroParente = 0;
        this.parentescoParenteSel = 0;
        this.profissaoParenteSel = 0;        
    }
    
    public void salvarParenteAction() {
        
        try
        {
            this.parente.setParentesco( (GrauParentesco) Util.getItemSelecionado(this.parentescos, (int) this.parentescoParenteSel) );
            this.parente.setProfissao(Util.getProfissaoSelecionado(this.profissoes, this.profissaoParenteSel));
            
            //informa que o parente é um membro já cadastrado
            //this.parente.setParenteMembro(Util.getMembroPktSelecionado(this.getMembrosPacote(), this.idMembroParente));
            
            /**
             * @TODO: informar ao membro adicionado como parente que ele é parente do membro atual
             */
            
            //adiciona o parente a lista de parentes do membro
            this.membro.getParentes().add(this.parente);

            new MembroNegocio().salvarMembro(this.membro);         
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, null, "Familiar adicionado com sucesso!"));
            
            this.limparParenteAction();
        } 
        catch (NegocioException ex) 
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, null, ex.getMessage()));
        }
        catch (Exception ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, null, ex.getMessage()));
            ex.printStackTrace();
        }
        
    }
    
    public void limparDadosMinisteriais() {
        this.dadosMinisteriais = new DadosMinisteriaisNegocio().getNovoDadosMinisteriais();
        this.idCargoSel = 0;
        this.idMinSel = 0;
        this.atualizaListaCargo();
    }
    
    public void salvarDadosMinisteriaisAction() {
        try
        {
            this.dadosMinisteriais.setMembro(this.membro);
            
            new DadosMinisteriaisNegocio().salvar(this.dadosMinisteriais);
            
            if(!this.membro.getDadosMinisteriais().contains(dadosMinisteriais))
                this.membro.getDadosMinisteriais().add(dadosMinisteriais);
            
            new MembroNegocio().salvarMembro(this.membro);
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, null, "Dados Ministeriais adicionado com sucesso!"));
        } 
        catch (NegocioException ex) 
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, null, ex.getMessage()));
        }
        catch (Exception ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, null, ex.getMessage()));
            ex.printStackTrace();
        }
    }
    
    public void salvarProfissaoAction() {
        try
        {        
            this.dadosProfissionais.setMembro(this.membro);
            
            new DadosProfissionaisNegocio().salvar(this.dadosProfissionais);
            
            this.membro.setDadosProfissionais(this.dadosProfissionais);
            
            new MembroNegocio().salvarMembro(this.membro);   
        
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, null, "Dados Profissionais adicionado com sucesso!"));
        } 
        catch (NegocioException ex) 
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, null, ex.getMessage()));
        }
        catch (Exception ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, null, ex.getMessage()));
            ex.printStackTrace();
        }
    }
    
    public void limparProfissaoAction() {
        this.ufPro = 2;
        this.municipioPro = 3;
        this.bairroPro = 0;
        this.profissao = 0;
    }
    
    public void salvarEnderecoAction() {
        
        try
        {        
            this.endereco.setBairro( (Bairro) Util.getItemSelecionado(this.bairros, (int)this.bairroEnd) );
            
            new EnderecoNegocio().validaEndereco(this.endereco);
            
            this.membro.getEndereco().add(this.endereco);
            new MembroNegocio().salvarMembro(this.membro);   
        
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, null, "Endereço adicionado com sucesso!"));
        } 
        catch (NegocioException ex) 
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, null, ex.getMessage()));
        }
        catch (Exception ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, null, ex.getMessage()));
            ex.printStackTrace();
        }
    }
    
    public void salvarTelefoneAction() {
        
        try
        {   
            new TelefoneNegocio().validaTelefone(this.telefone);
            
            this.membro.getTelefone().add(this.telefone);
            
            new MembroNegocio().salvarMembro(this.membro);   
        
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, null, "Telefone adicionado com sucesso!"));
        } 
        catch (NegocioException ex) 
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, null, ex.getMessage()));
        }
        catch (Exception ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, null, ex.getMessage()));
            ex.printStackTrace();
        }
    }
    
    public void limparDadosProfissionaisAction() {
        this.dadosProfissionais = new DadosProfissionaisNegocio().getNovoDadosProfissionais();
        this.limparProfissaoAction();
    }
    
    public void limparEnderecoAction() {
        this.endereco = new EnderecoNegocio().getNovoEndereco();
        this.ufEnd = 2;
        this.municipioEnd = 3;
        this.bairroEnd =0;
    }
    
    public void limparTelefoneAction() {
        this.telefone = new TelefoneNegocio().getNovoTelefone();
        this.idTipoTelSel = 0;
    }
    
    //método responsável de pegar os dados do membro selecionado no pacote e atribuir os respectivos valores ao parente
    public void atualizaParente() {
        if(this.idMembroParente > 0) {
            Membro mem = Util.getMembroPktSelecionado(this.getMembrosPacote(), this.idMembroParente);
            
            this.parente.setDtNasc(mem.getDataNasc());
            this.parente.setMasculino(mem.getMasculino());
            this.parente.setNome(mem.getNome());
            this.parente.setParentesco(null);
        }
    }
    
    public void atualizaListaCidades() {
        try
        {
            this.cidades = new CidadeNegocio().getLista(this.ufPro);
        }
        catch (NegocioException ex) 
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, null, ex.getMessage()));
        }
        catch (Exception ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, null, ex.getMessage()));
            ex.printStackTrace();
        }
    }
    
    public void atualizaListaBairros() {
        try
        {
            this.bairros = new BairroNegocio().getLista(this.municipioPro);  
        }
        catch (NegocioException ex) 
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, null, ex.getMessage()));
        }
        catch (Exception ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, null, ex.getMessage()));
            ex.printStackTrace();
        }
    }
    
    public void atualizaListaCargo() 
    {
        try
        {
            if(this.idMinSel > 0)
                this.listaCargos = new CargoNegocio().getLista( (Ministerio) Util.getItemSelecionado(this.listaMinisterios, (int) this.idMinSel));
            else
                this.listaCargos = new ArrayList<Cargo>();
        } 
        catch(NegocioException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, null, ex.getMessage()));
        }
        catch(Exception ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, null, ex.getMessage()));
        } 
    }

    public List<Estado> getEstados() {
        return estados;
    }

    public void setEstados(List<Estado> estados) {
        this.estados = estados;
    }

    public long getUfPro() {
        return ufPro;
    }

    public void setUfPro(long ufPro) {
        this.ufPro = ufPro;
    }

    public List<Cidade> getCidades() {
        return cidades;
    }

    public void setCidades(List<Cidade> cidades) {
        this.cidades = cidades;
    }

    public long getMunicipioPro() {
        return municipioPro;
    }

    public void setMunicipioPro(long municipioPro) {
        this.municipioPro = municipioPro;
    }

    public long getNaturalidade() {
        return naturalidade;
    }

    public void setNaturalidade(long naturalidade) {
        this.naturalidade = naturalidade;
    }

    public List<Bairro> getBairros() {
        return bairros;
    }

    public void setBairros(List<Bairro> bairros) {
        this.bairros = bairros;
    }

    public long getBairroPro() {
        return bairroPro;
    }

    public void setBairroPro(long bairroPro) {
        this.bairroPro = bairroPro;
        this.dadosProfissionais.setBairro((Bairro) Util.getItemSelecionado(this.bairros, (int)this.bairroPro));
    }

    public long getNumeroPro() {
        return numeroPro;
    }

    public void setNumeroPro(long numeroPro) {
        this.numeroPro = numeroPro;
    }

    public long getRamal() {
        return ramal;
    }

    public void setRamal(long ramal) {
        this.ramal = ramal;
    }

    public long getUfEnd() {
        return ufEnd;
    }

    public void setUfEnd(long ufEnd) {
        this.ufEnd = ufEnd;
    }

    public long getMunicipioEnd() {
        return municipioEnd;
    }

    public void setMunicipioEnd(long municipioEnd) {
        this.municipioEnd = municipioEnd;
    }

    public long getBairroEnd() {
        return bairroEnd;
    }

    public void setBairroEnd(long bairroEnd) {
        this.bairroEnd = bairroEnd;
    }

    public DadosProfissionais getDadosProfissionais() {
        return dadosProfissionais;
    }

    public void setDadosProfissionais(DadosProfissionais dadosProfissionais) {
        this.dadosProfissionais = dadosProfissionais;
    }

    public List<Escolaridade> getListaEscolaridade() {
        return listaEscolaridade;
    }

    public void setListaEscolaridade(List<Escolaridade> listaEscolaridade) {
        this.listaEscolaridade = listaEscolaridade;
    }

    public long getEscolaridade() {
        return escolaridade;
    }

    public void setEscolaridade(long escolaridade) {
        this.escolaridade = escolaridade;
        this.membro.setEscolaridade((Escolaridade) Util.getItemSelecionado(this.listaEscolaridade, (int) this.escolaridade));
    }

    public Foto getFotoPessoa() {
        return fotoPessoa;
    }

    public void setFotoPessoa(Foto fotoPessoa) {
        this.fotoPessoa = fotoPessoa;
    }

    public int getIndAtualFoto() {
        return indAtualFoto;
    }

    public void setIndAtualFoto(int indAtualFoto) {
        this.indAtualFoto = indAtualFoto;
    }

    public TipoAdmissao[] getTiposAdmissao() {
        return tiposAdmissao;
    }

    public FaixaEtaria[] getFaixasEtarias() {
        return faixasEtarias;
    }

    public TipoFrequentador[] getTiposFrequentador() {
        return tiposFrequentador;
    }
    
    public void excluir(Entidade item) {
        try
        {
            if(item instanceof Telefone)
            {
                if(this.membro.getTelefone().contains((Telefone) item))
                    this.membro.getTelefone().remove((Telefone) item);                                
            }
            else if(item instanceof Endereco)
            {
                if(this.membro.getEndereco().contains((Endereco) item))
                    this.membro.getEndereco().remove((Endereco) item);                                
            }
            else if(item instanceof Parente)
            {
                if(this.membro.getParentes().contains((Parente) item))
                    this.membro.getParentes().remove((Parente) item);                                
            }
            
            new MembroNegocio().salvarMembro(this.membro);
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, null, "Item excluído com sucesso!"));
        }
        catch(NegocioException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, null, ex.getMessage()));
        }
        catch(Exception ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, null, ex.getMessage()));
        } 
    }
    
    public void nextAction()
    {
        if (this.indAtualFoto < this.membro.getFotos().size() - 1) {
            this.indAtualFoto += 1;
        }
        
        this.fotoPessoa = ((Foto)this.membro.getFotos().get(this.indAtualFoto));
    }

    public void previusAction()
    {
        if (this.indAtualFoto > 0) {
            this.indAtualFoto -= 1;
        }
        
        this.fotoPessoa = ((Foto)this.membro.getFotos().get(this.indAtualFoto));
    }

    public void firstAction()
    {
        this.indAtualFoto = 0;
        this.fotoPessoa = ((Foto)this.membro.getFotos().get(this.indAtualFoto));
    }

    public void lastAction()
    {
        this.indAtualFoto = (this.membro.getFotos().size() - 1);
        this.fotoPessoa = ((Foto)this.membro.getFotos().get(this.indAtualFoto));
    }

    public void defineFotoPadrao()
    {
        int nrOrdem = this.membro.getFotoPrincipal().getNrOrdem() + 1;
        this.fotoPessoa.setNrOrdem(nrOrdem);

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso: ", "Foto atual definida como padrão com sucesso!"));
    }
    
    public void handleFileUploadFoto(FileUploadEvent event)
    {
        UploadedFile file = event.getFile();
        
        try
        {
            Foto f = new FotoNegocio().getNovaFoto();
            f.setPessoa(this.membro);
            
            if (this.membro.getFotos().size() > 0) {
              f.setNrOrdem(this.membro.getFotoPrincipal().getNrOrdem() + 1);
            }
            
            new FotoNegocio().salvar(f);

            String caminho = "membros" + File.separator + Integer.toString((int)f.getId() / 300) + File.separator;

            Util.criaPastas(Util.IMAGEM_PESSOA_PATH + caminho);

            caminho = caminho + Util.hashearFoto((int) f.getId());
            caminho = caminho + event.getFile().getFileName().substring(event.getFile().getFileName().lastIndexOf("."), event.getFile().getFileName().length()).toLowerCase();
            f.setCaminho(caminho);

            Util.copyFile(Util.IMAGEM_PESSOA_PATH + caminho, event.getFile().getInputstream());

            new FotoNegocio().salvar(f);

            this.membro.getFotos().add(0, f);
            this.fotoPessoa = this.membro.getFotoPrincipal();

            this.indAtualFoto = 0;
            
//            if (this.membro.getId() > 0)
//            {
//                f.registraObservador(this.membro);
//                this.membro.notificarAlteracao(8);
//            }
        }
        catch (NegocioException e)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Atenção:", e.getMessage()));
        }
        catch (IOException e)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, null, e.getMessage()));
            e.printStackTrace();
        }
        catch (Exception ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Erro:", ex.getMessage()));
        }
        
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso!", "Fotos recebidas com sucesso! Não esqueça de salvar!"));
    }

    public void excluirFoto()
    {
        try
        {
            String caminho = Util.IMAGEM_PESSOA_PATH + this.fotoPessoa.getCaminho();

            new FotoNegocio().excluir(this.fotoPessoa);

            Util.removerArquivos(new File(caminho));

            this.membro.getFotos().remove(this.fotoPessoa);

            this.fotoPessoa = this.membro.getFotoPrincipal();
            
//            if (this.membro.getId() > 0) {
//                this.membro.notificarAlteracao(8);
//            }
        }
        catch (Exception ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Erro:", ex.getMessage()));
        }
        
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso!", "Foto(s) excluida(s) com sucesso! Não esqueça de salvar!"));
    }
}


/*
 * 
 * para converter um objeto do tipo webservice para o tipo da regra de negócio utilizar o pattern adapter
 */
