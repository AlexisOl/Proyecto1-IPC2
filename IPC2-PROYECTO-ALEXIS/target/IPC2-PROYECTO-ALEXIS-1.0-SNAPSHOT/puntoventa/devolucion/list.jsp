<%@page import="java.text.SimpleDateFormat"%>
<%@page import="helper.Definitions"%>
<%@page import="dao.DevolucionDao"%>
<%@page import="java.util.Iterator"%>
<%@page import="model.Devolucion"%>
<%@page import="helper.Message"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Lista Devoluciones</title>
    </head>
    <body>
        <%@include file="../../template/menu_venta.jsp" %>
        <div class="container body">
            <h1>Devoluciones</h1>

            <%
                String filter = "";
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

                if (!String.valueOf(request.getParameter("filter")).equals("null")) {
                    filter = String.valueOf(request.getParameter("filter")).trim();
                }
            %>

            <form action="DevolucionController" method="GET">
                <div class="form-row">
                    <div class="col-md-12">
                        <label class="control-label">Buscar por Identificador de mueble</label>                        
                    </div>
                </div>
                <div class="form-row">
                    <div class="col-md-8">
                        <input class="form-control" type="text" name="filter" value="<%= filter%>"/>
                    </div>
                    <div class="col-md-4 text-right">
                        <input type="submit" name="accion" value="Buscar" class="btn btn-danger" />
                        <a href="DevolucionController?accion=add" class="btn btn-success"><i class="fa fa-plus" aria-hidden="true"></i> Crear Nuevo</a>
                    </div>
                </div>
            </form>
            <br>

            <table class="table">
                <tr>
                    <th>Factura</th>
                    <th>Identificador mueble</th>
                    <th>Cliente</th>
                    <th>Fecha devolución</th>
                    <th>Pérdida</th>
                </tr>
                <%
                    Iterator<Devolucion> iterator = new DevolucionDao().list(filter).iterator();
                    Devolucion item = null;

                    while (iterator.hasNext()) {
                        item = iterator.next();
                %>
                <tr>
                    <td><%= item.getDetalle_factura().getFactura().getCodigo()%></td>
                    <td><%= item.getDetalle_factura().getEnsamblar_mueble().getIdentificador()%></td>
                    <td><%= item.getDetalle_factura().getFactura().getCliente().getNit()%></td>
                    <td><%= sdf.format(item.getFecha_devolucion())%></td>
                    <td><%= item.getPerdida()%></td>
                </tr>
                <%
                    }
                %>
            </table>
        </div>
    </body>
</html>

