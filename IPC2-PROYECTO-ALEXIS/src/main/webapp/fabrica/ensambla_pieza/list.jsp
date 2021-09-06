<%@page import="dao.Ensambla_piezaDao"%>
<%@page import="java.util.Iterator"%>
<%@page import="model.Ensambla_pieza"%>
<%@page import="helper.Message"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Lista Ensambla_piezas</title>
    </head>
    <body>
        <%@include file="../../template/menu_fabrica.jsp" %>
        <div class="container body">
            <h1>Ensambla_piezas</h1>

            <% if (!String.valueOf(request.getParameter("success")).equals("null")) {%>
            <div class="alert alert-success alert-dismissable">
                <button type="button" class="close" data-dismiss="alert">&times;</button>
                <strong>¡Éxito!</strong> Ensambla_pieza <%=request.getParameter("success")%> correctamente
            </div> 
            <%}%>

            <% if (!String.valueOf(request.getParameter("error")).equals("null")) {%>
            <div class="alert alert-danger alert-dismissable">
                <button type="button" class="close" data-dismiss="alert">&times;</button>
                <strong>¡Error!</strong> No se eliminó la Ensambla_pieza <br>
                <p><%= Message.VALUE%></p>
            </div> 
            <%}%>

            <%
                String filter = "";
                if (!String.valueOf(request.getParameter("filter")).equals("null")) {
                    filter = String.valueOf(request.getParameter("filter")).trim();
                }
            %>

            <form action="Ensambla_piezaController" method="GET">
                <div class="form-row">
                    <div class="col-md-12">
                        <label class="control-label">Buscar por Mueble o Pieza</label>                        
                    </div>
                </div>
                <div class="form-row">
                    <div class="col-md-9">
                        <input class="form-control" type="text" name="filter" value="<%= filter%>"/>
                    </div>
                    <div class="col-md-3 text-right">
                        <input type="submit" name="accion" value="Buscar" class="btn btn-danger" />
                        <a href="Ensambla_piezaController?accion=add" class="btn btn-success"><i class="fa fa-plus" aria-hidden="true"></i> Crear Nuevo</a>
                    </div>
                </div>
            </form>

            <br>

            <table class="table">
                <tr>
                    <th>Mueble</th>
                    <th>Pieza</th>
                    <th>Cantidad</th>
                    <th></th>
                </tr>
                <%
                    Iterator<Ensambla_pieza> iterator = new Ensambla_piezaDao().list(filter).iterator();
                    Ensambla_pieza item = null;

                    while (iterator.hasNext()) {
                        item = iterator.next();
                %>
                <tr>
                    <td><%= item.getMueble()%></td>
                    <td><%= item.getPieza()%></td>
                    <td><%= item.getCantidad()%></td>
                    <td class="text-right">
                        <div class="dropdown">
                            <button class="btn btn-primary dropdown-toggle" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                Opciones
                            </button>
                            <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                                <a class="dropdown-item" href="Ensambla_piezaController?accion=edit&id=<%= item.getId()%>">Editar</a>
                                <a class="dropdown-item" href="Ensambla_piezaController?accion=delete&id=<%= item.getId()%>">Eliminar</a>                                
                                <a class="dropdown-item" href="Ensambla_piezaController?accion=view&id=<%= item.getId()%>">Ver</a>
                            </div>
                        </div>
                    </td>

                </tr>
                <%
                    }
                %>
            </table>
        </div>
    </body>
</html>

