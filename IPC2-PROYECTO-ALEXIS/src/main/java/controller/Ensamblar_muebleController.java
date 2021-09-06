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

import dao.Ensambla_piezaDao;
import dao.Ensamblar_muebleDao;
import dao.MuebleDao;
import dao.UsuarioDao;
import helper.Message;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Ensamblar_mueble;

public class Ensamblar_muebleController extends HttpServlet {

    String list = "fabrica/ensamblar_mueble/list.jsp";
    String add = "fabrica/ensamblar_mueble/add.jsp";
    String view = "fabrica/ensamblar_mueble/view.jsp";
    String login = "index.jsp";

    String mueble_disponible = "fabrica/ensamblar_mueble/muebles_disponibles.jsp";
    String reintegrar = "fabrica/ensamblar_mueble/reintegrar_mueble.jsp";

    Ensamblar_mueble object;
    Ensamblar_muebleDao dao = new Ensamblar_muebleDao();
    MuebleDao muebleDao = new MuebleDao();
    UsuarioDao usuarioDao = new UsuarioDao();
    Ensambla_piezaDao ensambla_piezaDao = new Ensambla_piezaDao();
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
            out.println("<title>Servlet Ensamblar_mueble</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Ensamblar_mueble at " + request.getContextPath() + "</h1>");
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
                        acceso = add;
                        session.setAttribute("mensaje", "");
                        break;
                    case "view":
                        acceso = view;
                        break;
                    case "setventa":
                        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddHHmmssSSS");
                        object = dao.search(Integer.parseInt(request.getParameter("id")));
                        object.setIdentificador(sdf1.format(new Date()));
                        object.setEstado("En venta");
                        if (dao.edit(object)) {
                            acceso = list + "?success=en venta";
                        } else {
                            acceso = list + "?error=true";
                        }
                        break;
                    case "listdisponible":
                    case "Filtrar":
                        acceso = mueble_disponible;
                        break;
                    case "listdevueltos":
                        acceso = reintegrar;
                        break;
                    case "reintegrar":
                        object = dao.search(Integer.parseInt(request.getParameter("id")));
                        object.setEstado("En venta");
                        if (dao.edit(object)) {
                            acceso = reintegrar + "?success=reintegrado";
                        } else {
                            acceso = reintegrar + "?error=true";
                        }
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
        String acceso = "";
        session = request.getSession();

        if (isLogged()) {
            try {
                String action = request.getParameter("accion");
                switch (action) {
                    case "Ensamblar":
                        acceso = add;
                        object = new Ensamblar_mueble();
                        object.setMueble(muebleDao.search(Integer.parseInt(request.getParameter("mueble_id"))));
                        object.setUsuario(usuarioDao.search(Integer.parseInt(request.getParameter("usuario_id"))));
                        object.setFecha(sdf.parse(request.getParameter("fecha")));

                        if (ensambla_piezaDao.stockSuficiente(object.getMueble().getId())) {
                            if (dao.add(object)) {
                                ensambla_piezaDao.actualizarStock(object.getMueble().getId());
                                acceso = list + "?success=ensamblado";
                            } else {
                                session.setAttribute("mensaje", "_" + Message.VALUE);
                            }
                        } else {
                            session.setAttribute("mensaje", "_Stock de piezas insuficiente");
                        }
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
