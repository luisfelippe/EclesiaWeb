/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import ControleMembros.CLN.CDP.Membro;
import ControleMembros.CLN.CDP.Profissao;
import ControleMembros.CLN.CDP.Visitante;
import ControleMembros.CLN.CGT.MembroNegocio;
import ControleMembros.CLN.CGT.ProfissaoNegocio;
import ControleMembros.CLN.CGT.VisitanteNegocio;
import controlefinanceiro.cln.cdp.CategoriaLancamento;
import controlefinanceiro.cln.cdp.TipoLancamento;
import controlefinanceiro.cln.cgt.CategoriaLancamentoNegocio;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Luis
 */
public class app {
    public static void main(String[] args)
    {
        try
        {
            CategoriaLancamento c = new CategoriaLancamento();
            c.setDescricao("teste");
            c.setTipo(TipoLancamento.SAIDA);
            
            new CategoriaLancamentoNegocio().salvar(c);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }        
    }
}
