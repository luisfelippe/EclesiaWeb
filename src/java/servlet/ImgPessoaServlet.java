package servlet;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import util.Util;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns={"/sistema/img-pessoa"})
public class ImgPessoaServlet
  extends HttpServlet
{
  private String imagePath;
  
  public void init()
    throws ServletException
  {}
  
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    String cam = request.getParameter("cam");
    
    cam = cam.replace("\\", File.separator);
    cam = cam.replace("/", File.separator);
    
    if ((cam == null) || (!cam.contains("membros")))
    {
        File img = new File(File.separator + "opt" + File.separator + "ArquivosEclesiaWeb" + File.separator + "bd-fotos" + File.separator + "sem-foto.jpg");
        
        response.reset();
        response.setContentType("image");
        response.setHeader("Content-Length", String.valueOf(img.length()));

        Files.copy(img.toPath(), response.getOutputStream());
        return;
    }
    
    File image = new File(Util.IMAGEM_PESSOA_PATH + cam);
    
    if (!image.exists())
    {
      File img = new File(File.separator + "opt" + File.separator + "ArquivosEclesiaWeb" + File.separator + "bd-fotos" + File.separator + "sem-foto.jpg");
      response.reset();
      response.setContentType("image");
      response.setHeader("Content-Length", String.valueOf(img.length()));
      
      Files.copy(img.toPath(), response.getOutputStream());
      return;
    }
    String contentType = getServletContext().getMimeType(image.getName());
    if ((contentType == null) || (!contentType.startsWith("image")))
    {
      response.sendError(404);
      return;
    }    
    
    //Files.copy(image.toPath(), response.getOutputStream());
    redimensionaImg(Util.IMAGEM_PESSOA_PATH + cam, 300, 300, response, contentType);
    //System.out.println(">>>>SAI2");
  }
  
    public void redimensionaImg(String caminho, double requiredWidth, double requiredHeight, HttpServletResponse out, String contentType) throws IOException 
    {
        //BufferedImage imagem = ImageIO.read(RedimensionarImagem.class.getResourceAsStream("background.jpg"));  
        BufferedImage imagem = null;
        
        try 
        {
            imagem = ImageIO.read(new File(caminho));
            
            double originalWidth = imagem.getWidth();
            double originalHeight = imagem.getHeight();
            double newWidth = 0;
            double newHeight = 0;
            double diff = 0;

            if (requiredHeight == 0) {
                requiredHeight = requiredWidth;
            }

            if (requiredWidth == 0) {
                requiredWidth = requiredHeight;
            }

            if(originalWidth < requiredWidth && originalHeight < requiredHeight){
                return ;
            }

            if(requiredWidth == 0 && requiredHeight == 0){
                return ;
            }

            if (originalWidth > originalHeight) 
            {
                diff = originalWidth - originalHeight;
                newWidth = requiredWidth;            
                diff = diff / originalWidth;
                newHeight = newWidth - (newWidth * diff);
            } 
            else if (originalWidth < originalHeight) 
            {
                diff = originalHeight - originalWidth;
                newHeight = requiredHeight;            
                diff = diff / originalHeight;
                newWidth = newHeight - (newHeight * diff);
            } 
            else 
            {
                if (requiredHeight > requiredWidth) 
                {
                    requiredHeight = requiredWidth;
                } 
                else if (requiredHeight < requiredWidth) 
                {
                    requiredWidth = requiredHeight;
                }

                newHeight = requiredHeight;
                newWidth = requiredWidth;
            }

            int type = BufferedImage.SCALE_SMOOTH;
            boolean isPng = caminho.toUpperCase().endsWith("PNG");
            String ext = "JPG";
            
            if (isPng) {
                type = BufferedImage.BITMASK;
                ext = "PNG";
            }

            BufferedImage new_img = new BufferedImage((int) newWidth, (int) newHeight, type);
            Graphics2D g = new_img.createGraphics();
            g.setComposite(AlphaComposite.Src);
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC); //mais lento, todavia melhora a qualidade 
            g.drawImage(imagem, 0, 0, (int) newWidth, (int) newHeight, null);
            //ByteArrayOutputStream out = new ByteArrayOutputStream();
            
            ByteArrayOutputStream tmp = new ByteArrayOutputStream();
            ImageIO.write(new_img, ext, tmp);
            tmp.close();
            Integer contentLength = tmp.size();

            out.setHeader("Content-Length",contentLength.toString());
            out.setContentType(contentType);
            out.getOutputStream().write(tmp.toByteArray());
            out.getOutputStream().close();            
            
            //System.out.println(">>>>SAI");
        } 
        catch (Exception ex) 
        {
            ex.printStackTrace();
        }
    }
}
