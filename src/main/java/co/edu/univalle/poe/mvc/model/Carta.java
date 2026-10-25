package co.edu.univalle.poe.mvc.model;

public class Carta {
    private final String palo;
    private final int valor;

    public Carta(String palo, int valor) {
        this.valor = valor;
        this.palo = palo;
    }

    public String getPalo() {
        return palo;
    }

    public int getValor() {
        return valor;
    }

    public String valorBase() {
        return valor + " _ " + palo.name();
    }

}