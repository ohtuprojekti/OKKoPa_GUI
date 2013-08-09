/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.helsinki.cs.web;

import fi.helsinki.cs.okkopa.reference.Reference;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author hannahir
 */
public class GetReferenceServlet extends HttpServlet {

    private String amount;
    private String size;
    private String letters;
    private Reference reference;

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
        try {
            // get your file as InputStream
            PrintWriter writer = new PrintWriter("references.txt", "UTF-8");
            
            getAmountSizeLettersByForm(request);
            
            reference = new Reference(Integer.valueOf(size));
            
            for (int i = 0; i < Integer.valueOf(amount); i++) {
                if(letters.equals("yes")) {
                    writer.println(reference.getReference());
                } else {
                    writer.println(reference.getReferenceNumber());
                }
            }

            writer.close();
            
            
            addFileAsResponse(response);
        } catch (IOException ex) {
            throw new RuntimeException("IOError writing file to output stream");
        }
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

    private void getAmountSizeLettersByForm(HttpServletRequest request) {
        amount = request.getParameter("amount");
        size = request.getParameter("size");
        letters = request.getParameter("letters");
    }

    private void addFileAsResponse(HttpServletResponse response) throws IOException, FileNotFoundException {
        InputStream is = new FileInputStream("references.txt");
        response.setContentType("text/plain");
        response.setHeader("Content-Disposition", "attachment; filename=references.txt");
        // copy it to response's OutputStream
        IOUtils.copy(is, response.getOutputStream());
        response.flushBuffer();
    }
}