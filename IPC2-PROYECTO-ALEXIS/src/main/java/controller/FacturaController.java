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

import dao.ClienteDao;
import dao.Detalle_facturaDao;
import dao.Ensamblar_muebleDao;
import dao.FacturaDao;
import dao.UsuarioDao;
import helper.Message;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Cliente;
import model.Detalle_factura;
import model.Ensamblar_mueble;
import model.Factura;

public class FacturaController extends HttpServlet {

    String list = "puntoventa/venta/list.jsp";
    String add = "puntoventa/venta/add.jsp";
    String edit = "puntoventa/venta/edit.jsp";
    String view = "puntoventa/venta/view.jsp";
    String login = "index.jsp";

    String listbycliente = "puntoventa/venta/list_bycliente.jsp";

    Factura object;
    FacturaDao dao = new FacturaDao();

    Cliente cliente;
    ClienteDao clienteDao = new ClienteDao();

    UsuarioDao usuarioDao = new UsuarioDao();
    Ensamblar_mueble ensamblar_mueble;
    Ensamblar_muebleDao ensamblar_muebleDao = new Ensamblar_muebleDao();

    Detalle_facturaDao detalle_facturaDao = new Detalle_facturaDao();

    ArrayList<Integer> detalle;

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
            out.println("<title>Servlet FacturaController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet FacturaController at " + request.getContextPath() + "</h1>");
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
                        cliente = new Cliente(0, "", "", "", "", "");
                        detalle = new ArrayList<>();
                        session.setAttribute("cliente", cliente);
                        session.setAttribute("detalle", detalle);
                        session.setAttribute("mensaje", "");
                        acceso = add;
                        break;
                    case "view":
                        acceso = view;
                        break;
                    case "listbycliente":
                    case "Buscar compras":
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
                    case "Buscar cliente":
                        String nit = request.getParameter("nit").trim();
                        cliente = clienteDao.searchByNit(nit);
                        if (cliente != null) {
                            session.setAttribute("cliente", cliente);
                            session.setAttribute("mensaje", "");
                        } else {
                            cliente = (Cliente) session.getAttribute("cliente");
                            cliente.setNit(nit);
                            session.setAttribute("cliente", cliente);
                            session.setAttribute("mensaje", "_El cliente no está registrado");
                        }
                        acceso = add;
                        break;
                    case "Agregar":
                        cliente = (Cliente) session.getAttribute("cliente");
                        cliente.setNit(request.getParameter("nit"));
                        cliente.setNombre(request.getParameter("nombre"));
                        cliente.setDireccion(request.getParameter("direccion"));
                        cliente.setMunicipio(request.getParameter("municipio"));
                        cliente.setDepartamento(request.getParameter("departamento"));
                        session.setAttribute("cliente", cliente);

                        detalle = (ArrayList<Integer>) session.getAttribute("detalle");
                        if (request.getParameter("ensamblar_mueble_id") != null) {
                            int ensamblar_mueble_id = Integer.parseInt(request.getParameter("ensamblar_mueble_id"));
                            if (!detalle.contains(ensamblar_mueble_id)) {
                                session.setAttribute("mensaje", "Mueble agregado a la factura_");
                                detalle.add(ensamblar_mueble_id);
                                session.setAttribute("detalle", detalle);
                            } else {
                                session.setAttribute("mensaje", "_El mueble ya está en la factura");
                            }
                        } else {
                            session.setAttribute("mensaje", "_No hay muebles disponibles para venta");
                        }
                        acceso = add;
                        break;
                    case "Comprar":
                        acceso = add;
                        detalle = (ArrayList<Integer>) session.getAttribute("detalle");
                        if (detalle.isEmpty()) {
                            session.setAttribute("mensaje", "_Agregue muebles al detalle de factura");
                        } else {
                            if (request.getParameter("usuario_id").isEmpty()) {
                                session.setAttribute("mensaje", "_Selecciona un vendedor");
                            } else {
                                cliente = (Cliente) session.getAttribute("cliente");
                                if (cliente.isValid()) {
                                    if (clienteDao.searchByNit(cliente.getNit()) == null) {
                                        if (!clienteDao.add(cliente)) {
                                            session.setAttribute("mensaje", "_" + Message.VALUE);
                                            acceso = add;
                                            break;
                                        }
                                    }
                                    SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddHHmmssSSS");
                                    object = new Factura();
                                    object.setCodigo(sdf1.format(new Date()));
                                    object.setCliente(clienteDao.searchByNit(cliente.getNit()));
                                    object.setUsuario(usuarioDao.search(Integer.parseInt(request.getParameter("usuario_id"))));
                                    object.setFecha(new Date());
                                    double total = 0;

                                    for (Integer id_mueble : detalle) {
                                        total += ensamblar_muebleDao.search(id_mueble).getMueble().getPrecio();
                                    }
                                    object.setCosto(total);
                                    if (dao.add(object)) {
                                        object = dao.searchByCodigo(object.getCodigo());
                                        for (Integer id_mueble : detalle) {
                                            ensamblar_mueble = ensamblar_muebleDao.search(id_mueble);
                                            ensamblar_mueble.setEstado("Vendido");
                                            ensamblar_muebleDao.edit(ensamblar_mueble);

                                            detalle_facturaDao.add(
                                                    new Detalle_factura(
                                                            0,
                                                            object,
                                                            ensamblar_mueble
                                                    )
                                            );
                                        }
                                        acceso = list + "?success=registrado";
                                    } else {
                                        session.setAttribute("mensaje", "_" + Message.VALUE);
                                    }
                                } else {
                                    session.setAttribute("mensaje", "_Ingresar los datos del Cliente");
                                }
                            }
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
