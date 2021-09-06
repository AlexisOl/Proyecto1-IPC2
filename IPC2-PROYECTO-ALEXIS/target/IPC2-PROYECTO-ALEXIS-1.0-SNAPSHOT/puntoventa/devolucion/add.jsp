
<%@page import="helper.Message"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Agregar Devolución</title>
    </head>
    <body>
        <%@include file="../../template/menu_venta.jsp" %>
        <div class="container body">            
            <h3>Devolución : Crear</h3>            
            <br>

            <% if (!session.getAttribute("mensaje").toString().isEmpty()) {%>
            <div class="alert alert-danger alert-dismissable">
                <button type="button" class="close" data-dismiss="alert">&times;</button>
                <strong>¡Error!</strong> <%=session.getAttribute("mensaje")%> <br>
            </div> 
            <%}%>

            <form action="DevolucionController" method="POST">
                <div class="form-group">                        
                    <div class="col-md-12">
                        <label>Código factura <b class="text-danger">*</b></label>
                        <input class="form-control" type="text" name="codigo" required="true"/>
                    </div>
                </div>  
                <div class="form-group">                        
                    <div class="col-md-12">
                        <label>Identificador de producto <b class="text-danger">*</b></label>
                        <input class="form-control" type="text" name="identificador" required="true"/>
                    </div>
                </div> 
                <div class="form-group">                        
                    <div class="col-md-12">
                        <label>Fecha de devolución <b class="text-danger">*</b></label>
                        <input class="form-control" type="date" name="fecha_devolucion" required="true"/>
                    </div>
                </div>
                <div class="col-md-12">
                    <input class="btn btn-primary" type="submit" name="accion" value="Devolver"/>
                    <a href="DevolucionController?accion=list" class="btn btn-danger"> Cancelar</a>
                </div>
            </form>
        </div>
    </body>
</html>
