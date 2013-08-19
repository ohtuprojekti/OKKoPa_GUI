package fi.helsinki.cs.web;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;
import org.apache.commons.io.IOUtils;

public class GetFrontServlet extends HttpServlet {

    private String cource;
    private ByteArrayOutputStream stream;
    private OutputStream outputStream;
    private BufferedImage img;
    private int width;
    private int height;
    private BufferedImage bufferedImage;
    private Graphics2D g2d;
    private Font font;
    private FontMetrics fm;
    private File file;
    private String url;

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        cource = request.getParameter("cource");
        
        System.out.println(cource);

        makeQRCodeImage(cource);
        makeGraphics2DForRender();
        drawTextToImage(cource);
        drawUrlToImage();
        closeImages();
        
        writeDownImage();

        addFileAsResponse(response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void makeQRCodeImage(String line) throws FileNotFoundException, IOException {
        stream = QRCode.from(line).to(ImageType.PNG).withSize(500, 500).stream();

        outputStream = new FileOutputStream("temp.png");
        stream.writeTo(outputStream);

        img = ImageIO.read(new File("temp.png"));
    }

    private void makeGraphics2DForRender() {
        width = img.getWidth();
        height = img.getHeight();
        bufferedImage = new BufferedImage(width * 3, height * 3, BufferedImage.TYPE_INT_RGB);
        g2d = bufferedImage.createGraphics();
        g2d.fillRect(0, 0, width * 3, height * 3);
        g2d.drawImage(img, 0, 0, Color.WHITE, null);
        g2d.drawImage(img, width * 2, height * 2, Color.WHITE, null);
        g2d.drawImage(img, 0, height * 2, Color.WHITE, null);
        g2d.drawImage(img, width * 2, 0, Color.WHITE, null);
    }

    private void makeFontSettings(int size, Color c) {
        font = new Font("Serif", Font.BOLD, size);
        g2d.setFont(font);
        g2d.setPaint(c);
        fm = g2d.getFontMetrics();
    }

    private void drawTextToImage(String line) {
        makeFontSettings(45, Color.BLACK);
        g2d.drawString(line, (width * 3) / 2 - (fm.stringWidth(line) / 2), (height * 3)/2 + 50);
    }
    
    private void drawUrlToImage() {
        url = "http://cs.helsinki.fi/okkopa";
        makeFontSettings(24, Color.BLACK);
        g2d.drawString(url, (width * 3)/2 - (fm.stringWidth(url) / 2), (height * 3)/2 - 50);
    }

    private void closeImages() {
        g2d.dispose();
    }

    private void addFileAsResponse(HttpServletResponse response) throws IOException, FileNotFoundException {
        InputStream is = new FileInputStream("temp.png");
        response.setContentType("image/png");
        response.setHeader("Content-Disposition", "attachment; filename=frontreference.png");
        IOUtils.copy(is, response.getOutputStream());
        response.flushBuffer();
    }
    
    private void writeDownImage() throws IOException {
        file = new File("temp.png");
        ImageIO.write(bufferedImage, "png", file);
    }
}
