
package util;

import ControleMembros.CLN.CDP.Membro;
import ControleMembros.CLN.CDP.Profissao;
import exception.NegocioException;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.Normalizer;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.codec.digest.DigestUtils;


/**
 *
 * @author Luis
 */
public abstract class Util
{    
    public final static String IMAGEM_PESSOA_PATH = File.separator + "opt" + File.separator + "ArquivosEclesiaWeb" + File.separator + "bd-fotos" + File.separator;
    public final static String DOWN_PATH = File.separator + "opt" + File.separator + "ArquivosEclesiaWeb" + File.separator + "down" + File.separator;
    //N�o alterar a chave, impacta diretamente no hashearFoto()
    public final static String CHAVE_FOTO = "bd-fotos@imges4@$*";
    
    /**
     * Troca barras pelo File.separator
     * @param cam
     * @return 
     */
    public static String insereFileSeparator(String cam){
        cam = cam.replace("\\", File.separator);
        cam = cam.replace("/", File.separator);
        return cam;
    }
    
    /**
     * Retorna o IP da m�quina cliente
     *
     * @param request
     * @return String (Ip Cliente)
     */
    public static String getClientIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    
    /**
     * Hashea uma string passa juntamente com a chave, utilizado para embaralhar a id da Foto.
     * Nunca utilizar para armazenar hash no banco, somente grava��o em disco.
     * @param id
     * @return 
     */
    public static String hashearFoto(int id){
        return DigestUtils.sha256Hex(id + CHAVE_FOTO);
    }
    
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
            
            //Altera��o 30/07/2015
            if (strCpf.equals("00000000000") || strCpf.equals("11111111111")
                    || strCpf.equals("22222222222") || strCpf.equals("33333333333")
                    || strCpf.equals("44444444444") || strCpf.equals("55555555555")
                    || strCpf.equals("66666666666") || strCpf.equals("77777777777")
                    || strCpf.equals("88888888888") || strCpf.equals("99999999999")
                    || (strCpf.length() != 11)) 
            {
                return (false);
            }
            
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
    
    public static boolean isRgPMESValido(String rg)
    {
        /* preenche com 0 a esquerda */
        rg = String.format("%8s", rg).replace(' ', '0');
        
        System.err.println(rg);
        
        /* extrai cada digito e converte para inteiro */
        int n1 = Integer.parseInt(rg.substring(7), 10);
        int n2 = Integer.parseInt(rg.substring(6,7), 10);
        int n3 = Integer.parseInt(rg.substring(5,6), 10);
        int n4 = Integer.parseInt(rg.substring(4,5), 10);
        int n5 = Integer.parseInt(rg.substring(3,4), 10);
        int n6 = Integer.parseInt(rg.substring(2,3), 10);
        int n7 = Integer.parseInt(rg.substring(1,2), 10);
        int n8 = Integer.parseInt(rg.substring(0,1), 10);
        
        /* calcula o digito */
        int d1 = 40 + n8*8 + n7*7 + n6*6 + n5*5 + n4*4 + n3 *3 + n2*2;
        d1 = 11 - (d1 % 11);
        
        /* acerta o digito */
        switch(d1)
        {
            case 11 : d1 = 0; break;
            case 10 : d1 = 5; break;
        }
        
        int calculado = d1;
        int digitado = n1;
        
        /* verifica se o digito calculado confere com o digitado*/
        if(calculado == digitado)
            return true;
        
        return false;
    }
    
    public static Date addDays(int qtd) {
        GregorianCalendar cal = (GregorianCalendar) GregorianCalendar.getInstance();

        cal.add(GregorianCalendar.DATE, qtd); //soma a quantidade de dias        
        Date nova_data = cal.getTime();
        
        return nova_data;
    }
    
//    public static Date addDays(Date data, int qtd) {
//        Date nova_data = new Date(data.getTime()+((1000*24*60*60)*qtd));
//        
//        return nova_data;
//    }
    
