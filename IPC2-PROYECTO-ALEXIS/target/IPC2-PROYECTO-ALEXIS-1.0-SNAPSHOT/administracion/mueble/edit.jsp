
<%@page import="dao.MuebleDao"%>
<%@page import="model.Mueble"%>
<%@page import="helper.Message"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Actualizar Mueble</title>
    </head>
    <body>
        <%@include file="../../template/menu_administracion.jsp" %>
        <div class="container body">
            <h3>Mueble : Actualizar</h3>            
            <br>

            <%
                int id = Integer.parseInt(request.getParameter("id"));
                Mueble item = new MuebleDao().search(id);
            %>

            <% if (!String.valueOf(request.getParameter("error")).equals("null")) {%>
            <div class="alert alert-danger alert-dismissable">
                <button type="button" class="close" data-dismiss="alert">&times;</button>
                <strong>¡Error!</strong> No se actualizó el Mueble <br>
                <p><%= Message.VALUE%></p>
            </div> 
            <%}%>

            <form action="MuebleController" method="POST">
                <div class="form-group">                        
                    <div class="col-md-12">
                        <input class="form-control" type="number" name="id" value="<%= item.getId()%>" required="true" hidden="true"/>
                    </div>
                </div>
                <div class="form-group">                        
                    <div class="col-md-12">
                        <label>Nombre <b class="text-danger">*</b></label>
                        <input class="form-control" type="text" name="nombre" value="<%= item.getNombre()%>" required="true"/>
                    </div>
                </div>  
                <div class="form-group">                        
                    <div class="col-md-12">
                        <label>Precio <b class="text-danger">*</b></label>
                        <input class="form-control" type="number" name="precio" value="<%= item.getPrecio()%>" step="0.01" min="0" required="true"/>
                    </div>
                </div>  
                <div class="col-md-12">
                    <input class="btn btn-primary" type="submit" name="accion" value="Actualizar"/>
                    <a href="MuebleController?accion=list" class="btn btn-danger"> Cancelar</a>
                </div>
            </form>
        </div>
    </body>
</html>