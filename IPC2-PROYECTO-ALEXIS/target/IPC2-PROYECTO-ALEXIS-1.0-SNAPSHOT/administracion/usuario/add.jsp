<%@page import="helper.Definitions"%>
<%@page import="helper.Message"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Agregar Usuario</title>
    </head>
    <body>
        <%@include file="../../template/menu_administracion.jsp" %>
        <div class="container body">            
            <h3>Usuario : Crear</h3>            
            <br>

            <% if (!String.valueOf(request.getParameter("error")).equals("null")) {%>
            <div class="alert alert-danger alert-dismissable">
                <button type="button" class="close" data-dismiss="alert">&times;</button>
                <strong>¡Error!</strong> No se agregó el Usuario <br>
                <p><%= Message.VALUE%></p>
            </div> 
            <%}%>

            <form action="UsuarioController" method="POST">
                <div class="form-group">                        
                    <div class="col-md-12">
                        <label>Username <b class="text-danger">*</b></label>
                        <input class="form-control" type="text" name="username" required="true"/>
                    </div>
                </div>  
                <div class="form-group">                        
                    <div class="col-md-12">
                        <label>Password <b class="text-danger">*</b></label>
                        <input class="form-control" type="password" name="password" required="true"/>
                    </div>
                </div> 
                <div class="form-group">                        
                    <div class="col-md-12">
                        <label>Tipo <b class="text-danger">*</b></label>
                        <select class="form-control" name="tipo" required="true">
                            <option value="">Seleccionar tipo</option>
                            <%for (int i = 0; i < Definitions.ROLES.length; i++) {%>
                            <option value="<%= (i + 1)%>"><%= Definitions.ROLES[i]%></option>
                            <%}%>
                        </select>
                    </div>
                </div> 
                <div class="col-md-12">
                    <input class="btn btn-primary" type="submit" name="accion" value="Registrar"/>
                    <a href="UsuarioController?accion=list" class="btn btn-danger"> Cancelar</a>
                </div>
            </form>
        </div>
    </body>
</html>

