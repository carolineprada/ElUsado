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
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

public class PrincipalLista {

    /**
     * INSTANCIA Y VARIABLES
     * Se realiza la instancia de la clase conexión
     * se crea la variable que contendrá la cadena SQL
     */
    private Conexion postg = new Conexion();
    private Connection conx = postg.conectar();
    private String sSQL="";//Almacena la cadena de SQL

    /**
     * MÉTODOS QUE INTERACTUAN CON LA BASE DE DATOS Y LOS BOTONES DE LA INTERFAZ
     */

    /**
     * MÉTODO LISTA
     * Este proceso:
     * * Crea un array con los encabezados
     * * Crea un array vacio el cual almacenará los registros
     * *Se utiliza el DefaultTableModel el cual es estándar y el objeto
     * que se requiere para mostrar los datos en la tabla del formulario
     * NOTA: Este proceso recupera todos los datos de la base de datos
     */
    public DefaultTableModel lista(){

        DefaultTableModel modelo;
        //Almacenar los titulos de las columnas
        String [] encabezado = {"Placa", "Marca", "Modelo", "Año", "N. Ejes", "Cilindraje", "Tipo Vehiculo", "Valor"};

        //Almacena los registros
        String [] datos = new String[8];//Vacío con 8 indices

        modelo = new DefaultTableModel(null,encabezado);

        sSQL = "SELECT * FROM infocar ";//Instrucción SQL

        /**
         * PROCESO
         * En este proceso se recuperan datos de la base y se almacenan en el array de datos
         */
        //Excepción de errores
        try{

            Statement st = conx.createStatement();//Variable tipo Statement
            ResultSet rs = st.executeQuery(sSQL);//Ejecuta la sentencia SQL

            //Recorrer todos los registros
            while(rs.next()){
                datos[0] = rs.getString("placa");
                datos[1] = rs.getString("marca");
                datos[2] = rs.getString("modelo");
                datos[3] = rs.getString("anio");
                datos[4] = rs.getString("nejes");
                datos[5] = rs.getString("cilindraje");
                datos[6] = rs.getString("tipovehi");
                datos[7] = rs.getString("valor");

                modelo.addRow(datos);//Se adicionan los datos
            }
            return modelo;

        }catch (Exception e){
            JOptionPane.showConfirmDialog(null, e);//Si se presenta un error
            return null;
        }
    }


    /**
     * MÉTODO BUSCAR
     * Este proceso:
     * * Crea un array con los encabezados
     * * Crea un array vacio el cual almacenará los registros
     * *Se utiliza el DefaultTableModel el cual es estándar y el objeto
     * que se requiere para mostrar los datos en la tabla del formulario
     * NOTA: Este proceso recibe una variable, la cual es la placa a buscar y
     *       devuelve los datos de dicha placa.
     */
    public DefaultTableModel mostrar(String buscar){
        DefaultTableModel modelo;

        //Almacenar los titulos de las columnas
        String [] encabezado = {"Placa", "Marca", "Modelo", "Año", "N. Ejes", "Cilindraje", "Tipo Vehiculo", "Valor"};
        //Almacena los registros
        String [] datos = new String[8];//Vacío con 8 indices

        modelo = new DefaultTableModel(null, encabezado);

        sSQL = "SELECT * FROM infocar WHERE placa = '"+buscar+"'";//Instrucción SQL


        /**
         * PROCESO
         * En este proceso se recuperan datos de la base y se almacenan en el array de datos
         */
        //Excepción de errores
        try{

            Statement st = conx.createStatement();//Variable tipo Statement
            ResultSet rs = st.executeQuery(sSQL);//Ejecuta la sentencia SQL

            //Recorrer todos los registros
            while(rs.next()){
                datos[0] = rs.getString("placa");
                datos[1] = rs.getString("marca");
                datos[2] = rs.getString("modelo");
                datos[3] = rs.getString("anio");
                datos[4] = rs.getString("nejes");
                datos[5] = rs.getString("cilindraje");
                datos[6] = rs.getString("tipovehi");
                datos[7] = rs.getString("valor");

                modelo.addRow(datos);//Se adicionan los datos
            }
            return modelo;

        }catch (Exception e){
            JOptionPane.showConfirmDialog(null, e);//Si se presenta un error
            return null;
        }

    }


