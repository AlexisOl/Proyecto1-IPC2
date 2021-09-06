/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helper;

/**
 *
 * @author alexis
 */
public class Definitions {

    public static String[] ROLES = {
        "Área de fábrica", "Área de punto de venta", "Área de administración"
    };
    public static String[] USUARIO_ESTADOS = {
        "Activo", "Cancelado"
    };
    public static String[] OBJETOS = {
        "USUARIO", "PIEZA", "MUEBLE", "ENSAMBLE_PIEZAS", "ENSAMBLAR_MUEBLE", "CLIENTE"
    };
    public static String[] MUEBLE_ESTADOS = {
        "Ensamblado", "En venta", "Devuelto", "Vendido"
    };
    public static String[] STOCK_ESTADOS = {
        "por agotarse/agotado", "suficiente"
    };
}