    /**
     * Adiciona "x" dias para uma determinada data, definindo uma nova hora e minuto
     * @param data data que ser� somada
     * @param qtd quantidade de dias a ser somado
     * @param hora hora a ser definida
     * @param min minuto a ser definido
     * @return novaData nova data somada "qtd" dias no hor�rio "hora" no minuto "min"
     */
    public static Date addDays(Date data, int qtd, int hora, int min) {
        
        GregorianCalendar cal = new GregorianCalendar();

        cal.setTime(data);
        cal.add(GregorianCalendar.DATE, qtd); //soma a quantidade de dias
        cal.set(GregorianCalendar.HOUR_OF_DAY, hora);
        cal.set(GregorianCalendar.MINUTE, min);
        cal.set(GregorianCalendar.SECOND, 0);
        
        Date nova_data = cal.getTime();
        
        return nova_data;
    }
    
//    public static Date addHours(int qtd) {
//        Date hoje = new Date();          
//        Date nova_data = new Date(hoje.getTime()+((1000*60*60)*qtd));
//        
//        return nova_data;
//    }
//    
//    public static Date addHours(Date data, int qtd) {
//        Date nova_data = new Date(data.getTime()+((1000*60*60)*qtd));
//        
//        return nova_data;
//    }
//    
//    public static Date addMin(int qtd) {
//        Date hoje = new Date();          
//        Date nova_data = new Date(hoje.getTime()+((1000*60)*qtd));
//        
//        return nova_data;
//    }
//    
//    public static Date addMin(Date data, int qtd) {
//        Date nova_data = new Date(data.getTime()+((1000*60)*qtd));
//        
//        return nova_data;
//    }
    
    public static Entidade getItemSelecionado (Collection lista, int id) {
        
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
    
    public static int getNumeroRandomico() {
        int i = (int) (Math.random() * 10000);
        return i;
    } 
    
    public static void criaArquivo(String arquivo, byte[] dados) throws FileNotFoundException, IOException {
        FileImageOutputStream imageOutput;

        imageOutput = new FileImageOutputStream(new File(arquivo));
        imageOutput.write(dados, 0, dados.length);
        imageOutput.close();        
    }
    
    public static void verificaExistenciaArquivo(String arquivo) {
        if (arquivo != null) {
            File file = new File(arquivo);
            if (file.exists()) {
                file.delete();
            }
        }
    }
    
    public static void convertePngToJpg(String pathFile) throws IOException {
        BufferedImage bufferedImage;
        
        //read image file
        bufferedImage = ImageIO.read(new File(pathFile));

        // create a blank, RGB, same width and height, and a white background
        BufferedImage newBufferedImage = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(), BufferedImage.TYPE_INT_RGB);
        newBufferedImage.createGraphics().drawImage(bufferedImage, 0, 0, Color.WHITE, null);

        // write to jpeg file
        ImageIO.write(newBufferedImage, "jpg", new File(pathFile.replace("png", "jpg")));
    }  
    
    public static String MySQLPassword(String plainText) throws UnsupportedEncodingException
    {
        byte[] utf8 = plainText.getBytes("UTF-8"); 
        byte[] test = DigestUtils.sha(DigestUtils.sha(utf8));
        return "*" + convertToHex(test).toUpperCase();
    }
    
    public static String convertToHex(byte[] data) 
    {
        StringBuffer buf = new StringBuffer();
        
        for (int i = 0; i < data.length; i++) 
        {
            int halfbyte = (data[i] >>> 4) & 0x0F;
            int two_halfs = 0;
            
            do {
                if ((0 <= halfbyte) && (halfbyte <= 9))
                    buf.append((char) ('0' + halfbyte));
                else
                    buf.append((char) ('a' + (halfbyte - 10)));
                
                halfbyte = data[i] & 0x0F;
                
            } while (two_halfs++ < 1);
        }
        return buf.toString();
    }
    
