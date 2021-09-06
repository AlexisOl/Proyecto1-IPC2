<%@page import="helper.Definitions"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="dao.Ensamblar_muebleDao"%>
<%@page import="java.util.Iterator"%>
<%@page import="model.Ensamblar_mueble"%>
<%@page import="helper.Message"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Lista Muebles disponibles</title>
    </head>
    <body>
        <%@include file="../../template/menu_venta.jsp" %>
        <div class="container body">
            <h1>Muebles disponibles en venta</h1>

            <%
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                String filter = "";
                if (!String.valueOf(request.getParameter("filter")).equals("null")) {
                    filter = String.valueOf(request.getParameter("filter")).trim();
                }
            %>

            <form action="Ensamblar_muebleController" method="GET">
                <div class="form-row">
                    <div class="col-md-12">
                        <label class="control-label">Buscar por Mueble o Identificador</label>                        
                    </div>
                </div>
                <div class="form-row">
                    <div class="col-md-9">
                        <input class="form-control" type="text" name="filter" value="<%= filter%>"/>
                    </div>
                    <div class="col-md-3">
                        <input type="submit" name="accion" value="Filtrar" class="btn btn-danger" />
                    </div>
                </div>
            </form>

            <br>

            <table class="table">
                <tr>
                    <th>Identificador</th>
                    <th>Mueble</th>                    
                    <th>Ensamblador</th>
                    <th>Precio</th>
                    <th>Fecha de ensamblado</th>
                    <th>Estado</th>
                </tr>
                <%
                    Iterator<Ensamblar_mueble> iterator = new Ensamblar_muebleDao().listByEstado(filter, "En venta").iterator();
                    Ensamblar_mueble item = null;

                    while (iterator.hasNext()) {
                        item = iterator.next();
                %>
                <tr>
                    <td><%= item.getIdentificador()%></td>
                    <td><%= item.getMueble()%></td>
                    <td><%= item.getUsuario()%></td>
                    <td><%= item.getMueble().getPrecio()%></td>
                    <td><%= sdf.format(item.getFecha())%></td>
                    <td><%= item.getEstado()%></td>

                </tr>
                <%
                    }
                %>
            </table>
        </div>
    </body>
</html>

