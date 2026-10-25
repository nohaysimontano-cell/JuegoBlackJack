package co.edu.univalle.poe.mvc.model;

public class Carta {
    private final Palo palo;
    private final Valor valor;

    public Carta(Palo palo, Valor valor) {
        this.valor = valor;
        this.palo = palo;
    }

    public Palo getPalo() {
        return palo;
    }

    public Valor getValor() {
        return valor;
    }

    public int getValorBase() {
        return valor.getValorBase();
    }
    public String getNombreImagen() {
        return valor.getEtiqueta() + "_" + palo.name() + ".png";
    }

    @Override
    public String toString() {
        return valor.getEtiqueta() + "de" + palo.getNombre();
    }

}