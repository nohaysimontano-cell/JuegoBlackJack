package co.edu.univalle.poe.mvc.model;

public enum Palo {

    DIAMANTES("Diamantes"),
    CORAZONES("Corazones"),
    TREBOLES("Treboles"),
    PICAS("Picas");

    private final String nombre;

    Palo(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }
}
