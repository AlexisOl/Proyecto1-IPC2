
<%@page import="dao.PiezaDao"%>
<%@page import="model.Pieza"%>
<%@page import="helper.Message"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Actualizar Pieza</title>
    </head>
    <body>
        <%@include file="../../template/menu_fabrica.jsp" %>
        <div class="container body">
            <h3>Pieza : Actualizar</h3>            
            <br>

            <%
                int id = Integer.parseInt(request.getParameter("id"));
                Pieza item = new PiezaDao().search(id);
            %>

            <% if (!String.valueOf(request.getParameter("error")).equals("null")) {%>
            <div class="alert alert-danger alert-dismissable">
                <button type="button" class="close" data-dismiss="alert">&times;</button>
                <strong>¡Error!</strong> No se actualizó la Pieza <br>
                <p><%= Message.VALUE%></p>
            </div> 
            <%}%>

            <form action="PiezaController" method="POST">
                <div class="form-group">                        
                    <div class="col-md-12">
                        <input class="form-control" type="number" name="id" value="<%= item.getId()%>" required="true" hidden="true"/>
                    </div>
                </div>
                <div class="form-group">                        
                    <div class="col-md-12">
                        <label>Tipo <b class="text-danger">*</b></label>
                        <input class="form-control" type="text" name="tipo" value="<%= item.getTipo()%>" required="true"/>
                    </div>
                </div>  
                <div class="form-group">                        
                    <div class="col-md-12">
                        <label>Costo <b class="text-danger">*</b></label>
                        <input class="form-control" type="number" name="costo" value="<%= item.getCosto()%>" step="0.01" min="0" required="true"/>
                    </div>
                </div> 
                <div class="form-group">                        
                    <div class="col-md-12">
                        <label>Stock <b class="text-danger">*</b></label>
                        <input class="form-control" type="number" value="<%= item.getStock()%>" min="0" name="stock" required="true"/>
                    </div>
                </div>  
                <div class="col-md-12">
                    <input class="btn btn-primary" type="submit" name="accion" value="Actualizar"/>
                    <a href="PiezaController?accion=list" class="btn btn-danger"> Cancelar</a>
                </div>
            </form>
        </div>
    </body>
</html>
