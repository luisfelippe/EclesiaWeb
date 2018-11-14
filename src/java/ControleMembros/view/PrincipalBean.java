/**
 * @TODO principais mudanças no projeto:
 * - nas telas de lançamentos financeiros no campo de selecionar um membro deixar como auto complete
 * - se possível utilizar o ireports para a confecção de relatórios.
 * - criar perfis de usuários
 * - criar as regras de negócios com base no perfil de usr
 * - criar o controle de patrimonio da igreja
 * - criar o controle de acervo, com controle de empréstimos
 * - criar a configuração da igreja
 * - criar o subsistema de EBD (verificar com o superitendente e secretaria os requisitos)
 * - cadastro de crianças (são todas ainda não batizadas - ministério infantil)
 * - cadastro de congregados/relatorio (vem mas não confirma membresia)
 * 
 * OK - cadastro de categorias de entradas e saídas
  * - verificar outros possíveis relatórios
 * 
 * OBS: todo membro é batizado
 */

package ControleMembros.view;

import corporativo.ManageBean;
import ControleMembros.CLN.CDP.Cargo;
import ControleMembros.CLN.CDP.Membro;
import ControleMembros.CLN.CDP.Ministerio;
import ControleMembros.CLN.CDP.Profissao;
import ControleMembros.CLN.CDP.TipoTelefone;
import ControleMembros.CLN.CGT.CargoNegocio;
import ControleMembros.CLN.CGT.MembroNegocio;
import ControleMembros.CLN.CGT.MinisterioNegocio;
import ControleMembros.CLN.CGT.ProfissaoNegocio;
import ControleMembros.CLN.CGT.TipoTelefoneNegocio;
import controlefinanceiro.cln.cdp.TipoEntrada;
import controlefinanceiro.cln.cdp.TipoSaida;
import controlefinanceiro.cln.cgt.LancamentoNegocio;
import controlefinanceiro.cln.cgt.TipoEntradaNegocio;
import controlefinanceiro.cln.cgt.TipoSaidaNegocio;
import exception.NegocioException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.model.DashboardColumn;
import org.primefaces.model.DashboardModel;
import org.primefaces.model.DefaultDashboardColumn;
import org.primefaces.model.DefaultDashboardModel;
import util.Entidade;
import util.Util;

@ManagedBean
@ViewScoped
public class PrincipalBean extends ManageBean implements Serializable{

    private Membro usr;
    private Date data;
    private SimpleDateFormat df = new SimpleDateFormat("E - dd/MM/yyyy");
    private DashboardModel model;
    private List<Ministerio> listaMinisterios;
    private Ministerio ministerio;
    private long idMinCargoSel = 0;
    private List<Cargo> listaCargo;
    private Cargo cargo;
    private List<Profissao> listaProfissao;
    private Profissao profissao;
    private List<Membro> aniversariantes;  
    
    private List<TipoEntrada> listaTipoEntrada;
    private TipoEntrada tipoEntrada;
    
    private List<TipoSaida> listaTipoSaida;
    private TipoSaida tipoSaida;
    