    public static String regrasConsoantesMudas(String variavel) {
        String letras_mudas_1 = "BCDFGJKPTV"; // Letras mudas que fazem encontro consonantal com R e L
        String letras_mudas_2 = "MNQRSXZ"; // Letras mudas que n�o fazem encontro consonantal com R e L
        String consoantes = "BCDFGHJKLMNPQRSTVXZ"; // Consoantes
        String v1 = "AEIOURL";
        String v2 = "AEIOU";
        
        int i = 0;
        
        while(i <= (variavel.length() -1) )
        {
            // elimina a consoante muda no final da palavra
            if( ( ( consoantes.indexOf( String.valueOf( variavel.charAt(i) ) ) >= 0 ) && (i > 0) && ( (i == (variavel.length() - 1) ) || variavel.charAt(i+1) == ' ') ) )
            {
                variavel = variavel.substring( 0, i ) + variavel.substring( i+1, variavel.length() ); // exclui caracter
            }
            else if( ( letras_mudas_1.indexOf( String.valueOf( variavel.charAt(i) ) ) >= 0 ) && ( (i == (variavel.length() - 1) ) || (v1.indexOf( String.valueOf( variavel.charAt(i+1) ) ) < 0 ) ) )
            {
                variavel = variavel.substring(0, i + 1) + "I" + variavel.substring(i + 1, variavel.length() );
                i++;
            }
            else if( (variavel.charAt(i) == 'L') && ( (i == (variavel.length() - 1) ) || (v2.indexOf( String.valueOf( variavel.charAt(i+1) ) ) < 0 ) ) )
            {
                char[] temp = variavel.toCharArray();
                temp[i] = 'U';
                variavel = String.valueOf(temp);
                i++;
            }
            else if( ( letras_mudas_2.indexOf( String.valueOf( variavel.charAt(i) ) ) >= 0 ) && ( ( i == (variavel.length() - 1) ) || (v2.indexOf( String.valueOf( variavel.charAt(i+1) ) ) < 0 ) ) )
            {
                variavel = variavel.substring(0, i) + variavel.substring(i+1, variavel.length()); // exclui caracter
            }
            else
            {
                i++;
            }
        }
        
        return variavel;
    }
    
    public static String tiraAcentos(String variavel) {
        variavel = Normalizer.normalize(variavel, Normalizer.Form.NFD);
        variavel = variavel.replaceAll("[^\\p{ASCII}]", "");
        return variavel;
    }
    
    public static String tiraLetrasRepetidas(String variavel) 
    {
        int i = 0;
        char[] temp;
        
        while(i <= variavel.length()) {
            temp = variavel.toCharArray();
            
            if((i < (variavel.length() -1)) && (temp[i] == temp[i+1])) {
                variavel = String.valueOf(temp);
                variavel = variavel.substring(0, i+1) + variavel.substring(i+2, variavel.length());
            }
            
            i++;
        }
        
        return variavel;
    }
    
    public static String soundex(String variavel) {
        
        variavel = variavel.toUpperCase();
        
        variavel = Util.tiraAcentos(variavel);
        
        variavel = variavel.replace("Y", "I");
        variavel = variavel.replace("W", "U");
        
        variavel = Util.tiraLetrasRepetidas(variavel);
        
        variavel = variavel.replace("CE", "SE");
        variavel = variavel.replace("CI", "SI");
        variavel = variavel.replace("CH", "X");
        variavel = variavel.replace("CK", "K");
        variavel = variavel.replace("CHR", "KR");
        variavel = variavel.replace("C", "K");
        variavel = variavel.replace("Ç", "S");
        
        variavel = variavel.replace("GE", "JE");
        variavel = variavel.replace("GI", "JI");
        variavel = variavel.replace("GUE", "GE");
        variavel = variavel.replace("GUI", "GI");
        variavel = variavel.replace("GUA", "GA");
        variavel = variavel.replace("GUO", "GO");
        
        variavel = variavel.replace("LH", "£");
        variavel = variavel.replace("NH", "µ");
        variavel = variavel.replace("H", "");
        variavel = variavel.replace("GUO", "GO");
        
        variavel = variavel.replace("NGT", "NT");
        
        variavel = variavel.replace("PH", "F");
        
        variavel = variavel.replace("QUE", "KE");
        variavel = variavel.replace("QUI", "KI");
        variavel = variavel.replace("QUA", "KA");
        variavel = variavel.replace("QUO", "KO");
        variavel = variavel.replace("QU", "KU");
        
        variavel = variavel.replace("SH", "X");
        
        variavel = variavel.replace("VR", "V");
        
        variavel = variavel.replace("XS", "X");
        
        variavel = Util.tiraLetrasRepetidas(variavel);        
        variavel = Util.regrasConsoantesMudas(variavel);
        
        variavel = variavel.replace("OE", "AO");
        variavel = variavel.replace("AE", "AO");
        variavel = variavel.replace("S", "Z");
        variavel = variavel.replace("X", "Z");
        variavel = variavel.replace("G", "K");
        variavel = variavel.replace("I", "E");
        variavel = variavel.replace("U", "O");
        
        variavel = Util.tiraLetrasRepetidas(variavel);
        
        return variavel;
    }
    
