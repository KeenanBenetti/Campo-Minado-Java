public class Celula {
    String Tipo;
    String Marcador;
    int NumBomProx;
    boolean Show;

    public Celula(String tipo, String marcador){
        this.Tipo = tipo;
        this.Marcador = marcador;
        this.NumBomProx = 0;
        this.Show = false;
    }

    public void setShow(boolean show) {
        Show = show;
    }

    public boolean isShow() {
        return Show;
    }

    public int getNumBomProx() {
        return NumBomProx;
    }

    public void setNumBomProx(int numBomProx) {
        NumBomProx = numBomProx;
    }

    public String getMarcador() {
        return Marcador;
    }

    public String getTipo() {
        return Tipo;
    }

    public void setTipo(String tipo) {
        Tipo = tipo;
    }

    public void setMarcador(String marcador) {
        Marcador = marcador;
    }
}
