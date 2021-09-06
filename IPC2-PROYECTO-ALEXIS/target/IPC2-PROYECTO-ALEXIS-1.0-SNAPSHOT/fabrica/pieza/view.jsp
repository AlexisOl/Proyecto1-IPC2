

<%@page import="dao.PiezaDao"%>
<%@page import="model.Pieza"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Ver Pieza</title>
    </head>
    <body>
        <%@include file="../../template/menu_fabrica.jsp" %>
        <div class="container body">
            <h3>Pieza: Información</h3>

            <%
                Pieza item = new PiezaDao().search(Integer.parseInt(request.getParameter("id")));
            %>

            <br>
            <div class="form-row">
                <div class="col-md-2">
                    <label><b>Tipo</b></label>
                </div>
                <%= item.getTipo()%>
            </div> 
            <div class="form-row">
                <div class="col-md-2">
                    <label><b>Costo</b></label>
                </div>
                <%= item.getCosto()%>
            </div> 
            <div class="form-row">
                <div class="col-md-2">
                    <label><b>Stock</b></label>
                </div>
                <%= item.getStock()%>
            </div> 
            <br>
            <div class="form-row">
                <div class="col-md-12">
                    <a href="PiezaController?accion=list" class="btn btn-success"> Volver</a>
                </div>
            </div>
        </div>
    </body>
</html>
