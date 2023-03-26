/**
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Universidad Ean (Bogota - Colombia)
 * Desarrollo de Software
 * Proyecto Integrador
 *
 * Proyecto El Usado
 * Autor: Caroline Prada
 * Fecha: Marzo de 2023
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */

package Mundo;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Conexion {

    /**
     *  VARIABLES
     * Se crean las variables que contienen los datos que se requieren
     * para la conexión a la base de datos
     */
    public String url="jdbc:postgresql://localhost:5433/proyectoCar";
    public String user="postgres";
    public String pass="Andromeda12+";


    /**
     * Se crea un método de la clase principal vacía
     */
    public Conexion() {
    }


    /**
     *  MÉTODO
     * Se crea el método que se conecta a la base de datos
     */
    public Connection conectar(){
        Connection link=null;

        try {
            //Class.forName("org.postgresql.Driver");
            link=DriverManager.getConnection(this.url, this.user, this.pass);

        } catch (SQLException e) {
            JOptionPane.showConfirmDialog(null, e);

        }

        return link;
    }




}