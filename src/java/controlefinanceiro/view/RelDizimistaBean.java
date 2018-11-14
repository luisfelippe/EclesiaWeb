/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlefinanceiro.view;

import exception.NegocioException;
import corporativo.ManageBean;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Cell;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Table;
import controlefinanceiro.cln.cdp.Entrada;
import controlefinanceiro.cln.cgt.EntradaNegocio;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

/**
 *
 * @author LuísFelippe
 */
@ManagedBean
@ViewScoped
public class RelDizimistaBean extends ManageBean{
    private Date dataIni;
    private Date dataFim;
    private List<Entrada> lista;
    private double total;
    
    public RelDizimistaBean() {
        
    }

    public Date getDataIni() {
        return dataIni;
    }

    public void setDataIni(Date dataIni) {
        this.dataIni = dataIni;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }

    public List<Entrada> getLista() {
        return lista;
    }

    public void setLista(List<Entrada> lista) {
        this.lista = lista;
    }
    
    public void buscar() {
        try
        {
            this.lista = new EntradaNegocio().getListaDizimista(dataIni, dataFim);
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
    
    public void limpar() {
        this.dataIni = null;
        this.dataFim = null;
    }
    
    public void cancelar() {
        //return "principal";
        try 
        {
            FacesContext.getCurrentInstance().getExternalContext().redirect("./principal.xhtml");
        }
        catch (Exception ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, null, ex.getMessage()));
        }
    }

    public double getTotal() {
        if(!this.lista.isEmpty())
        {
            this.total = 0;
            
            for(Entrada dizimos : this.lista)
            {
                this.total += dizimos.getValor();
            }
        }
        
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }    
    
    public void preProcessPDF(Object document) { 
        try
        {
            Document pdf = (Document) document;  
            pdf.open();  
            pdf.setPageSize(PageSize.A4);  

            ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();  
            String logo = servletContext.getRealPath("") + File.separator + "images" + File.separator + "logo-iWeb2.png";  

            Image img = Image.getInstance(logo);
            img.scaleAbsolute(70,80);
            img.setAlignment(Image.RIGHT);            
            
            Phrase p = new Phrase();
            
            Table t = new Table(2,1);
            t.setWidth(100);
            float[] widths = {10,90};
            t.setWidths(widths);
            t.setBorderWidth(0);
            t.setBorderColor(new Color(0,0,0));
            
            //Chunk ck = new Chunk(img, 0, -40);
            
            Cell c1 = new Cell();
            c1.add(img);
            c1.setBorderWidth(0);
            
            t.addCell(c1);
            
            Cell c2 = new Cell(new Phrase("\t\t\tiWeb - Gerenciamento Eclesiástico\n\t\t\tIgreja Manancial da Graça", new Font(Font.HELVETICA, 14, Font.BOLD, new Color(0,0,0))));
            c2.setHorizontalAlignment(Cell.ALIGN_LEFT);
            c2.setBorderWidth(0);
            
            t.addCell(c2);            
            
            p.clear();
            p.add(t);
            
            pdf.add(p);           
                        
            Paragraph title1 = new Paragraph("Relatório de Dizimistas", FontFactory.getFont(FontFactory.HELVETICA, 14, Font.BOLDITALIC, new Color(0,0,255)));
            title1.setSpacingAfter(20);
            //Chapter chapter1 = new Chapter(title1,1);
            //chapter1.setNumberDepth(0);
            
            pdf.add(title1);
        }
        catch(IOException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, null, ex.getMessage()));
        }
        catch(BadElementException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, null, ex.getMessage()));
        }
        catch(DocumentException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, null, ex.getMessage()));
        }
    }
}
