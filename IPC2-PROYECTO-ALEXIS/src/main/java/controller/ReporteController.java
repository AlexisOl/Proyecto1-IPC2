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
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.Detalle_facturaDao;
import dao.DevolucionDao;
import dao.MuebleDao;
import dao.UsuarioDao;
import java.text.SimpleDateFormat;
import model.Detalle_factura;
import model.Devolucion;
import model.Mueble;
import model.Usuario;

public class ReporteController extends HttpServlet {

    String reporte_venta = "administracion/reporte/reporte_venta.jsp";
    String reporte_devolucion = "administracion/reporte/reporte_devolucion.jsp";
    String reporte_ganancia = "administracion/reporte/reporte_ganancia.jsp";
    String reporte_max_venta_usuario = "administracion/reporte/reporte_max_venta_usuario.jsp";
    String reporte_max_ganancia_usuario = "administracion/reporte/reporte_max_ganancia_usuario.jsp";
    String reporte_max_venta_mueble = "administracion/reporte/reporte_max_venta_mueble.jsp";
    String reporte_min_venta_mueble = "administracion/reporte/reporte_min_venta_mueble.jsp";

    //String export_venta = "administracion/reporte/export_venta.jsp";
    String export_venta = "administracion/reporte/export_venta.jsp";

    String login = "index.jsp";

    HttpSession session;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

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
            out.println("<title>Servlet ReporteController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ReporteController at " + request.getContextPath() + "</h1>");
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            String acceso;
            session = request.getSession();

