<%@page import="helper.Definitions"%>
<%@page import="dao.PiezaDao"%>
<%@page import="java.util.Iterator"%>
<%@page import="model.Pieza"%>
<%@page import="helper.Message"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Lista Piezas</title>
    </head>
    <body>
        <%@include file="../../template/menu_fabrica.jsp" %>
        <div class="container body">
            <h1>Piezas</h1>

            <% if (!String.valueOf(request.getParameter("success")).equals("null")) {%>
            <div class="alert alert-success alert-dismissable">
                <button type="button" class="close" data-dismiss="alert">&times;</button>
                <strong>¡Éxito!</strong> Pieza <%=request.getParameter("success")%> correctamente
            </div> 
            <%}%>

            <% if (!String.valueOf(request.getParameter("error")).equals("null")) {%>
            <div class="alert alert-danger alert-dismissable">
                <button type="button" class="close" data-dismiss="alert">&times;</button>
                <strong>¡Error!</strong> No se eliminó la Pieza <br>
                <p><%= Message.VALUE%></p>
            </div> 
            <%}%>

            <%
                String filter = "", order = "asc", stock = "";

                if (!String.valueOf(request.getParameter("filter")).equals("null")) {
                    filter = String.valueOf(request.getParameter("filter")).trim();
                }
                if (!String.valueOf(request.getParameter("order")).equals("null")) {
                    order = String.valueOf(request.getParameter("order")).trim();
                }
                if (!String.valueOf(request.getParameter("stock")).equals("null")) {
                    stock = String.valueOf(request.getParameter("stock")).trim();
                }
            %>

            <form action="PiezaController" method="GET">
                <div class="form-row">
                    <div class="col-md-12">
                        <label class="control-label">Buscar por Tipo</label>                        
                    </div>
                </div>
                <div class="form-row">
                    <div class="col-md-4">
                        <input class="form-control" type="text" name="filter" value="<%= filter%>"/>
                    </div>

                    <div class="col-md-3">
                        <select class="form-control" name="stock">
                            <option value="">Todo</option>                            
                            <%
                                for (String es : Definitions.STOCK_ESTADOS) {
                                    if (es.equals(stock)) {
                            %>
                            <option value="<%= es%>" selected="true">Stock <%= es%></option>   
                            <%} else {%>
                            <option value="<%= es%>">Stock <%= es%></option> 
                            <%
                                    }
                                }
                            %>
                        </select>
                    </div>
                    <div>
                        <select class="form-control" name="order">
                            <%if (order.equals("asc")) {%>
                            <option value="asc" selected="true">Stock Ascendente</option>
                            <option value="desc">Stock Descendente</option>
                            <%} else {%>
                            <option value="asc">Stock Ascendente</option>
                            <option value="desc" selected="true">Stock Descendente</option>
                            <%}%>
                        </select>
                    </div>
                    <div class="col-md-3 text-right">
                        <input type="submit" name="accion" value="Buscar" class="btn btn-danger" />
                        <a href="PiezaController?accion=add" class="btn btn-success"><i class="fa fa-plus" aria-hidden="true"></i> Crear Nuevo</a>
                    </div>
                </div>
            </form>

            <br>

            <table class="table">
                <tr>
                    <th>Tipo</th>
                    <th>Costo</th>
                    <th>Stock</th>
                    <th></th>
                </tr>
                <%
                    Iterator<Pieza> iterator = new PiezaDao().list(filter, order, stock).iterator();
                    Pieza item = null;

                    while (iterator.hasNext()) {
                        item = iterator.next();
                %>
                <tr>
                    <td><%= item.getTipo()%></td>
                    <td><%= item.getCosto()%></td>
                    <td><%= item.getStock()%></td>
                    <td class="text-right">
                        <div class="dropdown">
                            <button class="btn btn-primary dropdown-toggle" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                Opciones
                            </button>
                            <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                                <a class="dropdown-item" href="PiezaController?accion=edit&id=<%= item.getId()%>">Editar</a>
                                <a class="dropdown-item" href="PiezaController?accion=delete&id=<%= item.getId()%>">Eliminar</a>                                
                                <a class="dropdown-item" href="PiezaController?accion=view&id=<%= item.getId()%>">Ver</a>
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

