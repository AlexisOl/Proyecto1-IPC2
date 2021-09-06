

<%@page import="dao.Detalle_facturaDao"%>
<%@page import="model.Detalle_factura"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="dao.FacturaDao"%>
<%@page import="model.Factura"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Ver Factura</title>
    </head>
    <body>
        <%@include file="../../template/menu_venta.jsp" %>
        <div class="container body">
            <h3>Factura: Información</h3>

            <%
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Factura item = new FacturaDao().search(Integer.parseInt(request.getParameter("id")));
                ArrayList<Detalle_factura> detalle = new Detalle_facturaDao().listByFactura(item.getId());
            %>

            <br>
            <div class="form-row">
                <div class="col-md-2">
                    <label><b>Código</b></label>
                </div>
                <%= item.getCodigo()%>
            </div> 
            <div class="form-row">
                <div class="col-md-2">
                    <label><b>Cliente</b></label>
                </div>
                <%= item.getCliente().informacion()%>
            </div> 
            <div class="form-row">
                <div class="col-md-2">
                    <label><b>Usuario</b></label>
                </div>
                <%= item.getUsuario()%>
            </div> 
            <div class="form-row">
                <div class="col-md-2">
                    <label><b>Fecha</b></label>
                </div>
                <%= sdf.format(item.getFecha())%>
            </div> 
            <div class="form-row">
                <div class="col-md-2">
                    <label><b>Costo</b></label>
                </div>
                <%= item.getCosto()%>
            </div>
            <br>
            <div class="form-row">                
                <div class="col-md-2">
                    <label><b>Detalle de factura</b></label>
                </div>
                <table class="table">
                    <tr>
                        <th>Identificador</th>
                        <th>Nombre</th>
                        <th>Precio</th>
                    </tr>
                    <%
                        for (Detalle_factura df : detalle) {
                    %>
                    <tr>
                        <td><%= df.getEnsamblar_mueble().getIdentificador()%></td>
                        <td><%= df.getEnsamblar_mueble().getMueble().getNombre()%></td>
                        <td><%= df.getEnsamblar_mueble().getMueble().getPrecio()%></td>
                    </tr>
                    <%
                        }
                    %>
                </table>
            </div>
            <br>
            <div class="form-row">
                <div class="col-md-12">
                    <a href="FacturaController?accion=list" class="btn btn-success"> Volver</a>
                </div>
            </div>
        </div>
    </body>
</html>
