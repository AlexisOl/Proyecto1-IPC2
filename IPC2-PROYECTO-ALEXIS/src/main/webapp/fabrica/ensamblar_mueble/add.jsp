
<%@page import="dao.UsuarioDao"%>
<%@page import="model.Usuario"%>
<%@page import="java.util.Iterator"%>
<%@page import="dao.MuebleDao"%>
<%@page import="model.Mueble"%>
<%@page import="java.util.ArrayList"%>
<%@page import="helper.Message"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Agregar Ensamblar_mueble</title>
    </head>
    <body>
        <%@include file="../../template/menu_fabrica.jsp" %>
        <div class="container body">            
            <h3>Ensamblar_mueble : Crear</h3>            
            <br>

            <% if (!session.getAttribute("mensaje").toString().isEmpty()) {%>
            <div class="alert alert-danger alert-dismissable">
                <button type="button" class="close" data-dismiss="alert">&times;</button>
                <strong>¡Error!</strong> No se ensambló el mueble <br>
                <p><%= session.getAttribute("mensaje")%></p>
            </div> 
            <%}%>

            <form action="Ensamblar_muebleController" method="POST">
                <div class="form-group">                        
                    <div class="col-md-12">
                        <label>Mueble <b class="text-danger">*</b></label>
                        <select class="form-control" name="mueble_id" required="true">
                            <option value="">Seleccionar mueble...</option>
                            <%
                                Iterator<Mueble> listaMuebles = new MuebleDao().list("").iterator();
                                Mueble mueble = null;
                                while (listaMuebles.hasNext()) {
                                    mueble = listaMuebles.next();
                            %>
                            <option value="<%= mueble.getId()%>"><%= mueble%></option>
                            <%}%>
                        </select>
                    </div>
                </div>
                <div class="form-group">                        
                    <div class="col-md-12">
                        <label>Usuario <b class="text-danger">*</b></label>
                        <select class="form-control" name="usuario_id" required="true">
                            <option value="">Seleccionar usuario...</option>
                            <%
                                Iterator<Usuario> listaUsuarios = new UsuarioDao().list().iterator();
                                Usuario usuario = null;
                                while (listaUsuarios.hasNext()) {
                                    usuario = listaUsuarios.next();
                            %>
                            <option value="<%= usuario.getId()%>"><%= usuario%></option>
                            <%}%>
                        </select>
                    </div>
                </div>
                <div class="form-group">                        
                    <div class="col-md-12">
                        <label>Fecha de ensamblado <b class="text-danger">*</b></label>
                        <input class="form-control" type="date" name="fecha" required="true"/>
                    </div>
                </div> 
                <div class="col-md-12">
                    <input class="btn btn-primary" type="submit" name="accion" value="Ensamblar"/>
                    <a href="Ensamblar_muebleController?accion=list" class="btn btn-danger"> Cancelar</a>
                </div>
            </form>
        </div>
    </body>
</html>
