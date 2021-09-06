<%@page import="dao.UsuarioDao"%>
<%@page import="java.util.Iterator"%>
<%@page import="model.Usuario"%>
<%@page import="helper.Message"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Lista Usuarios</title>
    </head>
    <body>
        <%@include file="../../template/menu_administracion.jsp" %>
        <div class="container body">
            <h1>Usuarios</h1>

            <% if (!String.valueOf(request.getParameter("success")).equals("null")) {%>
            <div class="alert alert-success alert-dismissable">
                <button type="button" class="close" data-dismiss="alert">&times;</button>
                <strong>¡Éxito!</strong> Usuario <%=request.getParameter("success")%> correctamente
            </div> 
            <%}%>

            <% if (!String.valueOf(request.getParameter("error")).equals("null")) {%>
            <div class="alert alert-danger alert-dismissable">
                <button type="button" class="close" data-dismiss="alert">&times;</button>
                <strong>¡Error!</strong> No se eliminó el Usuario <br>
                <p><%= Message.VALUE%></p>
            </div> 
            <%}%>

            <div class="col-md-12 text-right">
                <a href="UsuarioController?accion=add" class="btn btn-success"><i class="fa fa-plus" aria-hidden="true"></i> Crear Nuevo</a>
            </div>
            <br>
            <table class="table">
                <tr>
                    <th>Username</th>
                    <th>Tipo</th>
                    <th>Estado</th>
                    <th></th>
                </tr>
                <%
                    Iterator<Usuario> iterator = new UsuarioDao().list().iterator();
                    Usuario item = null;
                    while (iterator.hasNext()) {
                        item = iterator.next();
                %>
                <tr>
                    <td><%= item.getUsername()%></td>
                    <td><%= item.getTipoUsuario()%></td>
                    <td><%= item.getEstado()%></td>
                    <td class="text-right">
                        <div class="dropdown">
                            <button class="btn btn-primary dropdown-toggle" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                Opciones
                            </button>
                            <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                                <a class="dropdown-item" href="UsuarioController?accion=cancel&id=<%= item.getId()%>">Cancelar</a>
                                <a class="dropdown-item" href="UsuarioController?accion=edit&id=<%= item.getId()%>">Editar</a>
                                <a class="dropdown-item" href="UsuarioController?accion=delete&id=<%= item.getId()%>">Eliminar</a>                                
                                <a class="dropdown-item" href="UsuarioController?accion=view&id=<%= item.getId()%>">Ver</a>
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