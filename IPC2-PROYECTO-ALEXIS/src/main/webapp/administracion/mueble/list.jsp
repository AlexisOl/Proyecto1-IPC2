<%@page import="dao.MuebleDao"%>
<%@page import="java.util.Iterator"%>
<%@page import="model.Mueble"%>
<%@page import="helper.Message"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Lista Muebles</title>
    </head>
    <body>
        <%@include file="../../template/menu_administracion.jsp" %>
        <div class="container body">
            <h1>Muebles</h1>

            <% if (!String.valueOf(request.getParameter("success")).equals("null")) {%>
            <div class="alert alert-success alert-dismissable">
                <button type="button" class="close" data-dismiss="alert">&times;</button>
                <strong>¡Éxito!</strong> Mueble <%=request.getParameter("success")%> correctamente
            </div> 
            <%}%>

            <% if (!String.valueOf(request.getParameter("error")).equals("null")) {%>
            <div class="alert alert-danger alert-dismissable">
                <button type="button" class="close" data-dismiss="alert">&times;</button>
                <strong>¡Error!</strong> No se eliminó el Mueble <br>
                <p><%= Message.VALUE%></p>
            </div> 
            <%}%>

            <%
                String filter = "";
                if (!String.valueOf(request.getParameter("filter")).equals("null")) {
                    filter = String.valueOf(request.getParameter("filter")).trim();
                }
            %>

            <form action="MuebleController" method="GET">
                <div class="form-row">
                    <div class="col-md-12">
                        <label class="control-label">Buscar por Nombre</label>                        
                    </div>
                </div>
                <div class="form-row">
                    <div class="col-md-9">
                        <input class="form-control" type="text" name="filter" value="<%= filter%>"/>
                    </div>
                    <div class="col-md-3 text-right">
                        <input type="submit" name="accion" value="Buscar" class="btn btn-danger" />
                        <a href="MuebleController?accion=add" class="btn btn-success"><i class="fa fa-plus" aria-hidden="true"></i> Crear Nuevo</a>
                    </div>
                </div>
            </form>

            <br>

            <table class="table">
                <tr>
                    <th>Nombre</th>
                    <th>Precio</th>
                    <th></th>
                </tr>
                <%
                    Iterator<Mueble> iterator = new MuebleDao().list(filter).iterator();
                    Mueble item = null;

                    while (iterator.hasNext()) {
                        item = iterator.next();
                %>
                <tr>
                    <td><%= item.getNombre()%></td>
                    <td><%= item.getPrecio()%></td>
                    <td class="text-right">
                        <div class="dropdown">
                            <button class="btn btn-primary dropdown-toggle" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                Opciones
                            </button>
                            <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                                <a class="dropdown-item" href="MuebleController?accion=edit&id=<%= item.getId()%>">Editar</a>
                                <a class="dropdown-item" href="MuebleController?accion=delete&id=<%= item.getId()%>">Eliminar</a>                                
                                <a class="dropdown-item" href="MuebleController?accion=view&id=<%= item.getId()%>">Ver</a>
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

