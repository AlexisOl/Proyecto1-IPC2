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

import dao.UsuarioDao;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Usuario;

public class UsuarioController extends HttpServlet {

    String list = "administracion/usuario/list.jsp";
    String add = "administracion/usuario/add.jsp";
    String edit = "administracion/usuario/edit.jsp";
    String view = "administracion/usuario/view.jsp";

    String fabrica = "fabrica/pieza/list.jsp";
    String venta = "puntoventa/venta/list.jsp";
    String login = "index.jsp";

    Usuario object;
    UsuarioDao dao = new UsuarioDao();
    ;
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
            out.println("<title>Servlet UsuarioController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UsuarioController at " + request.getContextPath() + "</h1>");
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
                        break;
                    case "edit":
                        acceso = edit;
                        break;
                    case "view":
                        acceso = view;
                        break;
                    case "delete":
                        if (dao.delete(Integer.parseInt(request.getParameter("id")))) {
                            acceso = list + "?success=eliminado";
                        } else {
                            acceso = list + "?error=true";
                        }
                        break;
                    case "cancel":
                        if (dao.cancel(Integer.parseInt(request.getParameter("id")))) {
                            acceso = list + "?success=cancelado";
                        } else {
                            acceso = list + "?error=true";
                        }
                        break;
                    case "logout":
                        session.removeAttribute("user");
                        acceso = login;
                        break;
                    case "login":
                        acceso = login;
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
                    case "Registrar":
                        object = new Usuario();
                        object.setUsername(request.getParameter("username"));
                        object.setPassword(request.getParameter("password"));
                        object.setTipo(Integer.parseInt(request.getParameter("tipo")));
                        object.setEstado("Activo");

                        if (dao.add(object)) {
                            acceso = list + "?success=agregado";
                        } else {
                            acceso = add + "?error=true";
                        }
                        break;
                    case "Actualizar":
                        object = new Usuario();
                        object.setId(Integer.parseInt(request.getParameter("id")));
                        object.setUsername(request.getParameter("username"));
                        object.setPassword(request.getParameter("password"));
                        object.setTipo(Integer.parseInt(request.getParameter("tipo")));
                        object.setEstado(request.getParameter("estado"));

                        if (dao.edit(object)) {
                            acceso = list + "?success=actualizado";
                        } else {
                            acceso = edit + "?error=true";
                        }
                        break;
                    case "Login":
                        String username = request.getParameter("username");
                        String password = request.getParameter("password");
                        Usuario user = dao.login(username, password);
                        if (user != null) {
                            session.setAttribute("user", user.getUsername());
                            switch (user.getTipo()) {
                                case 1:
                                    acceso = fabrica;
                                    break;
                                case 2:
                                    acceso = venta;
                                    break;
                                case 3:
                                    acceso = list;
                                    break;
                                default:
                                    acceso = login + "?error=Rol no identificado";
                                    break;
                            }
                        } else {
                            session.removeAttribute("user");
                            acceso = login + "?error=Credenciales incorrectas";
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
            if (dao.list().isEmpty()) {
                object = new Usuario(0, "admin", "admin", 3, "Activo");
                dao.add(object);
                acceso = login + "?error=No hay usuarios registrados. "
                        + "Se registró un usuario<br>Username: admin<br>Password: admin";
            } else {
                try {
                    String action = request.getParameter("accion");
                    switch (action) {
                        case "Login":
                            String username = request.getParameter("username");
                            String password = request.getParameter("password");
                            Usuario user = dao.login(username, password);
                            if (user != null) {
                                session.setAttribute("user", user.getUsername());
                                switch (user.getTipo()) {
                                    case 1:
                                        acceso = fabrica;
                                        break;
                                    case 2:
                                        acceso = venta;
                                        break;
                                    case 3:
                                        acceso = list;
                                        break;
                                    default:
                                        acceso = login + "?error=Rol no identificado";
                                        break;
                                }
                            } else {
                                session.removeAttribute("user");
                                acceso = login + "?error=Credenciales incorrectas";
                            }
                            break;
                        default:
                            acceso = login;
                            break;
                    }
                } catch (Exception e) {
                    acceso = login;
                }
            }
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