    /**
     * MÉTODO INSERTAR
     * En este proceso se crea la sentencia SQL la cual inserta datos en la tabla,
     * los datos los llena con los ocho (8) campos de la sección de registro de la interfaz
     */
    public boolean insert (ListaCarro dts){
        //SQL Cadena
        sSQL = "INSERT INTO infocar(placa, marca, modelo, anio, nejes, cilindraje, tipovehi, valor) "+
                "VALUES(?,?,?,?,?,?,?,?)";

        /**
         * PROCESO
         * En este proceso ingresa los datos a la base con los datos recibidos del formulario de registro
         */
        try {
                PreparedStatement pst = conx.prepareStatement(sSQL); //Preparar consulta
                //Se envian los valores a la tabla
                pst.setString(1, dts.getPlaca());
                pst.setString(2, dts.getMarca());
                pst.setString(3, dts.getModelo());
                pst.setInt(4, dts.getAnio());
                pst.setInt(5, dts.getNejes());
                pst.setInt(6, dts.getCilindraje());
                pst.setString(7, dts.getTipovehi());
                pst.setDouble(8, dts.getValor());


                int n=pst.executeUpdate(); //Almacena el resultado del Statement

                //Se valida si se insertaron los datos
                if (n!=0){
                    return true;
                }else {
                    return false;
                }

        }catch (Exception e){
            JOptionPane.showConfirmDialog(null, e); //Si se presenta un error
            return false;
        }
    }


    /**
     * MÉTODO EDITAR
     * En este proceso se crea la sentencia SQL la cual actualiza datos en la tabla,
     * los datos se actualizan de los 7 campos de la Interfaz, en este caso el campo
     * de la placa no se actualiza, sólo se utiliza como llave validadora
     */

    public boolean editar (ListaCarro dts){
        //Sentencia SQL
        sSQL = "UPDATE infocar SET marca=?, modelo=?, anio=?, nejes=?, cilindraje=?, tipovehi=?, valor=? WHERE placa=?";


        /**
         * PROCESO
         * En este proceso se actualizan los datos de la base de acuerdo a las modificaciones realizadas en el
         * formulario de registro
         */
        try {
                PreparedStatement pst = conx.prepareStatement(sSQL); //Preparar consulta
                pst.setString(1, dts.getMarca());
                pst.setString(2, dts.getModelo());
                pst.setInt(3, dts.getAnio());
                pst.setInt(4, dts.getNejes());
                pst.setInt(5, dts.getCilindraje());
                pst.setString(6, dts.getTipovehi());
                pst.setDouble(7, dts.getValor());
                pst.setString(8, dts.getPlaca());

                int n=pst.executeUpdate();//Almacena el resultado del Statement

                //Se valida si se insertaron los datos
                if (n!=0){
                    return true;
                }else {
                    return false;
                }

        }catch (Exception e){
            JOptionPane.showConfirmDialog(null, e);
            return false;
        }
    }


    /**
     * MÉTODO VENDER
     * En este proceso se crea la sentencia SQL la cual elimina datos en la tabla,
     * los datos se eliminan y la validación la realiza con la llave que es la placa
     */
    public boolean vender (ListaCarro dts){
        sSQL = "DELETE FROM infocar WHERE placa=?";//Sentencia SQL

        /**
         * PROCESO
         * En este proceso elimina los datos de la base de acuerdo con la placa recibida por parámetro
         */
        try {
                PreparedStatement pst = conx.prepareStatement(sSQL);//Preparar consulta
                pst.setString(1, dts.getPlaca());

                int n=pst.executeUpdate();//Almacena el resultado del Statement

                //Se valida si se insertaron los datos
                if (n!=0){
                    return true;
                }else {
                    return false;
                }

        }catch (Exception e){
            JOptionPane.showConfirmDialog(null, e);
            return false;
        }
    }