            if (isLogged()) {
                try {
                    String action = request.getParameter("accion");
                    switch (action) {
                        case "reporte_venta":
                        case "Filtrar ventas":
                            acceso = reporte_venta;
                            break;
                        case "reporte_devolucion":
                        case "Filtrar devoluciones":
                            acceso = reporte_devolucion;
                            break;
                        case "reporte_ganancia":
                        case "Filtrar ganancia":
                            acceso = reporte_ganancia;
                            break;
                        case "reporte_max_venta_usuario":
                        case "Filtrar vendedor":
                            acceso = reporte_max_venta_usuario;
                            break;
                        case "reporte_max_ganancia_usuario":
                        case "Filtrar vendedor_":
                            acceso = reporte_max_ganancia_usuario;
                            break;
                        case "reporte_max_venta_mueble":
                        case "Filtrar mueble":
                            acceso = reporte_max_venta_mueble;
                            break;
                        case "reporte_min_venta_mueble":
                        case "Filtrar mueble_":
                            acceso = reporte_min_venta_mueble;
                            break;
                        case "export_venta":
                            export(request, response, action);
                            acceso = reporte_venta;
                            break;
                        case "export_devolucion":
                            export(request, response, action);
                            acceso = reporte_devolucion;
                            break;
                        case "export_ganancia":
                            export(request, response, action);
                            acceso = reporte_ganancia;
                            break;
                        case "export_max_venta_usuario":
                            export(request, response, action);
                            acceso = reporte_max_venta_usuario;
                            break;
                        case "export_max_ganancia_usuario":
                            export(request, response, action);
                            acceso = reporte_max_ganancia_usuario;
                            break;
                        case "export_max_venta_mueble":
                            export(request, response, action);
                            acceso = reporte_max_venta_mueble;
                            break;
                        case "export_min_venta_mueble":
                            export(request, response, action);
                            acceso = reporte_min_venta_mueble;
                            break;
                        default:
                            acceso = reporte_venta;
                            break;
                    }
                } catch (Exception e) {
                    acceso = reporte_venta;
                }
            } else {
                acceso = login;
            }
            RequestDispatcher vista = request.getRequestDispatcher(acceso);
            vista.forward(request, response);
        } catch (Exception e) {
        }
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

    public void export(HttpServletRequest request, HttpServletResponse response, String accion) {
        try {
            String inicio = request.getParameter("inicio");
            String fin = request.getParameter("fin");
            if (!inicio.isEmpty() && !fin.isEmpty()) {
                String nameFile = accion + "_" + inicio + "_to_" + fin + ".csv";
                response.setContentType("text/csv");
                response.setHeader("Content-Disposition", "attachment; filename=" + nameFile);
                if (!request.isSecure()) {
                    response.setHeader("Cache-Control", "no-cache");
                    response.setHeader("Pragma", "no-cache");
                    response.setHeader("Expires", "0");
                }

                PrintWriter writer;
                writer = response.getWriter();

                Detalle_facturaDao detalle_facturaDao = new Detalle_facturaDao();
                DevolucionDao devolucionDao = new DevolucionDao();
                UsuarioDao usuarioDao = new UsuarioDao();
                MuebleDao muebleDao = new MuebleDao();

                ArrayList<Detalle_factura> detalle_factura_data;
                ArrayList<Devolucion> devolucion_data;
                switch (accion) {
                    case "export_venta":
                        writer.append("Factura,Fecha de venta,Código producto,Nombre,Precio\n");
                        detalle_factura_data = detalle_facturaDao.listByFecha(inicio, fin);
                        for (Detalle_factura item : detalle_factura_data) {
                            writer.append(item.getFactura().getCodigo() + ","
                                    + sdf.format(item.getFactura().getFecha()) + ","
                                    + item.getEnsamblar_mueble().getIdentificador() + ","
                                    + item.getEnsamblar_mueble().getMueble().getNombre() + ","
                                    + item.getEnsamblar_mueble().getMueble().getPrecio()
                                    + "\n");
                        }
                        break;
                    case "export_devolucion":
                        writer.append("Factura,Fecha de devolución,Código producto,Nombre,Precio\n");
                        devolucion_data = devolucionDao.listByFecha(inicio, fin);
                        for (Devolucion item : devolucion_data) {
                            writer.append(item.getDetalle_factura().getFactura().getCodigo() + ","
                                    + sdf.format(item.getFecha_devolucion()) + ","
                                    + item.getDetalle_factura().getEnsamblar_mueble().getIdentificador() + ","
                                    + item.getDetalle_factura().getEnsamblar_mueble().getMueble().getNombre() + ","
                                    + item.getPerdida() + "\n");
                        }
                        break;
                    case "export_ganancia":
                        writer.append("Ganancia\n");
                        writer.append(detalle_facturaDao.getGananciaByFecha(inicio, fin) + "\n");
                        writer.append("Código producto,Nombre,Precio,Fecha de venta\n");
                        detalle_factura_data = detalle_facturaDao.listByFechaForGanancia(inicio, fin);
                        for (Detalle_factura item : detalle_factura_data) {
                            writer.append(item.getEnsamblar_mueble().getIdentificador() + ","
                                    + item.getEnsamblar_mueble().getMueble().getNombre() + ","
                                    + item.getEnsamblar_mueble().getMueble().getPrecio() + ","
                                    + sdf.format(item.getFactura().getFecha()) + "\n");
                        }
                        break;
                    case "export_max_venta_usuario":
                        Usuario usuario = usuarioDao.getUsuarioMaxVenta(inicio, fin);
                        if (usuario != null) {
                            detalle_factura_data = detalle_facturaDao.listByFechaUsuario(usuario.getId(), inicio, fin);
                            int count = 0;
                            String codigo = "";

                            writer.append("Usuario\n");
                            writer.append(usuario.getUsername() + "\n");
                            writer.append("Factura,Fecha de venta,Código producto,Nombre,Precio\n");
                            for (Detalle_factura item : detalle_factura_data) {
                                if (!item.getFactura().getCodigo().equals(codigo)) {
                                    count++;
                                    codigo = item.getFactura().getCodigo();
                                }
                                writer.append(item.getFactura().getCodigo() + ","
                                        + sdf.format(item.getFactura().getFecha()) + ","
                                        + item.getEnsamblar_mueble().getIdentificador() + ","
                                        + item.getEnsamblar_mueble().getMueble().getNombre() + ","
                                        + item.getEnsamblar_mueble().getMueble().getPrecio() + "\n");
                            }
                            writer.append("Cantidad de ventas\n");
                            writer.append(count + "\n");
                        }
                        break;
                    case "export_max_ganancia_usuario":
                        Usuario usuario1 = usuarioDao.getUsuarioMaxGanancia(inicio, fin);
                        if (usuario1 != null) {
                            detalle_factura_data = detalle_facturaDao.listByFechaUsuario(usuario1.getId(), inicio, fin);
                            double total = 0;
                            String codigo = "";

                            writer.append("Usuario\n");
                            writer.append(usuario1.getUsername() + "\n");
                            writer.append("Factura,Fecha de venta,Código producto,Nombre,Precio\n");
                            for (Detalle_factura item : detalle_factura_data) {
                                if (!item.getFactura().getCodigo().equals(codigo)) {
                                    total += item.getFactura().getCosto();
                                    codigo = item.getFactura().getCodigo();
                                }
                                writer.append(item.getFactura().getCodigo() + ","
                                        + sdf.format(item.getFactura().getFecha()) + ","
                                        + item.getEnsamblar_mueble().getIdentificador() + ","
                                        + item.getEnsamblar_mueble().getMueble().getNombre() + ","
                                        + item.getEnsamblar_mueble().getMueble().getPrecio() + "\n");
                            }
                            writer.append("Factura,Fecha de devolución,Código producto,Nombre,Reembolso,Pérdida\n");
                            devolucion_data = devolucionDao.listByUsuarioFecha(usuario1.getId(), inicio, fin);
                            for (Devolucion item : devolucion_data) {
                                writer.append(item.getDetalle_factura().getFactura().getCodigo() + ","
                                        + sdf.format(item.getFecha_devolucion()) + ","
                                        + item.getDetalle_factura().getEnsamblar_mueble().getIdentificador() + ","
                                        + item.getDetalle_factura().getEnsamblar_mueble().getMueble().getNombre() + ","
                                        + item.getDetalle_factura().getEnsamblar_mueble().getMueble().getPrecio() + ","
                                        + item.getPerdida() + "\n");
                            }

                            writer.append("Ganancia total\n");
                            writer.append(total + "\n");
                        }
                        break;
                    case "export_max_venta_mueble":
                        Mueble mueble = muebleDao.getMasVendido(inicio, fin);
                        if (mueble != null) {
                            writer.append("Mueble\n");
                            writer.append(mueble.getNombre() + "\n");
                            writer.append("Factura,Fecha de venta,Código producto,Nombre,Precio\n");
                            detalle_factura_data = detalle_facturaDao.listByFechaMueble(mueble.getId(), inicio, fin);
                            for (Detalle_factura item : detalle_factura_data) {
                                writer.append(item.getFactura().getCodigo() + ","
                                        + sdf.format(item.getFactura().getFecha()) + ","
                                        + item.getEnsamblar_mueble().getIdentificador() + ","
                                        + item.getEnsamblar_mueble().getMueble().getNombre() + ","
                                        + item.getEnsamblar_mueble().getMueble().getPrecio() + "\n");
                            }
                        }
                        break;
                    case "export_min_venta_mueble":
                        Mueble mueble1 = muebleDao.getMenosVendido(inicio, fin);
                        if (mueble1 != null) {
                            writer.append("Mueble\n");
                            writer.append(mueble1.getNombre() + "\n");
                            writer.append("Factura,Fecha de venta,Código producto,Nombre,Precio\n");
                            detalle_factura_data = detalle_facturaDao.listByFechaMueble(mueble1.getId(), inicio, fin);
                            for (Detalle_factura item : detalle_factura_data) {
                                writer.append(item.getFactura().getCodigo() + ","
                                        + sdf.format(item.getFactura().getFecha()) + ","
                                        + item.getEnsamblar_mueble().getIdentificador() + ","
                                        + item.getEnsamblar_mueble().getMueble().getNombre() + ","
                                        + item.getEnsamblar_mueble().getMueble().getPrecio() + "\n");
                            }
                        }
                        break;
                }
                writer.flush();
                writer.close();
            }
        } catch (IOException ex) {
            System.out.println("Exception. Export venta");
        }

    }

    public boolean isLogged() {
        List<String> atributos = Collections.list(session.getAttributeNames());
        if (atributos.contains("user")) {
            return session.getAttribute("user") != null;
        }
        return false;
    }
}