    public static String trocaCharsEspeciais(String texto){
        return (Normalizer.normalize(texto, Normalizer.Form.NFD)).replaceAll("[^\\p{ASCII}]","" );
    }
    
    /**
     * M�todo respons�vel por excrever um arquivo no diretorio e com o nome definido no filename
     * @param fileName caminho e nome do arquivo (ex: /home/usr/foto.jpg)
     * @param in Stream de entrada, o arquivo propriamente dito
     */
    public static void copyFile(String fileName, InputStream in) {
        try 
        {  
            // write the inputStream to a FileOutputStream
            OutputStream out = new FileOutputStream(new File(fileName));

            int read = 0;
            byte[] bytes = new byte[1024];

            while ((read = in.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }

            in.close();
            out.flush();
            out.close();
        } 
        catch (IOException e) 
        {
             FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, null, e.getMessage()));
        }
    }
    
    /**
     * cria um diretorio ou arquivo caso n�o exista
     * @param path 
     */
    public static void criaPastas(String path) 
    {        
        File diretorio = new File(path);  
        
        if (!diretorio.exists()) {  
           diretorio.mkdirs(); //mkdir() cria somente um diret�rio, mkdirs() cria diret�rios e subdiret�rios.  
        } 
    }
    
    /**
     * Exclui arquivo existente ou uma pasta com seus arquivos
     * @param f 
     */
    public static void removerArquivos(File f) { 
        if (f.isDirectory()) 
        { 
            File[] files = f.listFiles(); 
            
            for(File file : files) { 
                removerArquivos(file); 
            } 
        } 
        
        f.delete(); 
    }
    
    public static String acertaNome(String nome) 
    {
        //elimina os espa�os em branco � direita e � esquerda
        nome = nome.trim();
        
        //elimina os espa�os duplos
        nome = nome.replace("  ", " ");
        
        //adiciona um espa�o no in�cio e no final do nome - este espa�o ser� removido no final da rotina
        //eles t�m a finalidade de tratar a primeira e a �ltima palavra da string da mesma forma que s�o tratadas as palavras do meio
        
        nome = " " + nome + " ";
        
        //coloca todo o nome em caixa alta
        nome = nome.toUpperCase();
        
        //coloca em min�sculo as conjun��es
        nome = nome.replace(" DE "  , " de ");
        nome = nome.replace(" DA "  , " da ");
        nome = nome.replace(" DO "  , " do ");
        nome = nome.replace(" DAS " , " das ");
        nome = nome.replace(" DOS " , " dos ");
        nome = nome.replace(" E "   , " e ");
        nome = nome.replace(" EM "  , " em ");
        nome = nome.replace(" NA "  , " na ");
        nome = nome.replace(" NAS " , " nas ");
        nome = nome.replace(" NO "  , " no ");
        nome = nome.replace(" NOS " , " nos ");
        nome = nome.replace(" AO "  , " ao ");
        nome = nome.replace(" AOS " , " aos ");
        nome = nome.replace(" À "   , " à ");
        nome = nome.replace(" A "   , " a ");
        nome = nome.replace(" O "   , " o ");
        nome = nome.replace(" Á "   , " á ");
        
        //coloca a segunda letra em diante de cada palavra em min�sculo
        char[] nomeTmp = nome.toCharArray();
        
        for(int i = 1; i < nome.length(); i++) {                        
            if(nomeTmp[i-1] != ' ' && nomeTmp[i-1] != '(' && nomeTmp[i-1] != '\''){
                nomeTmp[i] = Character.toLowerCase(nomeTmp[i]);
            }   
        }
        
        nome = String.copyValueOf(nomeTmp);
        
        //coloca em caixa alta algumas palavras especiais
        nome = nome.replace(" Ii "  , " II ");
        nome = nome.replace(" Iii "  , " III ");
        nome = nome.replace(" Iv "  , " IV ");
        nome = nome.replace(" Vi "  , " VI ");
        nome = nome.replace(" Vii "  , " VII ");
        nome = nome.replace(" Viii "  , " VIII ");
        nome = nome.replace(" Ix "  , " IX ");
        nome = nome.replace(" Xi "  , " XI ");
        nome = nome.replace(" Xii "  , " XII ");
        nome = nome.replace(" Xiii "  , " XIII ");
        nome = nome.replace(" Xiv "  , " XIV ");
        nome = nome.replace(" Xv "  , " XV ");
        nome = nome.replace(" Xvi "  , " XVI ");
        nome = nome.replace(" Xvii "  , " XVII ");
        nome = nome.replace(" Xviii "  , " XVIII ");
        nome = nome.replace(" Xix "  , " XIX ");
        nome = nome.replace(" Xx "  , " XX ");
        
        //remove o espa�o ao final do nome - este espa�o foi colocado por esta rotina
        nome = nome.trim();
        
        return nome;
    }
    
    public static String abreviaNome(String nome)
    {
        //acerta o nome
        nome = Util.acertaNome(nome);
        
        //separa os nomes
        String[] nomes = nome.split(" "); 
        
        //Abreviar a partir do segundo nome, exceto o �ltimo.
        for(int i = 1; i < nomes.length - 1; i++)
        {
            //Cont�m mais de 4 letras? (ignorar de, da, das, do, dos, etc.)
            if(nomes[i].length() > 4)
                nomes[i] = nomes[i].charAt(0) + "."; //Pega apenas a primeira letra do nome e coloca um ponto ap�s.
        }
        
        //junta novamente
        nome = String.join(" ", nomes); 
        
        return nome;
    }
    
