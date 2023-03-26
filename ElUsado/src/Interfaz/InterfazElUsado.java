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


package Interfaz;

import Mundo.Conexion;
import Mundo.ListaCarro;
import Mundo.PrincipalLista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;


public class InterfazElUsado extends javax.swing.JFrame {

    private Conexion conex;
    private JPanel panelp;
    private JButton bbuscar;
    private JButton agregar;
    private JButton vender;
    private JButton lista;
    private JButton barato;
    private JButton bcilindraje;
    private JButton antiguo;
    private JButton salir;
    private JPanel panels;
    private JTextField placa;
    private JTextField anio;
    private JTextField modelo;
    private JTextField marca;
    private JTextField nejes;
    private JTextField cilindraje;
    private JTextField valor;
    private JLabel l1;
    private JLabel l2;
    private JLabel l3;
    private JLabel l4;
    private JLabel l5;
    private JLabel l6;
    private JLabel l7;
    private JComboBox tipov;
    private JLabel l8;
    private JPanel tabla;
    private JPanel panelr;
    private JTable table1;
    private JTextField buscar;
    private JLabel registro;
    private JButton nuevo;


    /**
     * Se inicializa la acción
     */
    private String accion="guardar";
    public InterfazElUsado() {
        //Inicializa
        //initComponents();
        mostrar("");
        inhabili();

        setContentPane(panelp);
        pack();

        /**
         * CONFIGURACIÓN DE LOS BOTONES
         * En esta sección se configura la acción de los botones
         * adicionalmente se invocan las acciones que se integran con la base de datos
         */


        /**
         * BOTÓN NUEVO
         * Este botón se habilitan los campos de registro y se declara la acción guardar
         */
        nuevo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                habili();
                agregar.setText("Guardar");
                accion="guardar"; //Acción declarada al inicio(l:52)

            }
        });


        /**
         * BOTÓN GUARDAR
         * Este botón valida que todos los campos del registro cuenten con información, en caso
         * de no tener, se genera un mensaje que indica que se deben validar todos los campos
         * Se instancia la clase principalLista, la cual contiene las acciones a aplicar en la base de datos
         * a partir de la línea 107 se empiezan a llenar los campos de la base de datos con los datos ingresados en
         * el registro
         */
        agregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Validar que las cajas de texto tenga información

                if (placa.getText().length()==0 || marca.getText().length()==0 || modelo.getText().length()==0 || anio.getText().length()==0 ||
                    nejes.getText().length()==0 || cilindraje.getText().length()==0 || valor.getText().length()==0){

                    JOptionPane.showConfirmDialog(rootPane, "Ingrese todos los datos");
                    placa.requestFocus();
                    return;
                }
                ListaCarro clist = new ListaCarro();
                PrincipalLista plist = new PrincipalLista();

                //Se empiezan a enviar datos
                clist.setPlaca(placa.getText());
                clist.setMarca(marca.getText());
                clist.setModelo(modelo.getText());
                clist.setAnio(Integer.parseInt(anio.getText()));
                clist.setNejes(Integer.parseInt(nejes.getText()));
                clist.setCilindraje(Integer.parseInt(cilindraje.getText()));
                clist.setValor(Double.valueOf(valor.getText()));

                int sl = tipov.getSelectedIndex();//Almacena el indice de la ButtonBox
                clist.setTipovehi((String) tipov.getItemAt(sl));

                //Se valida la acción a fin de saber si se debe guardar el registro o editar
                if (accion.equals("guardar")){
                    if (plist.insert(clist)){
                        JOptionPane.showMessageDialog(rootPane, "Registro ingresado");
                        mostrar("");
                        inhabili();
                    }
                } else if (accion.equals("editar")) {
                    clist.setPlaca(placa.getText());
                    if (plist.editar(clist)){
                        JOptionPane.showMessageDialog(rootPane, "Registro editado");
                        mostrar("");
                        inhabili();
                    }
                }


            }
        });


        /**
         * BOTÓN VENDER
         * Este botón activa una alerta en la cual se pide confirmar si se quiere vender
         * en caso de aceptar, se procede a activar la actividad de la base de datos
         * Nota: La acción vender genera un Delete del registro de la tabla
         */
        vender.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!placa.getText().equals("")){
                    int conf = JOptionPane.showConfirmDialog(rootPane, "Seguro quiere vender?","Confirmar",2);

                    //Confirmar Vender
                    if (conf ==0){
                        //Instanciar
                        ListaCarro clist = new ListaCarro();
                        PrincipalLista plist = new PrincipalLista();

                        clist.setPlaca(placa.getText());//Valor llave
                        plist.vender(clist);
                        mostrar("");
                        inhabili();
                        JOptionPane.showMessageDialog(null, "Gracias por tu compra");//Mensaje Final
                    }

                }
            }
        });

        /**
         * BOTÓN BUSCAR
         * Este botón busca la placa ingresada en el cuadro de entrada
         */
        bbuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrar(buscar.getText());

            }
        });


        /**
         * BOTÓN SALIR
         * Este botón activa la acción del sistema de salir
         */
        salir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });


        /**
         * BOTÓN LISTA
         * Este botón muestra todos los datos que se encuentran en la base de datos
         */
        lista.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lista();
            }
        });




        /**
         * CLIC DEL RATON
         * En este proceso al dar clic en un registro de la lista, este se encarga de
         * llenar el formulario con la información del registro seleccionado, permitiendo así
         * Vender (Activa el botón) o editar el registro
         */
        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                agregar.setText("Editar");//Cambio
                habili();
                vender.setEnabled(true);
                accion="editar";

                int fila = table1.rowAtPoint(e.getPoint());//Localiza la fila donde se ha dado clic
                placa.setText(table1.getValueAt(fila, 0).toString());
                marca.setText(table1.getValueAt(fila, 1).toString());
                modelo.setText(table1.getValueAt(fila, 2).toString());
                anio.setText(table1.getValueAt(fila, 3).toString());
                nejes.setText(table1.getValueAt(fila, 4).toString());
                cilindraje.setText(table1.getValueAt(fila, 5).toString());
                tipov.setSelectedItem(table1.getValueAt(fila, 6).toString());
                valor.setText(table1.getValueAt(fila, 7).toString());
            }
        });


        /**
         * BOTÓN + BARATO
         * Este botón cumple con la misma función del botón buscar, pero internamente
         * cuenta con la lógica que llama al vehículo de menor valor en toda la tabla
         */
        barato.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                barato();
            }
        });


        /**
         * BOTÓN + CILINDRAJE
         * Este botón cumple con la misma función del botón buscar, pero internamente
         * cuenta con la lógica que llama al vehículo con mayor cilindraje en toda la tabla
         */
        bcilindraje.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cilind();
            }
        });


        /**
         * BOTÓN + ANTIGUO
         * Este botón cumple con la misma función del botón buscar, pero internamente
         * cuenta con la lógica que llama al vehículo más antiguo en toda la tabla
         */
        antiguo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                antig();
            }
        });
    }



    /**
     * MÉTODO INHABILITAR
     * Este Método se encarga de inhabilitar todos los botones de formulario de registro
     */
    void inhabili(){

        //Cajas de texto inhabilitar
        placa.setEnabled(false);
        marca.setEnabled(false);
        modelo.setEnabled(false);
        anio.setEnabled(false);
        nejes.setEnabled(false);
        cilindraje.setEnabled(false);
        tipov.setEnabled(false);
        valor.setEnabled(false);


        //Cajas de texto sin datos
        placa.setText("");
        marca.setText("");
        modelo.setText("");
        anio.setText("");
        nejes.setText("");
        cilindraje.setText("");
        valor.setText("");


        //Botones
        agregar.setEnabled(false);
        vender.setEnabled(false);

    }


    /**
     * MÉTODO HABILITAR
     * Este Método se encarga de habilitar todos los botones de formulario de registro
     * y de inicializar todos los campos del registro
     */
    void habili(){

        //Cajas de texto Habilitar
        placa.setEnabled(true);
        marca.setEnabled(true);
        modelo.setEnabled(true);
        anio.setEnabled(true);
        nejes.setEnabled(true);
        cilindraje.setEnabled(true);
        tipov.setEnabled(true);
        valor.setEnabled(true);


        //Cajas de texto sin datos
        placa.setText("");
        marca.setText("");
        modelo.setText("");
        anio.setText("");
        nejes.setText("");
        cilindraje.setText("");
        valor.setText("");


        //Botones
        agregar.setEnabled(true);
        vender.setEnabled(true);

    }

    /**
     * PROCEDIMIENTO MOSTRAR
     * Este procedimiento instancia la clase PrincipalLista
     * la cual cuenta con el método mostrar el cual devuleve sólo la información del registro buscado
     */
    void mostrar(String buscar){

        try {
            DefaultTableModel modelo;
            //Instancia
            PrincipalLista plist = new PrincipalLista();
            modelo = plist.mostrar(buscar);

            table1.setModel(modelo);

        //En caso de falla se genera una excepción
        }catch (Exception e){
            JOptionPane.showConfirmDialog(null, e);
        }
    }


    /**
     * PROCEDIMIENTO LISTA
     * Este procedimiento instancia la clase PrincipalLista
     * la cual cuenta con el método lista que trae todos los datos de la base de datos
     */
    void lista(){
        try {
            DefaultTableModel modelo;
            //Instancia
            PrincipalLista plist = new PrincipalLista();
            modelo = plist.lista();

            table1.setModel(modelo);


        }catch (Exception e){
            JOptionPane.showConfirmDialog(null, e);
        }
    }

    /**
     * PROCEDIMIENTO + BARATO
     * Este procedimiento instancia la clase PrincipalLista
     * la cual cuenta con el método más barato
     */
    void barato(){
        try {
            DefaultTableModel modelo;
            //Instancia
            PrincipalLista plist = new PrincipalLista();
            modelo = plist.barato();

            table1.setModel(modelo);

        }catch (Exception e){
            JOptionPane.showConfirmDialog(null, e);
        }
    }


    /**
     * PROCEDIMIENTO + CILINDRAJE
     * Este procedimiento instancia la clase PrincipalLista
     * la cual cuenta con el método más cilindraje
     */
    void cilind(){
        try {
            DefaultTableModel modelo;
            //Instancia
            PrincipalLista plist = new PrincipalLista();
            modelo = plist.cilind();

            table1.setModel(modelo);

        }catch (Exception e){
            JOptionPane.showConfirmDialog(null, e);
        }
    }


    /**
     * PROCEDIMIENTO + ANTIGUO
     * Este procedimiento instancia la clase PrincipalLista
     * la cual cuenta con el método más Antiguo
     */
    void antig(){
        try {
            DefaultTableModel modelo;
            //Instancia
            PrincipalLista plist = new PrincipalLista();
            modelo = plist.antiguo();

            table1.setModel(modelo);

        }catch (Exception e){
            JOptionPane.showConfirmDialog(null, e);
        }
    }



    //Inicializa los componentes
    /**
     * MÉTODO
     * Este método inicializa todos los componentes de formulario creado
     */
    private void initComponents() {
        //Inicializan
        panelp = new JPanel();
        panelr = new JPanel();
        panels = new JPanel();
        l1 = new JLabel();
        l2 = new JLabel();
        l3 = new JLabel();
        l4 = new JLabel();
        l5 = new JLabel();
        l6 = new JLabel();
        l7 = new JLabel();
        l8 = new JLabel();
        registro = new JLabel();
        placa = new JTextField();
        marca = new JTextField();
        modelo = new JTextField();
        anio = new JTextField();
        nejes = new JTextField();
        cilindraje = new JTextField();
        valor = new JTextField();
        buscar = new JTextField();
        tipov = new JComboBox<>();
        agregar = new JButton();
        nuevo = new JButton();
        bbuscar = new JButton();
        bcilindraje = new JButton();
        barato = new JButton();
        antiguo = new JButton();
        salir = new JButton();
        lista = new JButton();
        vender = new JButton();
        table1 = new JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

    }

    /**
     * Procedimiento que activa todas las funciones de la clase InterfazElUsado
     */
    public static void main(String args[]) {

        new InterfazElUsado().setVisible(true);
        new InterfazElUsado().pack(); //Ajusta el formato de la interfaz

    }



}
