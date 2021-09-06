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
            <h1>Reintegración de muebles</h1>

            <%
                String filter = "";
                if (!String.valueOf(request.getParameter("filter")).equals("null")) {
                    filter = String.valueOf(request.getParameter("filter")).trim();
                }
            %>

            <% if (!String.valueOf(request.getParameter("success")).equals("null")) {%>
            <div class="alert alert-success alert-dismissable">
                <button type="button" class="close" data-dismiss="alert">&times;</button>
                <strong>¡Éxito!</strong> Mueble <%=request.getParameter("success")%> correctamente
            </div> 
            <%}%>

            <% if (!String.valueOf(request.getParameter("error")).equals("null")) {%>
            <div class="alert alert-danger alert-dismissable">
                <button type="button" class="close" data-dismiss="alert">&times;</button>
                <strong>¡Error!</strong> <p><%= Message.VALUE%></p> <br>                
            </div> 
            <%}%>

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
                    <th>Precio</th>
                    <th>Estado</th>
                    <th></th>
                </tr>
                <%
                    Iterator<Ensamblar_mueble> iterator = new Ensamblar_muebleDao().listByEstado(filter, "Devuelto").iterator();
                    Ensamblar_mueble item = null;

                    while (iterator.hasNext()) {
                        item = iterator.next();
                %>
                <tr>
                    <td><%= item.getIdentificador()%></td>
                    <td><%= item.getMueble()%></td>
                    <td><%= item.getMueble().getPrecio()%></td>
                    <td><%= item.getEstado()%></td>
                    <td>
                        <a class="btn-sm btn-primary" href="Ensamblar_muebleController?accion=reintegrar&id=<%= item.getId()%>">Reintegrar</a>
                    </td>
                </tr>
                <%
                    }
                %>
            </table>
        </div>
    </body>
</html>

