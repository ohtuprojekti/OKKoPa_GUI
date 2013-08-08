package fi.helsinki.cs.web;

import fi.helsinki.cs.okkopa.reference.Reference;
import fi.helsinki.cs.okkopa.reference.Warning;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.validator.EmailValidator;

public class AddReferenceServlet extends HttpServlet {

    private References references = new References();
    private Reference reference;
    private String id;
    private String code;
    private String email;
    private EmailValidator emailValidator;
    private boolean noErrorsSoFar;
    private boolean typos;

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
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        noErrorsSoFar = true;
        
        getIDCodeEmailByForm(request);
        
        Warning.setWarning("");
        
        emailValidator = EmailValidator.getInstance();
        if(emailValidator.isValid(email) == false) {
            Warning.setWarning("Sähköpostisi oli väärin kirjoitettu, tarkista virheet.");
            noErrorsSoFar = false;
        }
        
        typos = checkIfTypo();
        if(noErrorsSoFar && typos  == false) {
            //do the stuff for DB.
            Warning.addWarning("Tietosi on nyt lisätty kantaan, ja konseptisi tulee sinulle.");
        }

        Ref newRef = new Ref(id, code, email);
        references.addReference(newRef);

        request.getRequestDispatcher("/List").forward(request, response);
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

    private void getIDCodeEmailByForm(HttpServletRequest request) {
        id = request.getParameter("id");
        code = request.getParameter("code");
        email = request.getParameter("email");
    }

    private boolean checkIfTypo() throws NumberFormatException {
        reference = new Reference();
        
        if (!code.equals("")) {
            
            boolean joo = true;
            try {
                int luku = Integer.valueOf(code);
            } catch(NumberFormatException e) {
                joo = false;
            }
            
            if (reference.checkReference(code) || (joo && reference.checkReferenceNumber(Integer.valueOf(code)))) {
                return false;
            } else {
                setReferenceTypoWarning();
                return true;
            }
        }
        setReferenceTypoWarning();  
        return true;
    }

    private void setReferenceTypoWarning() {
        String warning = "Kirjoitit viitteesi väärin, tarkista oikeinkirjoitus. ";
        Warning.addWarning(warning);
    }
}
