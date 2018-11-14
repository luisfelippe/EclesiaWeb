/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import ControleMembros.CLN.CDP.GrauParentesco;
import ControleMembros.CLN.CDP.Membro;
import ControleMembros.CLN.CDP.Profissao;
import util.Entidade;
import exception.NegocioException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Luis
 */
public abstract class Util_OLD {
    
    public static boolean isPreenchidoPadrao(String string){
        return Util.isPreenchido(string, 3);
    }
    
    public static boolean isPreenchido(String string, int qtdMinDigito){
        return (string != null && string.trim().length() >= qtdMinDigito);
    }
    
    public static boolean isPreenchidoExato(String string, int qtdDigito){
        return (string != null && string.trim().length() == qtdDigito);
    }
    
    public static boolean isCPFValido (String strCpf) throws NegocioException{
        // formato XXX.XXX.XXX-XX    
          
        if (! strCpf.substring(0,1).equals("")) {    
                
            boolean validado = true;    
            int     d1, d2;    
            int     digito1, digito2, resto;    
            int     digitoCPF;    

            String  nDigResult;    
            strCpf = strCpf.replace('.',' ');    
            strCpf = strCpf.replace('-',' ');    
            strCpf = strCpf.replaceAll(" ","");    
            d1 = d2 = 0;    
            digito1 = digito2 = resto = 0;    

            for (int nCount = 1; nCount < strCpf.length() -1; nCount++) {    
                digitoCPF = Integer.valueOf(strCpf.substring(nCount -1, nCount)).intValue();    

                //multiplique a ultima casa por 2 a seguinte por 3 a seguinte por 4 e assim por diante.    
                d1 = d1 + ( 11 - nCount ) * digitoCPF;    

                //para o segundo digito repita o procedimento incluindo o primeiro digito calculado no passo anterior.    
                d2 = d2 + ( 12 - nCount ) * digitoCPF;    
            }    

            //Primeiro resto da divisão por 11.    
            resto = (d1 % 11);    

            //Se o resultado for 0 ou 1 o digito é 0 caso contrário o digito é 11 menos o resultado anterior.    
            if (resto < 2)    
                digito1 = 0;    
            else    
                digito1 = 11 - resto;    

            d2 += 2 * digito1;    

            //Segundo resto da divisão por 11.    
            resto = (d2 % 11);    

            //Se o resultado for 0 ou 1 o digito é 0 caso contrário o digito é 11 menos o resultado anterior.    
            if (resto < 2)    

                digito2 = 0;    

            else    
                digito2 = 11 - resto;    

            //Digito verificador do CPF que está sendo validado.    
            String nDigVerific = strCpf.substring(strCpf.length()-2, strCpf.length());    

            //Concatenando o primeiro resto com o segundo.    
            nDigResult = String.valueOf(digito1) + String.valueOf(digito2);    

            //comparar o digito verificador do cpf com o primeiro resto + o segundo resto.    
            return nDigVerific.equals(nDigResult);                    
        }
        else 
            return false;
    }
    
    public static boolean isMailValido(String email){
        
        boolean isValido = false;
         
        if(email != null && email.length() > 0) {
            String expressao = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
            Pattern pattern = Pattern.compile(expressao, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(email);
            
            isValido = matcher.matches();
        }
        
        return isValido;
    }
    
    public static Entidade getItemSelecionado (Collection lista, long id) {
        
        Entidade e = null;
        
        if(lista != null) 
        {
            Iterator i = lista.iterator();
            
            while(i.hasNext()) 
            {
                e = (Entidade) i.next();

                if(e.getId() == id)
                    break;
                else
                    e = null;
            }
        }
        
        return e;
    }
    
    public static Profissao getProfissaoSelecionado (List<Profissao> lista, long id) 
    {        
        Profissao e = null;
        
        for(int i = 0; i < lista.size(); i++ ) 
        {
            if(lista.get(i).getId() == id)
            {
                e = lista.get(i);
                break;
            }
        }
        
        return e;
    }
    
    public static Membro getMembroPktSelecionado (List<Membro> lista, long id) 
    {        
        Membro e = null;
        
        for(int i = 0; i < lista.size(); i++ ) 
        {
            if(lista.get(i).getId() == id)
            {
                e = lista.get(i);
                break;
            }
        }
        
        return e;
    }
    
    public static void addItemPkt(Membro membro, Entidade item) {
        membro.getPacote().getConteudo().add(item);
    }
    
    public static String getMes(Integer mes)
    {
        String m[] = {"jan","fev", "mar", "abr", "mai", "jun", "jul", "ago", "set", "out", "nov", "dez"};
        return m[mes - 1];
    }
    
    public static String getMesExtenso(Integer mes)
    {
        String m[] = {"Janeiro","Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"};
        return m[mes - 1];
    }
}