    /**
     * MÉTODO + BARATO
     * Este proceso:
     * * Crea un array con los encabezados
     * * Crea un array vacio el cual almacenará los registros
     * * Se utiliza el DefaultTableModel el cual es estándar y el objeto
     *   que se requiere para mostrar los datos en la tabla del formulario
     */
    public DefaultTableModel barato() {
        DefaultTableModel modelo;

        //Almacena el encabezado
        String[] encabezado = {"Placa", "Marca", "Modelo", "Año", "N. Ejes", "Cilindraje", "Tipo Vehiculo", "Valor"};
        //Almacena los registros
        String[] datos = new String[8];//Vacío con 8 indices

        modelo = new DefaultTableModel(null,encabezado);


        /**
         * INSTRUCCIÓN SQL
         * En esta instrucción se ordena de forma ascendente los registro por el valor y
         * se devuelve sólo el registro con menor valor
         */
        sSQL = "SELECT * FROM infocar ORDER BY valor LIMIT 1";//Instrucción SQL


        /**
         * PROCESO
         * En este proceso se recuperan datos de la base y se almacenan en el array de datos
         */
        //Excepción de errores
        try {

            Statement st = conx.createStatement();//Variable tipo Statement
            ResultSet rs = st.executeQuery(sSQL);//Ejecuta la sentencia SQL

            //Recorrer todos los registros
            while (rs.next()) {
                datos[0] = rs.getString("placa");
                datos[1] = rs.getString("marca");
                datos[2] = rs.getString("modelo");
                datos[3] = rs.getString("anio");
                datos[4] = rs.getString("nejes");
                datos[5] = rs.getString("cilindraje");
                datos[6] = rs.getString("tipovehi");
                datos[7] = rs.getString("valor");


                modelo.addRow(datos);//Se adicionan los datos
            }
            return modelo;

        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null, e);//Si se presenta un error
            return null;
        }
    }


    /**
     * MÉTODO + CILINDRAJE
     * Este proceso:
     * * Crea un array con los encabezados
     * * Crea un array vacio el cual almacenará los registros
     * * Se utiliza el DefaultTableModel el cual es estándar y el objeto
     *   que se requiere para mostrar los datos en la tabla del formulario
     */
    public DefaultTableModel cilind() {
        DefaultTableModel modelo;

        //Almacena el encabezado
        String[] encabezado = {"Placa", "Marca", "Modelo", "Año", "N. Ejes", "Cilindraje", "Tipo Vehiculo", "Valor"};
        //Almacena los registros
        String[] datos = new String[8];//Vacío con 8 indices

        modelo = new DefaultTableModel(null,encabezado);

        /**
         * INSTRUCCIÓN SQL
         * En esta instrucción se ordena de forma descendente los registro por el cilindraje y
         * se devuelve sólo el registro con el cilindraje más potente
         */
        sSQL = "SELECT * FROM infocar ORDER BY cilindraje DESC LIMIT 1";//Instrucción SQL

        /**
         * PROCESO
         * En este proceso se recuperan datos de la base y se almacenan en el array de datos
         */
        try {

            Statement st = conx.createStatement();//Variable tipo Statement
            ResultSet rs = st.executeQuery(sSQL);//Ejecuta la sentencia SQL

            //Recorrer todos los registros
            while (rs.next()) {
                datos[0] = rs.getString("placa");
                datos[1] = rs.getString("marca");
                datos[2] = rs.getString("modelo");
                datos[3] = rs.getString("anio");
                datos[4] = rs.getString("nejes");
                datos[5] = rs.getString("cilindraje");
                datos[6] = rs.getString("tipovehi");
                datos[7] = rs.getString("valor");


                modelo.addRow(datos);//Se adicionan los datos
            }
            return modelo;

        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null, e);//Si se presenta un error
            return null;
        }
    }


    /**
     * MÉTODO + ANTIGUO
     * Este proceso:
     * * Crea un array con los encabezados
     * * Crea un array vacio el cual almacenará los registros
     * * Se utiliza el DefaultTableModel el cual es estándar y el objeto
     *   que se requiere para mostrar los datos en la tabla del formulario
     */
    public DefaultTableModel antiguo() {
        DefaultTableModel modelo;

        //Almacena el encabezado
        String[] encabezado = {"Placa", "Marca", "Modelo", "Año", "N. Ejes", "Cilindraje", "Tipo Vehiculo", "Valor"};
        //Almacena los registros
        String[] datos = new String[8];//Vacío con 8 indices

        modelo = new DefaultTableModel(null,encabezado);


        /**
         * INSTRUCCIÓN SQL
         * En esta instrucción se ordena de forma ascendente los registro por el Año y
         * se devuelve sólo el registro con el año más antiguo
         */
        sSQL = "SELECT * FROM infocar ORDER BY anio LIMIT 1";//Instrucción SQL


        /**
         * PROCESO
         * En este proceso se recuperan datos de la base y se almacenan en el array de datos
         */
        try {

            Statement st = conx.createStatement();//Variable tipo Statement
            ResultSet rs = st.executeQuery(sSQL);//Ejecuta la sentencia SQL

            //Recorrer todos los registros
            while (rs.next()) {
                datos[0] = rs.getString("placa");
                datos[1] = rs.getString("marca");
                datos[2] = rs.getString("modelo");
                datos[3] = rs.getString("anio");
                datos[4] = rs.getString("nejes");
                datos[5] = rs.getString("cilindraje");
                datos[6] = rs.getString("tipovehi");
                datos[7] = rs.getString("valor");


                modelo.addRow(datos);//Se adicionan los datos
            }
            return modelo;

        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null, e);//Si se presenta un error
            return null;
        }
    }

}
