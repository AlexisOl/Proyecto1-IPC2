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
            <h1>Devoluciones de cliente por rango de fechas</h1>

            <%
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                String nit = "", inicio = "", fin = "";

                if (!String.valueOf(request.getParameter("nit")).equals("null")) {
                    nit = String.valueOf(request.getParameter("nit")).trim();
                }
                if (!String.valueOf(request.getParameter("inicio")).equals("null")) {
                    inicio = request.getParameter("inicio");
                }
                if (!String.valueOf(request.getParameter("fin")).equals("null")) {
                    fin = request.getParameter("fin");
                }
            %>

            <form action="DevolucionController" method="GET">
                <div class="form-row">
                    <div class="col-md-6">
                        <label class="control-label">Buscar por NIT</label>                        
                    </div>
                    <div class="col-md-2">
                        <label class="control-label">Fecha inicio</label>                        
                    </div>
                    <div class="col-md-2">
                        <label class="control-label">Fecha fin</label>                        
                    </div>
                </div>
                <div class="form-row">
                    <div class="col-md-6">
                        <input class="form-control" type="text" name="nit" value="<%= nit%>" required="true"/>
                    </div>
                    <div class="col-md-2">
                        <input class="form-control" type="date" name="inicio" value="<%=inicio%>" required="true"/>
                    </div>
                    <div class="col-md-2">
                        <input class="form-control" type="date" name="fin" value="<%=fin%>" required="true"/>
                    </div>
                    <div class="col-md-2 text-right">
                        <input type="submit" name="accion" value="Buscar devolucion" class="btn btn-danger" />
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
                    Iterator<Devolucion> iterator = new DevolucionDao().list(nit, inicio, fin).iterator();
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

