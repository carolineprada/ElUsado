package Mundo;

//Clase que carga datos a la tabla
public class ListaCarro {

    //Se declaran los tipos de datos de la tabla
    private String placa;
    private String marca;
    private String modelo;
    private int anio;
    private int nejes;
    private int cilindraje;
    private String tipovehi;
    private Double valor;

    //METODO CONSTRUCTOR
    public ListaCarro(String placa, String marca, String modelo, int anio, int nejes, int cilindraje, String tipovehi, Double valor) {
        this.placa = placa;
        this.marca = marca;
        this.modelo = modelo;
        this.anio = anio;
        this.nejes = nejes;
        this.cilindraje = cilindraje;
        this.tipovehi = tipovehi;
        this.valor = valor;
    }

    public ListaCarro() { }

    //GET AND SET
    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public int getNejes() {
        return nejes;
    }

    public void setNejes(int nejes) {
        this.nejes = nejes;
    }

    public int getCilindraje() {
        return cilindraje;
    }

    public void setCilindraje(int cilindraje) {
        this.cilindraje = cilindraje;
    }

    public String getTipovehi() {
        return tipovehi;
    }

    public void setTipovehi(String tipovehi) {
        this.tipovehi = tipovehi;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }
}
