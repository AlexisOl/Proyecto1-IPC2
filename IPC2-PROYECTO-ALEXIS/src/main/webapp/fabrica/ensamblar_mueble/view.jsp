

<%@page import="java.text.SimpleDateFormat"%>
<%@page import="dao.Ensamblar_muebleDao"%>
<%@page import="model.Ensamblar_mueble"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Ver Ensamblar_mueble</title>
    </head>
    <body>
        <%@include file="../../template/menu_fabrica.jsp" %>
        <div class="container body">
            <h3>Ensamblar_mueble: Información</h3>

            <%
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Ensamblar_mueble item = new Ensamblar_muebleDao().search(Integer.parseInt(request.getParameter("id")));
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
                    <label><b>Usuario</b></label>
                </div>
                <%= item.getUsuario()%>
            </div> 
            <div class="form-row">
                <div class="col-md-2">
                    <label><b>Identificador</b></label>
                </div>
                <%= item.getIdentificador()%>
            </div> 
            <div class="form-row">
                <div class="col-md-2">
                    <label><b>Fecha</b></label>
                </div>
                <%= sdf.format(item.getFecha())%>
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
                    <a href="Ensamblar_muebleController?accion=list" class="btn btn-success"> Volver</a>
                </div>
            </div>
        </div>
    </body>
</html>
