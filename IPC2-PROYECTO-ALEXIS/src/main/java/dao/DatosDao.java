/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

/**
 *
 * @author alexis
 */
import helper.Archivo;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import model.*;

public class DatosDao {

    UsuarioDao usuarioDao = new UsuarioDao();
    PiezaDao piezaDao = new PiezaDao();
    MuebleDao muebleDao = new MuebleDao();
    Ensambla_piezaDao ensambla_piezaDao = new Ensambla_piezaDao();
    Ensamblar_muebleDao ensamblar_muebleDao = new Ensamblar_muebleDao();
    ClienteDao clienteDao = new ClienteDao();

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public void crearUsuarios(ArrayList<String[]> data) {
        for (String[] row : data) {
            try {
                Usuario obj = new Usuario();
                obj.setUsername(row[0]);
                obj.setPassword(row[1]);
                obj.setTipo(Integer.parseInt(row[2]));
                obj.setEstado("Activo");
                usuarioDao.add(obj);
            } catch (Exception e) {
                System.out.println("exception crearUsuarios. " + e.getMessage());
            }
        }
    }

    public void crearPiezas(ArrayList<String[]> data) {
        for (String[] row : data) {
            try {
                Pieza obj = new Pieza();
                obj.setTipo(row[0]);
                obj.setCosto(Double.parseDouble(row[1]));
                obj.setStock(10);
                piezaDao.add(obj);
            } catch (Exception e) {
                System.out.println("exception crearPiezas. " + e.getMessage());
            }
        }
    }

    public void crearMuebles(ArrayList<String[]> data) {
        for (String[] row : data) {
            try {
                Mueble obj = new Mueble();
                obj.setNombre(row[0]);
                obj.setPrecio(Double.parseDouble(row[1]));
                muebleDao.add(obj);
            } catch (Exception e) {
                System.out.println("exception crearMuebles. " + e.getMessage());
            }
        }
    }

    public void crearEnsamblePieza(ArrayList<String[]> data) {
        for (String[] row : data) {
            try {
                Ensambla_pieza obj = new Ensambla_pieza();
                obj.setMueble(muebleDao.searchByNombre(row[0]));
                obj.setPieza(piezaDao.searchByTipo(row[1]));
                obj.setCantidad(Integer.parseInt(row[2]));
                ensambla_piezaDao.add(obj);
            } catch (Exception e) {
                System.out.println("exception crearEnsamblePieza. " + e.getMessage());
            }
        }
    }

    public void crearEnsamblarMueble(ArrayList<String[]> data) {
        for (String[] row : data) {
            try {
                Ensamblar_mueble obj = new Ensamblar_mueble();
                obj.setMueble(muebleDao.searchByNombre(row[0]));
                obj.setUsuario(usuarioDao.searchByUsername(row[1]));
                obj.setFecha(sdf.parse(row[2]));
                ensamblar_muebleDao.add(obj);
            } catch (ParseException e) {
                System.out.println("exception crearEnsamblarMueble. " + e.getMessage());
            }
        }
    }

    public void crearCliente(ArrayList<String[]> data) {
        for (String[] row : data) {
            try {
                Cliente obj = new Cliente();
                obj.setNombre(row[0]);
                obj.setNit(row[1]);
                obj.setDireccion(row[2]);
                if (row.length == 5) {
                    obj.setMunicipio(row[3]);
                    obj.setDepartamento(row[4]);
                }
                clienteDao.add(obj);
            } catch (Exception e) {
                System.out.println("exception crearCliente. " + e.getMessage());
            }
        }
    }

    public void cargarDatos(InputStream inputStream, String objeto) {
        ArrayList<String[]> data = Archivo.leer_archivo_texto(inputStream, objeto);
        switch (objeto) {
            case "USUARIO":
                crearUsuarios(data);
                break;
            case "PIEZA":
                crearPiezas(data);
                break;
            case "MUEBLE":
                crearMuebles(data);
                break;
            case "ENSAMBLE_PIEZAS":
                crearEnsamblePieza(data);
                break;
            case "ENSAMBLAR_MUEBLE":
                crearEnsamblarMueble(data);
                break;
            case "CLIENTE":
                crearCliente(data);
                break;
        }
    }
}
