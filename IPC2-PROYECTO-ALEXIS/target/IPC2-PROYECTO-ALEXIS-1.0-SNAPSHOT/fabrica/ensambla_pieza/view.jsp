

<%@page import="dao.Ensambla_piezaDao"%>
<%@page import="model.Ensambla_pieza"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Ver Ensambla_pieza</title>
    </head>
    <body>
        <%@include file="../../template/menu_fabrica.jsp" %>
        <div class="container body">
            <h3>Ensambla_pieza: Información</h3>

            <%
                Ensambla_pieza item = new Ensambla_piezaDao().search(Integer.parseInt(request.getParameter("id")));
            %>

            <br>
            <div class="form-row">
                <div class="col-md-2">
                    <label><b>Mueble</b></label>
                </div>
                <%= item.getMueble()%>
            </div> 
            <div class="form-row">
                <div class="col-md-2">
                    <label><b>Pieza</b></label>
                </div>
                <%= item.getPieza()%>
            </div> 
            <div class="form-row">
                <div class="col-md-2">
                    <label><b>Cantidad</b></label>
                </div>
                <%= item.getCantidad()%>
            </div> 
            <br>
            <div class="form-row">
                <div class="col-md-12">
                    <a href="Ensambla_piezaController?accion=list" class="btn btn-success"> Volver</a>
                </div>
            </div>
        </div>
    </body>
</html>
