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

    public String valorBase() {
        return valor + " _ " + palo.name();
    }

}