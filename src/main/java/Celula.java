public class Celula {
    String Tipo;
    String Marcador;

    public Celula(String tipo, String marcador){
        this.Tipo = tipo;
        this.Marcador = marcador;
    }

    public String getMarcador() {
        return Marcador;
    }

    public String getTipo() {
        return Tipo;
    }

    public void setMarcador(String marcador) {
        Marcador = marcador;
    }
}
