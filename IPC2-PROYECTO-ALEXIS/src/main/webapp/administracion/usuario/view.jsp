<%@page import="dao.UsuarioDao"%>
<%@page import="model.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Ver Usuario</title>
    </head>
    <body>
        <%@include file="../../template/menu_administracion.jsp" %>
        <div class="container body">
            <h3>Usuario: Información</h3>

            <%
                Usuario item = new UsuarioDao().search(Integer.parseInt(request.getParameter("id")));
            %>

            <br>
            <div class="form-row">
                <div class="col-md-2">
                    <label><b>Username</b></label>
                </div>
                <%= item.getUsername()%>
            </div> 
            <div class="form-row">
                <div class="col-md-2">
                    <label><b>Tipo</b></label>
                </div>
                <%= item.getTipoUsuario()%>
            </div> 
            <div class="form-row">
                <div class="col-md-2">
                    <label><b>Estado</b></label>
                </div>
                <%= item.getEstado()%>
            </div> 
            <br>
            <div class="form-row">
                <div class="col-md-12">
                    <a href="UsuarioController?accion=list" class="btn btn-success"> Volver</a>
                </div>
            </div>
        </div>
    </body>
</html>