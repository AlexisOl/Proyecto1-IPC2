<%-- 
    Document   : menu_administracion
    Created on : 28/08/2021, 20:13:29
    Author     : alexis
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
    <head>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" integrity="sha384-wvfXpqpZZVQGK6TAh5PVlGOfQNHSoD2xbE+QkPxCAFlNEevoEH3Sl0sibVcOQVnN" crossorigin="anonymous">
    </head>
    <body>
        <%HttpSession s = request.getSession();%>
        <nav class="navbar navbar-expand-lg navbar-light" style="background-color:#2E2E2E;">
            <a class="navbar-brand text-white col-md-2" href="#"><h3>ADMINISTRACION</h3></a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>

            <div class="collapse navbar-collapse " id="navbarSupportedContent">
                <ul class="navbar-nav mr-auto">
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle text-white" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            <%= s.getAttribute("user")%>
                        </a>
                        <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                            <a class="dropdown-item" href="UsuarioController?accion=logout">Cerrar Sesión</a>                          
                        </div>
                    </li>
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle text-white" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            Usuarios
                        </a>
                        <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                            <a class="dropdown-item" href="UsuarioController?accion=list">Listar Usuarios</a>
                            <div class="dropdown-divider"></div>
                            <a class="dropdown-item" href="UsuarioController?accion=add">Agregar Usuario</a>                            
                        </div>
                    </li>
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle text-white" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            Muebles
                        </a>
                        <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                            <a class="dropdown-item" href="MuebleController?accion=list">Listar Muebles</a>
                            <div class="dropdown-divider"></div>
                            <a class="dropdown-item" href="MuebleController?accion=add">Agregar Mueble</a>                            
                        </div>
                    </li>
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle text-white" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            Reportes
                        </a>
                        <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                            <a class="dropdown-item" href="ReporteController?accion=reporte_venta">Reporte de ventas</a>  
                            <a class="dropdown-item" href="ReporteController?accion=reporte_devolucion">Reporte de devoluciones</a>
                            <a class="dropdown-item" href="ReporteController?accion=reporte_ganancia">Reporte de ganancia</a>
                            <a class="dropdown-item" href="ReporteController?accion=reporte_max_venta_usuario">Reporte de vendedor con más ventas</a>
                            <a class="dropdown-item" href="ReporteController?accion=reporte_max_ganancia_usuario">Reporte de vendedor con más ganancia</a>
                            <a class="dropdown-item" href="ReporteController?accion=reporte_max_venta_mueble">Reporte de mueble más vendido</a>
                            <a class="dropdown-item" href="ReporteController?accion=reporte_min_venta_mueble">Reporte de mueble menos vendido</a>
                        </div>
                    </li>
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle text-white" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            Cargar datos
                        </a>
                        <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                            <a class="dropdown-item" href="DatosController?accion=data">Cargar</a>                           
                        </div>
                    </li>
                </ul>
            </div>
        </nav>
        <script src="https://code.jquery.com/jquery-3.5.1.min.js" integrity="sha256-9/aliU8dGd2tb6OSsuzixeV4y/faTqgFtohetphbbj0=" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.11.0/umd/popper.min.js" integrity="sha384-b/U6ypiBEHpOf/4+1nzFpr53nxSS+GLCkfwBdFNTxtclqqenISfwAzpKaMNFNmj4" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js" integrity="sha384-B4gt1jrGC7Jh4AgTPSdUtOBvfO8shuf57BaghqFfPlYxofvL8/KUEfYiJOMMV+rV" crossorigin="anonymous"></script>

    </body>
</html>
