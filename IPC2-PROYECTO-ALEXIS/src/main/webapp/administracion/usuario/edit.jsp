<%@page import="helper.Definitions"%>
<%@page import="dao.UsuarioDao"%>
<%@page import="model.Usuario"%>
<%@page import="helper.Message"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Actualizar Categoría</title>
    </head>
    <body>
        <%@include file="../../template/menu_administracion.jsp" %>
        <div class="container body">
            <h3>Categoría : Actualizar</h3>            
            <br>

            <%
                int id = Integer.parseInt(request.getParameter("id"));
                Usuario item = new UsuarioDao().search(id);
            %>

            <% if (!String.valueOf(request.getParameter("error")).equals("null")) {%>
            <div class="alert alert-danger alert-dismissable">
                <button type="button" class="close" data-dismiss="alert">&times;</button>
                <strong>¡Error!</strong> No se actualizó el Usuario <br>
                <p><%= Message.VALUE%></p>
            </div> 
            <%}%>

            <form action="UsuarioController" method="POST">
                <div class="form-group">                        
                    <div class="col-md-12">
                        <input class="form-control" type="number" name="id" value="<%= item.getId()%>" required="true" hidden="true"/>
                    </div>
                </div>
                <div class="form-group">                        
                    <div class="col-md-12">
                        <label>Username <b class="text-danger">*</b></label>
                        <input class="form-control" type="text" name="username" value="<%= item.getUsername()%>" required="true"/>
                    </div>
                </div>  
                <div class="form-group">                        
                    <div class="col-md-12">
                        <label>Password <b class="text-danger">*</b></label>
                        <input class="form-control" type="password" name="password" value="<%= item.getPassword()%>" required="true"/>
                    </div>
                </div> 
                <div class="form-group">                        
                    <div class="col-md-12">
                        <label>Tipo <b class="text-danger">*</b></label>
                        <select class="form-control" name="tipo" required="true">
                            <option value="">Seleccionar tipo</option>
                            <%
                                for (int i = 0; i < Definitions.ROLES.length; i++) {
                                    if (item.getTipo() == (i + 1)) {
                            %>
                            <option value="<%= (i + 1)%>" selected="true"><%= Definitions.ROLES[i]%></option>
                            <% } else {%>
                            <option value="<%= (i + 1)%>"><%= Definitions.ROLES[i]%></option>
                            <%
                                    }
                                }
                            %>
                        </select>
                    </div>
                </div> 
                <div class="form-group">                        
                    <div class="col-md-12">
                        <label>Estado <b class="text-danger">*</b></label>
                        <select class="form-control" name="estado" required="true">
                            <option value="">Seleccionar estado</option>
                            <%
                                for (String estado : Definitions.USUARIO_ESTADOS) {
                                    if (item.getEstado().equals(estado)) {
                            %>
                            <option value="<%= estado%>" selected="true"><%= estado%></option>
                            <% } else {%>
                            <option value="<%= estado%>"><%= estado%></option>
                            <%
                                    }
                                }
                            %>
                        </select>
                    </div>
                </div> 
                <div class="col-md-12">
                    <input class="btn btn-primary" type="submit" name="accion" value="Actualizar"/>
                    <a href="UsuarioController?accion=list" class="btn btn-danger"> Cancelar</a>
                </div>
            </form>
        </div>
    </body>
</html>