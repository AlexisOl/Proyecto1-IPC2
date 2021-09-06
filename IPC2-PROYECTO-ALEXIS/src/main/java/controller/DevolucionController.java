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

import dao.Detalle_facturaDao;
import dao.DevolucionDao;
import dao.Ensamblar_muebleDao;
import helper.Message;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Detalle_factura;
import model.Devolucion;
import model.Ensamblar_mueble;

public class DevolucionController extends HttpServlet {

    String list = "puntoventa/devolucion/list.jsp";
    String add = "puntoventa/devolucion/add.jsp";
    String view = "puntoventa/devolucion/view.jsp";
    String login = "index.jsp";

    String listbycliente = "puntoventa/devolucion/list_bycliente.jsp";

    Devolucion object;
    DevolucionDao dao = new DevolucionDao();
    Detalle_facturaDao detalle_facturaDao = new Detalle_facturaDao();
    Ensamblar_muebleDao ensamblar_muebleDao = new Ensamblar_muebleDao();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    HttpSession session;

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
            out.println("<title>Servlet DevolucionController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet DevolucionController at " + request.getContextPath() + "</h1>");
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
        session = request.getSession();

        if (isLogged()) {
            try {
                String action = request.getParameter("accion");
                switch (action) {
                    case "list":
                        acceso = list;
                        break;
                    case "add":
                        session.setAttribute("mensaje", "");
                        acceso = add;
                        break;
                    case "view":
                        acceso = view;
                        break;
                    case "listbycliente":
                    case "Buscar devolucion":
                        acceso = listbycliente;
                        break;
                    default:
                        acceso = list;
                        break;
                }
            } catch (Exception e) {
                acceso = list;
            }
        } else {
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
        session = request.getSession();

        if (isLogged()) {
            try {
                String action = request.getParameter("accion");
                switch (action) {
                    case "Devolver":
                        acceso = add;
                        String fecha = request.getParameter("fecha_devolucion");
                        String codigo_factura = request.getParameter("codigo");
                        String identificador_mueble = request.getParameter("identificador");
                        Detalle_factura detalle_factura = detalle_facturaDao.search(codigo_factura, identificador_mueble);

                        if (detalle_factura != null) {
                            if (detalle_facturaDao.devolucionValid(detalle_factura.getId(), fecha)) {
                                object = new Devolucion();
                                object.setFecha_devolucion(sdf.parse(fecha));
                                object.setDetalle_factura(detalle_factura);
                                object.setPerdida(object.getDetalle_factura().getEnsamblar_mueble().getMueble().getPrecio() / 3);

                                if (dao.add(object)) {
                                    Ensamblar_mueble mueble = detalle_factura.getEnsamblar_mueble();
                                    mueble.setEstado("Devuelto");
                                    ensamblar_muebleDao.edit(mueble);
                                    acceso = list + "?success=registrado";
                                } else {
                                    session.setAttribute("mensaje", "_" + Message.VALUE);
                                }
                            } else {
                                session.setAttribute("mensaje", "_Ya pasaron más de 7 días, "
                                        + "no se puede devolver");
                            }

                        } else {
                            session.setAttribute("mensaje", "_El código de factura "
                                    + "o identificador de mueble no es válido");
                        }
                        break;
                    default:
                        acceso = list;
                        break;
                }
            } catch (Exception ex) {
                acceso = list;
            }
        } else {
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
