
<%@page import="dao.PiezaDao"%>
<%@page import="model.Pieza"%>
<%@page import="java.util.Iterator"%>
<%@page import="dao.MuebleDao"%>
<%@page import="dao.MuebleDao"%>
<%@page import="model.Mueble"%>
<%@page import="dao.Ensambla_piezaDao"%>
<%@page import="model.Ensambla_pieza"%>
<%@page import="helper.Message"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Actualizar Ensambla_pieza</title>
    </head>
    <body>
        <%@include file="../../template/menu_fabrica.jsp" %>
        <div class="container body">
            <h3>Ensambla_pieza : Actualizar</h3>            
            <br>

            <%
                int id = Integer.parseInt(request.getParameter("id"));
                Ensambla_pieza item = new Ensambla_piezaDao().search(id);
            %>

            <% if (!String.valueOf(request.getParameter("error")).equals("null")) {%>
            <div class="alert alert-danger alert-dismissable">
                <button type="button" class="close" data-dismiss="alert">&times;</button>
                <strong>¡Error!</strong> No se actualizó la Ensambla_pieza <br>
                <p><%= Message.VALUE%></p>
            </div> 
            <%}%>

            <form action="Ensambla_piezaController" method="POST">
                <div class="form-group">                        
                    <div class="col-md-12">
                        <input class="form-control" type="number" name="id" value="<%= item.getId()%>" required="true" hidden="true"/>
                    </div>
                </div>
                <div class="form-group">                        
                    <div class="col-md-12">
                        <label>Mueble <b class="text-danger">*</b></label>
                        <select class="form-control" name="mueble_id">
                            <option value="">Seleccionar mueble...</option>
                            <%
                                Iterator<Mueble> listaMuebles = new MuebleDao().list("").iterator();
                                Mueble mueble = null;
                                while (listaMuebles.hasNext()) {
                                    mueble = listaMuebles.next();
                                    if (mueble.getId() == item.getMueble().getId()) {
                            %>
                            <option value="<%= mueble.getId()%>" selected="true"><%= mueble%></option>
                            <%} else {%>
                            <option value="<%= mueble.getId()%>"><%= mueble%></option>
                            <%
                                    }
                                }
                            %>
                        </select>
                    </div>
                </div>
                <div class="form-group">                        
                    <div class="col-md-12">
                        <label>Pieza <b class="text-danger">*</b></label>
                        <select class="form-control" name="pieza_id">
                            <option value="">Seleccionar pieza...</option>
                            <%
                                Iterator<Pieza> listaPiezas = new PiezaDao().list("", "asc", "").iterator();
                                Pieza pieza = null;
                                while (listaPiezas.hasNext()) {
                                    pieza = listaPiezas.next();
                                    if (pieza.getId() == item.getPieza().getId()) {
                            %>
                            <option value="<%= pieza.getId()%>" selected="true"><%= pieza%></option>
                            <%} else {%>
                            <option value="<%= pieza.getId()%>"><%= pieza%></option>
                            <%
                                    }
                                }
                            %>
                        </select>
                    </div>
                </div>
                <div class="form-group">                        
                    <div class="col-md-12">
                        <label>Cantidad <b class="text-danger">*</b></label>
                        <input class="form-control" type="number" name="cantidad" value="<%= item.getCantidad()%>" min="0" required="true"/>
                    </div>
                </div>   
                <div class="col-md-12">
                    <input class="btn btn-primary" type="submit" name="accion" value="Actualizar"/>
                    <a href="Ensambla_piezaController?accion=list" class="btn btn-danger"> Cancelar</a>
                </div>
            </form>
        </div>
    </body>
</html>
