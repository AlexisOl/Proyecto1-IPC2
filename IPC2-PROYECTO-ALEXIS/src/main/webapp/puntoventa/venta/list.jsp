<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="helper.Definitions"%>
<%@page import="dao.FacturaDao"%>
<%@page import="java.util.Iterator"%>
<%@page import="model.Factura"%>
<%@page import="helper.Message"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Lista Facturas</title>
    </head>
    <body>
        <%@include file="../../template/menu_venta.jsp" %>
        <div class="container body">
            <h1>Facturas</h1>

            <% if (!String.valueOf(request.getParameter("success")).equals("null")) {%>
            <div class="alert alert-success alert-dismissable">
                <button type="button" class="close" data-dismiss="alert">&times;</button>
                <strong>¡Éxito!</strong> Factura <%=request.getParameter("success")%> correctamente
            </div> 
            <%}%>

            <% if (!String.valueOf(request.getParameter("error")).equals("null")) {%>
            <div class="alert alert-danger alert-dismissable">
                <button type="button" class="close" data-dismiss="alert">&times;</button>
                <strong>¡Error!</strong> No se eliminó la Factura <br>
                <p><%= Message.VALUE%></p>
            </div> 
            <%}%>

            <%
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                String filter = "", date = "";

                if (!String.valueOf(request.getParameter("filter")).equals("null")) {
                    filter = String.valueOf(request.getParameter("filter")).trim();
                }
                if (!String.valueOf(request.getParameter("date")).equals("null")) {
                    date = request.getParameter("date");
                }
            %>

            <form action="FacturaController" method="GET">
                <div class="form-row">
                    <div class="col-md-12">
                        <label class="control-label">Buscar por NIT o Código de factura</label>                        
                    </div>
                </div>
                <div class="form-row">
                    <div class="col-md-7">
                        <input class="form-control" type="text" name="filter" value="<%= filter%>"/>
                    </div>
                    <div class="col-md-2">
                        <input class="form-control" type="date" name="date" value="<%=date%>"/>
                    </div>
                    <div class="col-md-3 text-right">
                        <input type="submit" name="accion" value="Buscar" class="btn btn-danger" />
                        <a href="FacturaController?accion=add" class="btn btn-success"><i class="fa fa-plus" aria-hidden="true"></i> Crear Nuevo</a>
                    </div>
                </div>
            </form>

            <br>

            <table class="table">
                <tr>
                    <th>Código</th>
                    <th>Cliente</th>
                    <th>Vendedor</th>
                    <th>Fecha</th>
                    <th>Costo</th>
                    <th></th>
                </tr>
                <%
                    Iterator<Factura> iterator = new FacturaDao().list(filter, date).iterator();
                    Factura item = null;

                    while (iterator.hasNext()) {
                        item = iterator.next();
                %>
                <tr>
                    <td><%= item.getCodigo()%></td>
                    <td><%= item.getCliente()%></td>
                    <td><%= item.getUsuario()%></td>
                    <td><%= sdf.format(item.getFecha())%></td>
                    <td><%= item.getCosto()%></td>
                    <td class="text-right">
                        <a class="btn-sm btn-primary" href="FacturaController?accion=view&id=<%= item.getId()%>">Ver detalle</a>
                    </td>

                </tr>
                <%
                    }
                %>
            </table>
        </div>
    </body>
</html>

