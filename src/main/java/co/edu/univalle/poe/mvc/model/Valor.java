package co.edu.univalle.poe.mvc.model;

public enum Valor {
    AS("AS", 11),
    DOS("2", 2),
    TRES("3", 3),
    CUATRO("4", 4),
    CINCO("5", 5),
    SEIS("6", 6),
    SIETE("7", 7),
    OCHO("8", 8),
    NUEVE("9", 9),
    DIEZ("10", 10),
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
