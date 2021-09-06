<%@page import="java.text.SimpleDateFormat"%>
<%@page import="dao.Detalle_facturaDao"%>
<%@page import="model.Detalle_factura"%>
<%@page import="helper.Definitions"%>
<%@page import="java.util.Iterator"%>
<%@page import="helper.Message"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Reporte ganacia</title>
    </head>
    <body>
        <%@include file="../../template/menu_administracion.jsp" %>
        <div class="container body">
            <h1>Reporte de ganancia por rango de fecha</h1>

            <%
                Detalle_facturaDao dao = new Detalle_facturaDao();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                String inicio = "", fin = "";

                if (!String.valueOf(request.getParameter("inicio")).equals("null")) {
                    inicio = request.getParameter("inicio");
                }
                if (!String.valueOf(request.getParameter("fin")).equals("null")) {
                    fin = request.getParameter("fin");
                }
            %>

            <form action="ReporteController" method="GET">
                <div class="form-row">
                    <div class="col-md-3">
                        <label class="control-label">Fecha inicio</label>                        
                    </div>
                    <div class="col-md-3">
                        <label class="control-label">Fecha fin</label>                        
                    </div>
                </div>
                <div class="form-row">
                    <div class="col-md-3">
                        <input class="form-control" type="date" name="inicio" value="<%=inicio%>" required="true"/>
                    </div>
                    <div class="col-md-3">
                        <input class="form-control" type="date" name="fin" value="<%=fin%>" required="true"/>
                    </div>
                    <div class="col-md-3">
                        <input type="submit" name="accion" value="Filtrar ganancia" class="btn btn-danger" />
                    </div>
                    <div class="col-md-3 text-right">
                        <a href="ReporteController?accion=export_ganancia&inicio=<%=inicio%>&fin=<%=fin%>" class="btn btn-warning"><i class="fa fa-save" aria-hidden="true"></i> Exportar</a>
                    </div>
                </div>
            </form>

            <br>
            <br>
            <div class="form-row">
                <div class="col-md-12">
                    <h3 class="text-center">
                        <label>Ganancia $<%= dao.getGananciaByFecha(inicio, fin)%> </label>
                    </h3>
                </div>                
            </div>
            <br>

            <table class="table">
                <tr>
                    <th>Código producto</th>
                    <th>Nombre</th>
                    <th>Precio</th>
                    <th>Fecha de venta</th>
                </tr>
                <%
                    Iterator<Detalle_factura> iterator = dao.listByFechaForGanancia(inicio, fin).iterator();
                    Detalle_factura item = null;

                    while (iterator.hasNext()) {
                        item = iterator.next();
                %>
                <tr>
                    <td><%= item.getEnsamblar_mueble().getIdentificador()%></td>
                    <td><%= item.getEnsamblar_mueble().getMueble().getNombre()%></td>
                    <td><%= item.getEnsamblar_mueble().getMueble().getPrecio()%></td>   
                    <td><%= sdf.format(item.getFactura().getFecha())%></td>
                </tr>
                <%
                    }
                %>
            </table>
        </div>
    </body>
</html>