    public PrincipalBean() 
    {        
        this.usr = (Membro) super.getObjetcSession("usr");  
        this.data = new Date();
        
        this.limparMinisterio();
        this.limparCargo();
        this.limparTipoEntrada();
        this.limparTipoSaida();
        this.limparProfissao();
        
        this.carregaListas();
        //this.drawDashboard();
        
        try
        {
            this.aniversariantes = new MembroNegocio().getListaAniverMes(data);
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
    
    public void drawDashboard() 
    {
        this.model = new DefaultDashboardModel();
        
        DashboardColumn column1 = new DefaultDashboardColumn();
        DashboardColumn column2 = new DefaultDashboardColumn();
        DashboardColumn column3 = new DefaultDashboardColumn();
        DashboardColumn column4 = new DefaultDashboardColumn();
        
        column1.addWidget("membro");
//        column1.addWidget("relAniversariante");
//        column1.addWidget("relDizimista");
        
        column2.addWidget("visitante");
//        column2.addWidget("relMembro");
//        column2.addWidget("relBalancete");
        
        column3.addWidget("usuario");
//        column3.addWidget("relVisitante");
        
//        column4.addWidget("entradas");
//        column4.addWidget("saidas");
               
        this.model.addColumn(column1);
        this.model.addColumn(column2);
        this.model.addColumn(column3);
        this.model.addColumn(column4);
    }

    public Membro getUsuario() {
        return this.usr;
    }

    public void setUsuario(Membro usr) {
        this.usr = usr;
    } 
    
    public Date getDataAtual() {
        return data;
    }
    
    public String getMesExtenso() {
        return Util.getMesExtenso(this.data.getMonth()+1);
    }
    
    public String getMesAnteriorExtenso() {
        int m = this.data.getMonth()-1;
        
        //valida se o mês for dezembro e o atual for janeiro
        if(m < 0)
            m = 12;
        
        return Util.getMesExtenso(m);
    }

    public DashboardModel getModel() {
        return model;
    }

    public void setModel(DashboardModel model) {
        this.model = model;
    }

    public Membro getUsr() {
        return usr;
    }

    public void setUsr(Membro usr) {
        this.usr = usr;
    }

    public SimpleDateFormat getDf() {
        return df;
    }

    public void setDf(SimpleDateFormat df) {
        this.df = df;
    }

    public List<Ministerio> getListaMinisterios() {
        return listaMinisterios;
    }

    public void setListaMinisterios(List<Ministerio> listaMinisterios) {
        this.listaMinisterios = listaMinisterios;
    }

    public Ministerio getMinisterio() {
        return ministerio;
    }

    public void setMinisterio(Ministerio ministerio) {
        this.ministerio = ministerio;
    }

    public List<Cargo> getListaCargo() {
        return listaCargo;
    }

    public void setListaCargo(List<Cargo> listaCargo) {
        this.listaCargo = listaCargo;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
        
        if(this.cargo != null && this.cargo.getMinisterio() != null && this.cargo.getMinisterio().getId() > 0)
            this.idMinCargoSel = this.cargo.getMinisterio().getId();
    }

    public List<Profissao> getListaProfissao() {
        return listaProfissao;
    }

    public void setListaProfissao(List<Profissao> listaProfissao) {
        this.listaProfissao = listaProfissao;
    }

    public Profissao getProfissao() {
        return profissao;
    }

    public void setProfissao(Profissao profissao) {
        this.profissao = profissao;
    }

    public long getIdMinCargoSel() {
        return idMinCargoSel;
    }

    public void setIdMinCargoSel(long idMinCargoSel) {
        this.idMinCargoSel = idMinCargoSel;
        this.cargo.setMinisterio((Ministerio) Util.getItemSelecionado(this.listaMinisterios, (int) idMinCargoSel));
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public List<Membro> getAniversariantes() {
        return aniversariantes;
    }

    public void setAniversariantes(List<Membro> aniversariantes) {
        this.aniversariantes = aniversariantes;
    }

    public List<TipoEntrada> getListaTipoEntrada() {
        return listaTipoEntrada;
    }

    public void setListaTipoEntrada(List<TipoEntrada> listaTipoEntrada) {
        this.listaTipoEntrada = listaTipoEntrada;
    }

    public TipoEntrada getTipoEntrada() {
        return tipoEntrada;
    }

    public void setTipoEntrada(TipoEntrada tipoEntrada) {
        this.tipoEntrada = tipoEntrada;
    }

    public List<TipoSaida> getListaTipoSaida() {
        return listaTipoSaida;
    }

    public void setListaTipoSaida(List<TipoSaida> listaTipoSaida) {
        this.listaTipoSaida = listaTipoSaida;
    }

    public TipoSaida getTipoSaida() {
        return tipoSaida;
    }

    public void setTipoSaida(TipoSaida tipoSaida) {
        this.tipoSaida = tipoSaida;
    }
    
    public void limparProfissao() {
        this.profissao = new Profissao();
    }
    
    public void limparCargo() {
        this.cargo = new Cargo();
    }
    
    public void limparMinisterio() {
        this.ministerio = new Ministerio();
    }
    
    public void limparTipoEntrada() {
        this.tipoEntrada = new TipoEntrada();
    }
    
    public void limparTipoSaida() {
        this.tipoSaida = new TipoSaida();
    }
    
    public void salvarProfissao() {
        try
        {
            new ProfissaoNegocio().salvar(this.profissao);
            this.carregaListas();
            this.limparProfissao();
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, null, "Profissão Cadastrada com Sucesso!"));
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
    
    public void salvarMinisterio() 
    {
        try
        {
            new MinisterioNegocio().salvar(this.ministerio);
            this.carregaListas();
            this.limparMinisterio();
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, null, "Ministério Cadastrado com Sucesso!"));
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
    
    public void salvarTipoEntrada() 
    {
        try
        {
            new TipoEntradaNegocio().salvar(this.tipoEntrada);
            this.carregaListas();
            this.limparTipoEntrada();
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, null, "Tipo de Entrada Cadastrado com Sucesso!"));
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
    
    public void salvarTipoSaida() 
    {
        try
        {
            new TipoSaidaNegocio().salvar(this.tipoSaida);
            this.carregaListas();
            this.limparTipoSaida();
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, null, "Tipo de Saída Cadastrado com Sucesso!"));
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
    
    public void salvarCargo() 
    {
        try
        {
            new CargoNegocio().salvar(this.cargo);
            this.carregaListas();
            this.limparCargo();
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, null, "Cargo Cadastrado com Sucesso!"));
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
    
    public void atualizaListaCargo() 
    {
        try
        {
            if(this.idMinCargoSel > 0)                
                this.listaCargo = new CargoNegocio().getLista( (Ministerio) Util.getItemSelecionado(this.listaMinisterios, (int) this.idMinCargoSel));
            else
                this.listaCargo = new ArrayList<Cargo>();
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
    
    public void carregaListas() 
    {
        try 
        {
            this.listaProfissao = new ProfissaoNegocio().getLista(null);
            this.listaMinisterios = new MinisterioNegocio().getLista();
            this.listaCargo = new CargoNegocio().getLista();
            this.listaTipoEntrada = new TipoEntradaNegocio().getLista(null);
            this.listaTipoSaida = new TipoSaidaNegocio().getLista(null);
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
    
    public long getQtdMembros() 
    {
        long qtd = 0;
        
        try
        {
            qtd = new MembroNegocio().getQtdMembros();
        } 
        catch(NegocioException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, null, ex.getMessage()));
        }
        catch(Exception ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, null, ex.getMessage()));
        } 
        
        return qtd;
    }
    
    public long getQtdMembrosAtivos() 
    {
        long qtd = 0;
        
        try
        {
            qtd = new MembroNegocio().getQtdMembrosAtivos();
        } 
        catch(NegocioException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, null, ex.getMessage()));
        }
        catch(Exception ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, null, ex.getMessage()));
        } 
        
