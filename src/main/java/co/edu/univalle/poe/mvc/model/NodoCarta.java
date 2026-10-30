package co.edu.univalle.poe.mvc.model;

public class NodoCarta {
    private final Carta carta;
    private NodoCarta siguiente;

    public NodoCarta(Carta carta) {
        this.carta = carta;
        this.siguiente = null;
    
    }

    public Carta getCarta() {
        return carta;
    
    }

    public NodoCarta getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(NodoCarta siguiente) {
        this.siguiente = siguiente;
    }
}
