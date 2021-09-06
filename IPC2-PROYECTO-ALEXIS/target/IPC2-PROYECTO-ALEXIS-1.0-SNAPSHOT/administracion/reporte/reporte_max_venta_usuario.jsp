<%@page import="dao.UsuarioDao"%>
<%@page import="model.Usuario"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="dao.Detalle_facturaDao"%>
<%@page import="model.Detalle_factura"%>
<%@page import="helper.Definitions"%>
<%@page import="java.util.Iterator"%>
<%@page import="helper.Message"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Reporte Usuario mayor ventas</title>
    </head>
    <body>
        <%@include file="../../template/menu_administracion.jsp" %>
        <div class="container body">
            <h1>Reporte de usuario con más ventas en rango de fecha</h1>

            <%
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                String inicio = "", fin = "";

                if (!String.valueOf(request.getParameter("inicio")).equals("null")) {
                    inicio = request.getParameter("inicio");
                }
                if (!String.valueOf(request.getParameter("fin")).equals("null")) {
                    fin = request.getParameter("fin");
                }
            %>

            <form action="ReporteController" method="GET">
                <div class="form-row">
                    <div class="col-md-3">
                        <label class="control-label">Fecha inicio</label>                        
                    </div>
                    <div class="col-md-3">
                        <label class="control-label">Fecha fin</label>                        
                    </div>
                </div>
                <div class="form-row">
                    <div class="col-md-3">
                        <input class="form-control" type="date" name="inicio" value="<%=inicio%>" required="true"/>
                    </div>
                    <div class="col-md-3">
                        <input class="form-control" type="date" name="fin" value="<%=fin%>" required="true"/>
                    </div>
                    <div class="col-md-3">
                        <input type="submit" name="accion" value="Filtrar vendedor" class="btn btn-danger" />
                    </div>
                    <div class="col-md-3 text-right">
                        <a href="ReporteController?accion=export_max_venta_usuario&inicio=<%=inicio%>&fin=<%=fin%>" class="btn btn-warning"><i class="fa fa-save" aria-hidden="true"></i> Exportar</a>
                    </div>
                </div>
            </form>

            <br>
            <%
                Usuario usuario = new UsuarioDao().getUsuarioMaxVenta(inicio, fin);
                if (usuario == null) {
            %>
            <div class="form-row">
                <h3>Sin resultados</h3>
            </div>
            <%} else {%>
            <div class="form-row">
                <h3>Usuario con más ventas [ <%=usuario.getUsername()%> ]</h3>
            </div>
            <br>
            
            <table class="table">
                <tr>
                    <th>Factura</th>
                    <th>Fecha de venta</th>
                    <th>Código producto</th>
                    <th>Nombre</th>
                    <th>Precio</th>
                </tr>
                <%
                    Iterator<Detalle_factura> iterator = new Detalle_facturaDao().listByFechaUsuario(usuario.getId(),inicio, fin).iterator();
                    Detalle_factura item = null;
                    int count = 0;
                    String codigo = "";

                    while (iterator.hasNext()) {
                        item = iterator.next();
                        if(!item.getFactura().getCodigo().equals(codigo)){
                            count++;
                            codigo = item.getFactura().getCodigo();
                        }
                %>
                <tr>
                    <td><%= item.getFactura().getCodigo()%></td>
                    <td><%= sdf.format(item.getFactura().getFecha())%></td>
                    <td><%= item.getEnsamblar_mueble().getIdentificador()%></td>
                    <td><%= item.getEnsamblar_mueble().getMueble().getNombre()%></td>
                    <td><%= item.getEnsamblar_mueble().getMueble().getPrecio()%></td>                    
                </tr>
                <%
                    }
                %>
            </table>
            <br>
            <div class="form-row">
                <h3>Cantidad de ventas [ <%=count%> facturas ]</h3>
            </div>
            <%}%>            
        </div>
    </body>
</html>

