
<%@page import="dao.UsuarioDao"%>
<%@page import="model.Usuario"%>
<%@page import="java.util.ArrayList"%>
<%@page import="model.Cliente"%>
<%@page import="dao.Ensamblar_muebleDao"%>
<%@page import="model.Ensamblar_mueble"%>
<%@page import="java.util.Iterator"%>
<%@page import="helper.Message"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Agregar Factura</title>
    </head>
    <body>
        <%@include file="../../template/menu_venta.jsp" %>
        <div class="container body">            
            <h3>Factura : Información de cliente</h3>            
            <br>

            <%
                String mensaje = session.getAttribute("mensaje").toString();
                Cliente cliente = (Cliente) session.getAttribute("cliente");
                Ensamblar_muebleDao ensamblar_muebleDao = new Ensamblar_muebleDao();
                double total = 0;
            %>

            <% if (mensaje.startsWith("_")) {%>
            <div class="alert alert-danger alert-dismissable">
                <button type="button" class="close" data-dismiss="alert">&times;</button>
                <strong>¡Error!</strong> <%=mensaje%>
            </div> 
            <%}%>
            <% if (mensaje.endsWith("_")) {%>
            <div class="alert alert-success alert-dismissable">
                <button type="button" class="close" data-dismiss="alert">&times;</button>
                <strong>¡Éxito!</strong> <%=mensaje%>
            </div> 
            <%}%>

            <form action="FacturaController" method="POST">
                <div>
                    <label>NIT <b class="text-danger">*</b></label>
                </div>
                <div class="form-row">                        
                    <div class="col-md-6">
                        <input class="form-control" type="text" name="nit" value="<%=cliente.getNit()%>" minlength="7" maxlength="7" required="true"/>
                    </div>
                    <div class="col-md-6">
                        <input class="btn btn-danger" type="submit" name="accion" value="Buscar cliente" />
                    </div>
                </div> 
                <br>
                <div class="form-row">                        
                    <div class="col-md-6">
                        <label>Nombre <b class="text-danger">*</b></label>
                        <input class="form-control" type="text" name="nombre" value="<%=cliente.getNombre()%>"/>
                    </div>
                    <div class="col-md-6">
                        <label>Dirección <b class="text-danger">*</b></label>
                        <input class="form-control" type="text" name="direccion" value="<%=cliente.getDireccion()%>"/>
                    </div>
                </div> 
                <br>
                <div class="form-row">                        
                    <div class="col-md-6">
                        <label>Municipio </label>
                        <input class="form-control" type="text" name="municipio" value="<%=cliente.getMunicipio()%>"/>
                    </div>
                    <div class="col-md-6">
                        <label>Departamento </label>
                        <input class="form-control" type="text" name="departamento" value="<%=cliente.getDepartamento()%>"/>
                    </div>
                </div>
                <br>
                <h3>Detalle de Factura</h3>

                <div><label>Seleccionar Mueble </label></div>
                <div class="form-row">                        
                    <div class="col-md-6">
                        <select class="form-control" name="ensamblar_mueble_id">
                            <%
                                Iterator<Ensamblar_mueble> listaMuebles = ensamblar_muebleDao.list("", "desc", "En venta").iterator();
                                Ensamblar_mueble mueble = null;
                                while (listaMuebles.hasNext()) {
                                    mueble = listaMuebles.next();
                            %>
                            <option value="<%= mueble.getId()%>"><%= mueble%></option>
                            <%}%>
                        </select>
                    </div>
                    <div class="col-md-6">
                        <input class="btn btn-success" type="submit" name="accion" value="Agregar" />
                    </div>
                </div>
                <br>
                <%
                    ArrayList<Integer> detalle = (ArrayList<Integer>) session.getAttribute("detalle");
                    if (detalle.isEmpty()) {
                %>
                <div class="form-row"><p>Sin productos agregados</p></div>
                <%} else {%>
                <div class="form-row">
                    <table class="table">
                        <tr>
                            <th>Identificador</th>
                            <th>Nombre</th>
                            <th>Precio</th>
                        </tr>
                        <%
                            for (int ensamblar_mueble_id : detalle) {
                                Ensamblar_mueble item = ensamblar_muebleDao.search(ensamblar_mueble_id);
                                total += item.getMueble().getPrecio();
                        %>
                        <tr>
                            <td><%= item.getIdentificador()%></td>
                            <td><%= item.getMueble().getNombre()%></td>
                            <td><%= item.getMueble().getPrecio()%></td>
                        </tr>
                        <%
                            }
                        %>
                    </table>
                </div>
                <%}%>
                <br>
                <div class="form-row">
                    <div class="col-md-6">
                        <label>Total a pagar </label>
                        <input class="form-control" type="text" name="total" value="<%=total%>" readonly="true"/>
                    </div>
                    <div class="col-md-6">
                        <label>Vendedor </label>
                        <select class="form-control" name="usuario_id">
                            <option value="">Seleccionar vendedor...</option>
                            <%
                                Iterator<Usuario> listaUsuarios = new UsuarioDao().list(2).iterator();
                                Usuario usuario = null;
                                while (listaUsuarios.hasNext()) {
                                    usuario = listaUsuarios.next();
                            %>
                            <option value="<%= usuario.getId()%>"><%= usuario%></option>
                            <%}%>
                        </select>
                    </div>
                </div>
                <br>
                <div class="form-row">
                    <div class="col-md-6">
                        <input class="btn btn-primary" type="submit" name="accion" value="Comprar"/> 
                        <a href="FacturaController?accion=list" class="btn btn-danger"> Cancelar</a>
                    </div>
                </div>
                <br>
            </form>
        </div>
    </body>
</html>
