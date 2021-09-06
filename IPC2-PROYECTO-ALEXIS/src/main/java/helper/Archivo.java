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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Archivo {

    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");

    public static ArrayList<String[]> leer_archivo_texto(InputStream inputStream, String objeto) {
        ArrayList<String[]> data = new ArrayList<>();
        String linea;

        int inicio, fin;
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            while ((linea = br.readLine()) != null) {
                if (linea.startsWith(objeto + "(")) {
                    linea = linea.replaceAll('"' + "", "");
                    linea = linea.replaceAll(", ", ",");
                    inicio = linea.indexOf("(") + 1;
                    fin = linea.indexOf(")");
                    data.add(linea.substring(inicio, fin).split(","));
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return data;
    }
}
