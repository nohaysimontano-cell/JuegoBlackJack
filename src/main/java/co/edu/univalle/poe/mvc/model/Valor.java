package co.edu.univalle.poe.mvc.model;

public enum Valor {
    AS("As", 11),
    DOS("Dos", 2),
    TRES("Tres", 3),
    CUATRO("Cuatro", 4),
    CINCO("Cinco", 5),
    SEIS("Seis", 6),
    SIETE("Siete", 7),
    OCHO("Ocho", 8),
    NUEVE("Nueve", 9),
    DIEZ("Diez", 10),
    JOTA("J", 10),
    REINA("Q", 10),
    REY("K", 10);

    private final String etiqueta;
    private final int valorBase;

    Valor(String etiqueta, int valorBase) {
        this.etiqueta = etiqueta;
        this.valorBase = valorBase;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public int getValorBase() {
        return valorBase;
    }


    
}
