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
import controlefinanceiro.cln.cdp.Saida;
import controlefinanceiro.cln.cgt.EntradaNegocio;
import controlefinanceiro.cln.cgt.SaidaNegocio;
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
public class RelBalanceteBean extends ManageBean{
    private Date dataIni;
    private Date dataFim;
    private List<Saida> listaSaida;
    private double totalSaida;
    
    private List<Entrada> listaEntrada;
    private double totalEntrada;
    
    public RelBalanceteBean() {
        
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

    public List<Saida> getListaSaida() {
        return listaSaida;
    }

    public void setListaSaida(List<Saida> listaSaida) {
        this.listaSaida = listaSaida;
    }
    
    public void buscar() {
        try
        {
            this.listaSaida = new SaidaNegocio().getLista(dataIni, dataFim);
            this.listaEntrada = new EntradaNegocio().getLista(dataIni, dataFim);
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

    public double getTotalSaida() {
        if(!this.listaSaida.isEmpty())
        {
            this.totalSaida = 0;
            
            for(Saida saida : this.listaSaida)
            {
                this.totalSaida += saida.getValor();
            }
        }
        
        return totalSaida;
    }

    public void setTotalSaida(double total) {
        this.totalSaida = total;
    }

    public List<Entrada> getListaEntrada() {
        return listaEntrada;
    }

    public void setListaEntrada(List<Entrada> listaEntrada) {
        this.listaEntrada = listaEntrada;
    }

    public double getTotalEntrada() {
        if(!this.listaEntrada.isEmpty())
        {
            this.totalEntrada = 0;
            
            for(Entrada entrada : this.listaEntrada)
            {
                this.totalEntrada += entrada.getValor();
            }
        }
        
        return totalEntrada;
    }

    public void setTotalEntrada(double totalEntrada) {
        this.totalEntrada = totalEntrada;
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
            
            Cell c2 = new Cell(new Phrase("\t\t\tGerenciamento Eclesiástico\n\t\t\tIgreja Manancial da Graça", new Font(Font.HELVETICA, 12, Font.BOLD, new Color(0,0,0))));
            c2.setHorizontalAlignment(Cell.ALIGN_LEFT);
            c2.setBorderWidth(0);
            
            t.addCell(c2);            
            
            p.clear();
            p.add(t);
            
            pdf.add(p);           
                        
            Paragraph title1 = new Paragraph("Balancete Financeiro", FontFactory.getFont(FontFactory.HELVETICA, 14, Font.BOLD, new Color(0,0,0)));
            title1.setSpacingAfter(20);
            title1.setAlignment(Paragraph.ALIGN_CENTER);
            
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
    
    public void postProcessPDF(Object document) {
        try
        {
            Document pdf = (Document) document; 
            Table t = new Table(2,1);
            t.setWidth(100);
            float[] widths = {20,80};
            t.setWidths(widths);
            t.setBorderWidth(0);
            t.setBorderColor(new Color(0,0,0));
            
            Cell c1 = new Cell();
            c1.add(new Phrase("Total Entrada:", new Font(Font.HELVETICA, 10, Font.BOLD, new Color(0,0,0))));
            c1.setBorderWidth(0);
            
            t.addCell(c1);
            
            Cell c2 = new Cell();
            c2.add(new Phrase("R$ " + Double.toString(this.getTotalEntrada()), new Font(Font.HELVETICA, 10, Font.NORMAL, new Color(0,0,0))));
            c2.setBorderWidth(0);
            
            t.addCell(c2);
            
            Cell c3 = new Cell(new Phrase("Total Saída", new Font(Font.HELVETICA, 10, Font.BOLD, new Color(0,0,0))));
            c3.setHorizontalAlignment(Cell.ALIGN_LEFT);
            c3.setBorderWidth(0);
            
            t.addCell(c3); 
            
            Cell c4 = new Cell();
            c4.add(new Phrase("R$ " + Double.toString(this.getTotalSaida()), new Font(Font.HELVETICA, 10, Font.NORMAL, new Color(0,0,0))));
            c4.setBorderWidth(0);
            
            t.addCell(c4);
            
            Cell c5 = new Cell(new Phrase("Total Geral:", new Font(Font.HELVETICA, 10, Font.BOLD, new Color(0,0,0))));
            c5.setHorizontalAlignment(Cell.ALIGN_LEFT);
            c5.setBorderWidth(0);
            
            t.addCell(c5); 
            
            Cell c6 = new Cell();
            c6.add(new Phrase("R$ " + Double.toString(this.getTotalEntrada() - this.getTotalSaida()), new Font(Font.HELVETICA, 10, Font.NORMAL, ((this.getTotalEntrada() - this.getTotalSaida()) > 0) ? new Color(0,0,0) : new Color(255,0,0) )));
            c6.setBorderWidth(0);
            
            t.addCell(c6);            
            
            pdf.add(t);
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
