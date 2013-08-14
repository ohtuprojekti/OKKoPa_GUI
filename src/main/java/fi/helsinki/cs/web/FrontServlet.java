/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.helsinki.cs.web;

import fi.helsinki.cs.okkopa.database.OracleConnector;
import fi.helsinki.cs.okkopa.database.Settings;
import fi.helsinki.cs.okkopa.model.CourseDbModel;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.xpath.operations.Equals;

/**
 *
 * @author tatutall
 */
public class FrontServlet extends HttpServlet {

    private ArrayList<String> courceCodes;
    private ArrayList<String> courceName;
    private ArrayList<String> courceNumber;
    private ArrayList<String> courcePeriod;
    private ArrayList<String> courceType;
    private ArrayList<String> courceYear;
    private ArrayList<String> lista;
    private ArrayList<String> lista2;
    private String name;
    private String period;
    private String type;
    private String number;

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
            throws ServletException, IOException, SQLException {
        request.setAttribute("message", "OKKoPa viitteiden luonti");

        OracleConnector oc = new OracleConnector(new Settings("settings.xml"));
        oc.connect();

        courceCodes = new ArrayList<>();
        courceName = new ArrayList<>();
        courceNumber = new ArrayList<>();
        courcePeriod = new ArrayList<>();
        courceType = new ArrayList<>();
        courceYear = new ArrayList<>();

        lista = new ArrayList<>();
        lista2 = new ArrayList<>();

        for (CourseDbModel cource : oc.getCourseList()) {
            courceCodes.add(cource.getCourseCode());
            courceName.add(cource.getName());
            courceNumber.add("" + cource.getCourseNumber());
            courcePeriod.add(cource.getPeriod());
            courceType.add(cource.getType());
            courceYear.add("" + cource.getYear());
        }

        for (int i = 0; i < courceCodes.size(); i++) {
            parseName(i);
            parsePeriod(i);
            parseType(i);
            parseNumber(i);

            lista.add(courceCodes.get(i) + ":" + courceYear.get(i) + ":" + courcePeriod.get(i) + ":" + courceType.get(i) + ":" + courceNumber.get(i));
            lista2.add(name + " (" + courceCodes.get(i) + "), " + courceYear.get(i) + ", " + period + ", " + type + ", " + number);
        }

        Collections.sort(lista2);

        request.setAttribute("courceCodes", lista2);

//        request.setAttribute("warning", Warning.getWarning());  

        RequestDispatcher dispatcher = request.getRequestDispatcher("front.jsp");
        dispatcher.forward(request, response);
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
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(FrontServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(FrontServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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

    private void parseName(int i) {
        name = courceName.get(i);
        if (name.length() >= 70) {
            name = name.substring(0, 67) + "...";
        }
    }

    private void parsePeriod(int i) {
        period = courcePeriod.get(i);
        if (period.equals("K")) {
            period = "kevät";
        } else if (period.equals("S")) {
            period = "syksy";
        } else if (period.equals("V")) {
            period = "kesä";
        }
    }

    private void parseType(int i) {
        type = courceType.get(i);
        if (type.equals("L")) {
            type = "Luento";
        } else if (type.equals("K")) {
            type = "Koe";
        } else if (type.equals("A")) {
            type = "harjoitustyö";
        } else if (type.equals("S")) {
            type = "Seminaari";
        }
    }

    private void parseNumber(int i) {
        number = courceNumber.get(i);
        if (number.equals("1")) {
            number = "Kurssi";
        } else if (number.equals("2")) {
            number = "Erilliskoe";
        }
    }
}
