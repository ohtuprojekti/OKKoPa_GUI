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
import java.util.List;
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

    private ArrayList<String> list;
    private String courcePeriod;
    private String courceType;
    private String courceNumber;
    private String value;
    private ArrayList<String> values;
    private String courceCode;
    private String courceName;
    private String courceYear;
    private String value2;

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

        list = new ArrayList<String>();
        values = new ArrayList<String>();
        
        List<CourseDbModel> cources = oc.getCourseList();

        for (int i = 0; i < cources.size(); i++) {
            
            courceCode = cources.get(i).getCourseCode();
            courceName = cources.get(i).getName();
            courceNumber = "" + cources.get(i).getCourseNumber();
            courcePeriod = cources.get(i).getPeriod();
            courceType = cources.get(i).getType();
            courceYear = "" + cources.get(i).getYear();

            value = courceCode + ":" + courcePeriod + ":" + courceYear + ":" + courceType + ":" + courceNumber;
            
            parseName(i);
            parsePeriod(i);
            parseType(i);
            parseNumber(i);
            
            value2 = courceName + " (" + courceCode + "), " + courceYear + ", " + courcePeriod + ", " + courceType + ", " + courceNumber;
            
            list.add("<option value=\"" + value+ "\">" + value2 + "</option>");
        }

        Collections.sort(list);

        request.setAttribute("courceCodes", list);

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
        if (courceName.length() >= 70) {
            courceName = courceName.substring(0, 67) + "...";
        }
    }

    private void parsePeriod(int i) {
        if (courcePeriod.equals("K")) {
            courcePeriod = "kevät";
        } else if (courcePeriod.equals("S")) {
            courcePeriod = "syksy";
        } else if (courcePeriod.equals("V")) {
            courcePeriod = "kesä";
        }
    }

    private void parseType(int i) {
        if (courceType.equals("L")) {
            courceType = "Luento";
        } else if (courceType.equals("K")) {
            courceType = "Koe";
        } else if (courceType.equals("A")) {
            courceType = "harjoitustyö";
        } else if (courceType.equals("S")) {
            courceType = "Seminaari";
        }
    }

    private void parseNumber(int i) {
        if (courceNumber.equals("1")) {
            courceNumber = "Kurssi";
        } else if (courceNumber.equals("2")) {
            courceNumber = "Erilliskoe";
        }
    }
}
