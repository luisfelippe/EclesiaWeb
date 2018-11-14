
package ControleMembros.CLN.CGT;

import ControleMembros.CGD.FotoDAO;
import ControleMembros.CLN.CDP.Foto;
import ControleMembros.CLN.CDP.Pessoa;
import exception.NegocioException;
import java.util.List;

/**
 *
 * @author luisfelippe
 */
public class FotoNegocio 
{
    public Foto getNovaFoto()
    {
        return new Foto();
    }

    public Foto getFotoPadrao(List<Foto> fotos) throws NegocioException
    {
        Foto f = null;
        
        if (fotos.size() < 1) {
            return f;
        }
        
        int max = 0;
        
        for (int i = 0; i < fotos.size(); i++) 
        {
            if (((Foto)fotos.get(i)).getNrOrdem() > max)
            {
                max = ((Foto)fotos.get(i)).getNrOrdem();
                f = (Foto)fotos.get(i);
            }
        }
        
        return f;
    }

    public List<Foto> buscaListaFotos(Pessoa pessoa) throws NegocioException, Exception
    {
        if (pessoa == null) {
            throw new NegocioException("Não foram encontrados registros");
        }
        
        return new FotoDAO().getLista(pessoa);
    }

    public void salvar(Foto f) throws NegocioException, Exception
    {
        if (f == null) {
            throw new NegocioException("Foto inválida!");
        }
        
        if ((f.getPessoa() == null) || (f.getPessoa().getId() < 1)) {
            throw new NegocioException("Pessoa correspondente inválida!");
        }
        
        if ((f.getCaminho() == null) || (f.getCaminho().isEmpty())) {
            throw new NegocioException("Caminho para foto inválido!");
        }
        
        new FotoDAO().salvar(f);
    }

    public void excluir(Foto f) throws NegocioException, Exception
    {
        if ((f == null) || (f.getId() < 1)) {
            throw new NegocioException("Foto inválida!");
        }
        
        new FotoDAO().Excluir(f);
    }
}
