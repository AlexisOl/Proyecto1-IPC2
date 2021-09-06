<%@page import="helper.Definitions"%>
<%@page import="dao.UsuarioDao"%>
<%@page import="java.util.Iterator"%>
<%@page import="model.Usuario"%>
<%@page import="helper.Message"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cargar datos</title>
    </head>
    <body>
        <%@include file="../../template/menu_administracion.jsp" %>

        <%
            String objeto = "";
            if (!String.valueOf(request.getParameter("objeto")).equals("null")) {
                objeto = String.valueOf(request.getParameter("objeto")).trim();
            }
        %>

        <div class="container body">
            <h1>Datos</h1>

            <% if (!String.valueOf(request.getParameter("success")).equals("null")) {%>
            <div class="alert alert-success alert-dismissable">
                <button type="button" class="close" data-dismiss="alert">&times;</button>
                <strong>¡Éxito!</strong> <%=objeto%>(s) cargados correctamente
            </div> 
            <%}%>

            <% if (!String.valueOf(request.getParameter("error")).equals("null")) {%>
            <div class="alert alert-danger alert-dismissable">
                <button type="button" class="close" data-dismiss="alert">&times;</button>
                <strong>¡Error!</strong> No se cargaron los <%=objeto%>(s) <br>
                <p>Formato de archivo incorrecto. Solo se aceptan .txt</p>
            </div> 
            <%}%>

            <form action="DatosController" method="POST" enctype="multipart/form-data">
                <div class="form-group">                        
                    <div class="col-md-12">
                        <label>Archivo <b class="text-danger">*</b></label>
                        <input class="form-control" type="file" name="archivo" required="true"/>
                    </div>
                </div>  
                <div class="form-group">                        
                    <div class="col-md-12">
                        <label>Objeto <b class="text-danger">*</b></label>
                        <select class="form-control" name="objeto">
                            <option value="">Seleccionar objeto</option>                            
                            <%for (String obj : Definitions.OBJETOS) {%>
                            <option value="<%= obj%>"><%= obj%></option> 
                            <%}%>
                        </select>
                    </div>
                </div>
                <div class="col-md-12">
                    <input class="btn btn-primary" type="submit" name="accion" value="Cargar"/>
                </div>
            </form>
        </div>
    </body>
</html>

