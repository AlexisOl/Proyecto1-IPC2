<%@page import="helper.Definitions"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="dao.Ensamblar_muebleDao"%>
<%@page import="java.util.Iterator"%>
<%@page import="model.Ensamblar_mueble"%>
<%@page import="helper.Message"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Lista Ensamblar_muebles</title>
    </head>
    <body>
        <%@include file="../../template/menu_fabrica.jsp" %>
        <div class="container body">
            <h1>Ensamblar_muebles</h1>

            <% if (!String.valueOf(request.getParameter("success")).equals("null")) {%>
            <div class="alert alert-success alert-dismissable">
                <button type="button" class="close" data-dismiss="alert">&times;</button>
                <strong>¡Éxito!</strong> Mueble <%=request.getParameter("success")%> correctamente
            </div> 
            <%}%>

            <% if (!String.valueOf(request.getParameter("error")).equals("null")) {%>
            <div class="alert alert-danger alert-dismissable">
                <button type="button" class="close" data-dismiss="alert">&times;</button>
                <strong>¡Error!</strong> <p><%= Message.VALUE%></p> <br>                
            </div> 
            <%}%>

            <%
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                String filter = "", order = "asc", state = "";
                if (!String.valueOf(request.getParameter("filter")).equals("null")) {
                    filter = String.valueOf(request.getParameter("filter")).trim();
                }
                if (!String.valueOf(request.getParameter("order")).equals("null")) {
                    order = String.valueOf(request.getParameter("order")).trim();
                }
                if (!String.valueOf(request.getParameter("state")).equals("null")) {
                    state = String.valueOf(request.getParameter("state")).trim();
                }
            %>

            <form action="Ensamblar_muebleController" method="GET">
                <div class="form-row">
                    <div class="col-md-12">
                        <label class="control-label">Buscar por Mueble</label>                        
                    </div>
                </div>
                <div class="form-row">
                    <div class="col-md-4">
                        <input class="form-control" type="text" name="filter" value="<%= filter%>"/>
                    </div>
                    <div class="col-md-3">
                        <select class="form-control" name="order">
                            <%if (order.equals("asc")) {%>
                            <option value="asc" selected="true">Fecha Ascendente</option>
                            <option value="desc">Stock Descendente</option>
                            <%} else {%>
                            <option value="asc">Stock Ascendente</option>
                            <option value="desc" selected="true">Fecha Descendente</option>
                            <%}%>
                        </select>
                    </div>
                    <div class="col-md-2">
                        <select class="form-control" name="state">
                            <option value="">Todo</option>                            
                            <%
                                for (String item : Definitions.MUEBLE_ESTADOS) {
                                    if (item.equals(state)) {
                            %>
                            <option value="<%= item%>" selected="true"><%= item%></option>   
                            <%} else {%>
                            <option value="<%= item%>"><%= item%></option> 
                            <%
                                    }
                                }
                            %>
                        </select>
                    </div>
                    <div class="col-md-3 text-right">
                        <input type="submit" name="accion" value="Buscar" class="btn btn-danger" />
                        <a href="Ensamblar_muebleController?accion=add" class="btn btn-success"><i class="fa fa-plus" aria-hidden="true"></i> Ensamblar Nuevo</a>
                    </div>
                </div>
            </form>

            <br>

            <table class="table">
                <tr>
                    <th>Mueble</th>
                    <th>Usuario</th>
                    <th>Identificador</th>
                    <th>Fecha</th>
                    <th>Estado</th>
                    <th></th>
                </tr>
                <%
                    Iterator<Ensamblar_mueble> iterator = new Ensamblar_muebleDao().list(filter, order, state).iterator();
                    Ensamblar_mueble item = null;

                    while (iterator.hasNext()) {
                        item = iterator.next();
                %>
                <tr>
                    <td><%= item.getMueble()%></td>
                    <td><%= item.getUsuario()%></td>
                    <td><%= item.getIdentificador()%></td>
                    <td><%= sdf.format(item.getFecha())%></td>
                    <td><%= item.getEstado()%></td>
                    <td class="text-right">
                        <div class="dropdown">
                            <button class="btn btn-primary dropdown-toggle" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                Opciones
                            </button>
                            <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                                <% if (item.getEstado().equals("Ensamblado")
                                            || item.getEstado().equals("Devuelto")) {%>
                                <a class="dropdown-item" href="Ensamblar_muebleController?accion=setventa&id=<%= item.getId()%>">Poner en venta</a>
                                <%}%>
                                <a class="dropdown-item" href="Ensamblar_muebleController?accion=view&id=<%= item.getId()%>">Ver</a>
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