//    public static String MD5(String valor) throws NoSuchAlgorithmException, UnsupportedEncodingException {
//        String md5 = "";
//        
//        MessageDigest m = MessageDigest.getInstance("MD5");
//        m.update(valor.getBytes(), 0, valor.length());
//        
//        byte[] bytes = m.digest( valor.getBytes());
//        //BigInteger hash = new BigInteger(1, m.digest());
//        
//        //md5 = hash.toString(16);
//        md5 = stringHexa(bytes);
//        
//        return md5;
//    }
//    
//    private static String stringHexa(byte[] bytes) {  
//        StringBuilder s = new StringBuilder();  
//        
//        for (int i = 0; i < bytes.length; i++) {  
//            int parteAlta = ((bytes[i] >> 4) & 0xf) << 4;  
//            int parteBaixa = bytes[i] & 0xf;  
//            if (parteAlta == 0) s.append('0');  
//            s.append(Integer.toHexString(parteAlta | parteBaixa));  
//        }  
//        
//        return s.toString();  
//     } 
//    
//    private static String getString( byte[] bytes ) 
//    {
//        StringBuffer sb = new StringBuffer();
//        
//        for( int i=0; i < bytes.length; i++ )     
//        {
//            byte b = bytes[ i ];
//            String hex = Integer.toHexString((int) 0x00FF & b);
//            
//            if (hex.length() == 1) 
//            {
//                sb.append("0");
//            }
//            
//            sb.append( hex );
//        }
//        
//        return sb.toString();
//    }
    
    /**
     * Here is Java implementation of MD5 hashing that will produce exactly the same result as md5() function in PHP and MySQL
     * @see http://www.sergiy.ca/how-to-make-java-md5-and-sha-1-hashes-compatible-with-php-or-mysql/
     * @param input
     * @return
     * @throws NoSuchAlgorithmException 
     */
    public static String MD5(String input) throws NoSuchAlgorithmException 
    {
        String result = input;
        
        if(input != null) 
        {
            MessageDigest md = MessageDigest.getInstance("MD5"); //or "SHA-1"
            md.update(input.getBytes());
            BigInteger hash = new BigInteger(1, md.digest());
            result = hash.toString(16);
            
            while(result.length() < 32) { //40 for SHA-1
                result = "0" + result;
            }
        }
        
        return result;
    }
    
    public static String getMes(Integer mes)
    {
        String m[] = {"jan","fev", "mar", "abr", "mai", "jun", "jul", "ago", "set", "out", "nov", "dez"};
        return m[mes - 1];
    }
    
    public static String getMesExtenso(Integer mes)
    {
        String m[] = {"janeiro","fevereiro", "março", "abril", "maio", "junho", "julho", "agosto", "setembro", "outubro", "novembro", "dezembro"};
        return m[mes - 1];
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
    
    public static String lerModelo(File modelo, String charset) throws IOException 
    {
        StringBuilder builder = new StringBuilder();
        String linha = null;
        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(modelo), charset));
        
        while ((linha = in.readLine()) != null) 
        {
            builder.append(linha);
        }
        
        in.close();
        
        return builder.toString();
    }
    
    public static File gravarArquivo(String path, String conteudo) throws IOException 
    {
        File file = new File(path);
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        
        //Substitui��o do \n por \par (Quebra de linha no .RTF)
        conteudo = conteudo.replaceAll("\\n", " \\\\par ");
        conteudo = conteudo.replaceAll("\\t", " \\\\tab ");
        
        //corrigir caracteres com acentos
        conteudo = conteudo.replaceAll("\\'c3\\rdblquote ", "}{\\f1 \\'d4}{\\f1 ");
        
        writer.write(conteudo);

        writer.flush();
        writer.close();
        
        //System.out.println("FIM GRAVA��O: " + path + new Date().getSeconds());
        
        return file;
    }
    
    public static void downloadFile(File file, String mimeType, FacesContext contexto) throws Exception
    {
        FileInputStream in = null;
        
        try 
        {
            ExternalContext context = contexto.getExternalContext();
            
            HttpServletResponse response = (HttpServletResponse) context.getResponse();
            response.setHeader("Content-Disposition", "attachment;filename=\"" + file.getName() + "\""); 
            response.setContentLength((int) file.length()); 
            response.setContentType(mimeType);
            
            in = new FileInputStream(file);
            
            OutputStream out = response.getOutputStream();
            
            byte[] buf = new byte[(int) file.length()];
            
            int count;
            while ((count = in.read(buf)) >= 0) {
                out.write(buf, 0, count);
            }
            
            contexto.responseComplete();
        } 
        catch (Exception exc) 
        {
            exc.printStackTrace();
            throw new Exception("Erro ao tentar realizar o download.");            
        } 
        finally 
        {
            try 
            {
                if (in != null) 
                {
                    in.close();
                }
            } 
            catch (IOException ex) 
            {
                throw new Exception("Erro ao tentar fechar o arquivo de download no servidor.");
            }
        }
    }
    
    public static void downloadFile(File file, String mimeType, HttpServletResponse response) throws Exception
    {
        FileInputStream in = null;
        
        try 
        {
            //ExternalContext context = contexto.getExternalContext();                        
            response.setHeader("Content-Disposition", "attachment;filename=\"" + file.getName() + "\""); 
            response.setContentLength((int) file.length()); 
            response.setContentType(mimeType);
            
            in = new FileInputStream(file);
            
            OutputStream out = response.getOutputStream();
            
            byte[] buf = new byte[(int) file.length()];
            
            int count;
            while ((count = in.read(buf)) >= 0) {
                out.write(buf, 0, count);
            }
        } 
        catch (Exception exc) 
        {
            exc.printStackTrace();
            throw new Exception("Erro ao tentar realizar o download.");            
        } 
        finally 
        {
            try 
            {
                if (in != null) 
                {
                    in.close();
                }
            } 
            catch (IOException ex) 
            {
                throw new Exception("Erro ao tentar fechar o arquivo de download no servidor.");
            }
        }
    }
    
    /**
     * M�todo respons�vel por converter um array de bytes em hexadecimal
     * @see http://stackoverflow.com/questions/9655181/how-to-convert-a-byte-array-to-a-hex-string-in-java
     * @param bytes
     * @return string contendo o valor em hexadecimal
     */
    public static String bytesToHex(byte[] bytes) 
    {
        char[] hexArray = "0123456789ABCDEF".toCharArray();
        char[] hexChars = new char[bytes.length * 2];
        
        for ( int j = 0; j < bytes.length; j++ ) 
        {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        
        return new String(hexChars);
    }
    
    public static Long unixTimestamp(Date d) 
    {   
        Calendar c = Calendar.getInstance(); 
        c.setTime(d);
//        c.set(Calendar.YEAR, 1982); 
//        c.set(Calendar.MONTH, Calendar.JUNE); 
//        c.set(Calendar.DATE, 22);
//        c.set(Calendar.HOUR_OF_DAY, 15);            
//        c.set(Calendar.MINUTE, 0);
//        c.set(Calendar.SECOND, 0);
            
        return (c.getTime().getTime()/1000);
    }
    
    public static boolean isSenhaValida(String senha) {
        Pattern ptr = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[@#$%^&+=*])(?=\\S+$).{6,}$");
        return ptr.matcher(senha).matches();
    }
    
    public static long fatorial(int n) {
        long ret = 1;                
        
        for(int i = n; i > 0; i--) {
            ret *= i;
        }
        
        return ret;
    }
    
    public static String nl2br(String str) {
        return str.replaceAll("(\r\n|\n)", "<br />");
    }
}
