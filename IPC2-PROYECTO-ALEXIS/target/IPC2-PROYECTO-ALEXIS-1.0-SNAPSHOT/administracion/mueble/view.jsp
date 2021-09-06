
<%@page import="dao.MuebleDao"%>
<%@page import="model.Mueble"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Ver Mueble</title>
    </head>
    <body>
        <%@include file="../../template/menu_administracion.jsp" %>
        <div class="container body">
            <h3>Mueble: Información</h3>

            <%
                Mueble item = new MuebleDao().search(Integer.parseInt(request.getParameter("id")));
            %>

            <br>
            <div class="form-row">
                <div class="col-md-2">
                    <label><b>Nombre</b></label>
                </div>
                <%= item.getNombre()%>
            </div> 
            <div class="form-row">
                <div class="col-md-2">
                    <label><b>Precio</b></label>
                </div>
                <%= item.getPrecio()%>
            </div>
            <br>
            <div class="form-row">
                <div class="col-md-12">
                    <a href="MuebleController?accion=list" class="btn btn-success"> Volver</a>
                </div>
            </div>
        </div>
    </body>
</html>