        return qtd;
    }
    
    public long getQtdMembrosInativos() 
    {
        long qtd = 0;
        
        try
        {
            qtd = new MembroNegocio().getQtdMembrosInativos();
        } 
        catch(NegocioException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, null, ex.getMessage()));
        }
        catch(Exception ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, null, ex.getMessage()));
        } 
        
        return qtd;
    }
    
    public long getQtdMembrosOciosos() 
    {
        long qtd = 0;
        
        try
        {
            qtd = new MembroNegocio().getQtdMembrosOciosos();
        } 
        catch(NegocioException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, null, ex.getMessage()));
        }
        catch(Exception ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, null, ex.getMessage()));
        } 
        
        return qtd;
    }
    
    public long getQtdMinisterios() 
    {
        long qtd = 0;
        
        try
        {
            qtd = new MinisterioNegocio().getQtdMinisterios();
        } 
        catch(NegocioException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, null, ex.getMessage()));
        }
        catch(Exception ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, null, ex.getMessage()));
        } 
        
        return qtd;
    }
    
    public long getQtdMembrosBatizados() 
    {
        long qtd = 0;
        
        try
        {
            qtd = new MembroNegocio().getQtdMembrosBatizados();
        } 
        catch(NegocioException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, null, ex.getMessage()));
        }
        catch(Exception ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, null, ex.getMessage()));
        } 
        
        return qtd;
    }
    
    public long getQtdContribuintes() {
        long qtd = 0;
        
        try
        {
            qtd = new LancamentoNegocio().getQtdContribuintes();
        }
        catch(NegocioException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, null, ex.getMessage()));
        }
        catch(Exception ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, null, ex.getMessage()));
        } 
        
        return qtd;
    }
    
    public double getValorMedioPorContribuinte() {
        double qtd = 0;
        
        try
        {
            qtd = new LancamentoNegocio().getValorMedioPorContribuinte();
        }
        catch(NegocioException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, null, ex.getMessage()));
        }
        catch(Exception ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, null, ex.getMessage()));
        } 
        
        return qtd;
    }
    
    public double getValorArrecadacaoMesAnterior() {
        double qtd = 0;
        
        try
        {
            qtd = new LancamentoNegocio().getValorEntradaMesAnterior();
        }
        catch(NegocioException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, null, ex.getMessage()));
        }
        catch(Exception ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, null, ex.getMessage()));
        } 
        
        return qtd;
    }
    
    public double getValorArrecadacaoMesAtual() {
        double qtd = 0;
        
        try
        {
            qtd = new LancamentoNegocio().getValorEntradaMes();
        }
        catch(NegocioException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, null, ex.getMessage()));
        }
        catch(Exception ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, null, ex.getMessage()));
        } 
        
        return qtd;
    }
    
    public double getValorSaidaMesAtual() {
        double qtd = 0;
        
        try
        {
            qtd = new LancamentoNegocio().getValorSaidaMes();
        }
        catch(NegocioException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, null, ex.getMessage()));
        }
        catch(Exception ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, null, ex.getMessage()));
        } 
        
        return qtd;
    }
    
    public double getValorSaidaMesAnterior() {
        double qtd = 0;
        
        try
        {
            qtd = new LancamentoNegocio().getValorSaidaMesAnterior();
        }
        catch(NegocioException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, null, ex.getMessage()));
        }
        catch(Exception ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, null, ex.getMessage()));
        } 
        
        return qtd;
    }           
    
    @Override
    public void excluir(Entidade item) {
        try
        {
            if(item instanceof Profissao)
            {
                new ProfissaoNegocio().excluir(item);                                
            }
            else if(item instanceof Ministerio)
            {
                new MinisterioNegocio().excluir(item);
            }
            else if(item instanceof Cargo)
            {
                new CargoNegocio().excluir(item);
            }
            else if(item instanceof TipoTelefone)
            {
                new TipoTelefoneNegocio().excluir(item);
            }
            
            this.carregaListas();
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
}
