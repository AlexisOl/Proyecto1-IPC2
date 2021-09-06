/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

/**
 *
 * @author alexis
 */
import dao.DatosDao;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

@MultipartConfig
public class DatosController extends HttpServlet {

    String login = "index.jsp";
    String data = "administracion/datos/data.jsp";

    HttpSession session;
    DatosDao dao = new DatosDao();
    Part part;
    InputStream inputStream;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet DatosController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet DatosController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String acceso;
        try {
            session = request.getSession();

            if (isLogged()) {
                acceso = data;
            } else {
                acceso = login;
            }
        } catch (Exception e) {
            acceso = login;
        }

        RequestDispatcher vista = request.getRequestDispatcher(acceso);
        vista.forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String acceso;
        try {
            session = request.getSession();

            if (isLogged()) {
                String action = request.getParameter("accion");
                switch (action) {
                    case "Cargar":
                        String objeto = request.getParameter("objeto");
                        part = request.getPart("archivo");
                        if (part.getSubmittedFileName().endsWith(".txt")) {
                            dao.cargarDatos(part.getInputStream(), objeto);
                            acceso = data + "?success=cargados";
                        } else {
                            acceso = data + "?error=true";
                        }
                        break;
                    default:
                        acceso = data;
                        break;
                }
            } else {
                acceso = login;
            }
        } catch (Exception e) {
            acceso = login;
        }
        RequestDispatcher vista = request.getRequestDispatcher(acceso);
        vista.forward(request, response);
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

    public boolean isLogged() {
        List<String> atributos = Collections.list(session.getAttributeNames());
        if (atributos.contains("user")) {
            return session.getAttribute("user") != null;
        }
        return false;
    }
}