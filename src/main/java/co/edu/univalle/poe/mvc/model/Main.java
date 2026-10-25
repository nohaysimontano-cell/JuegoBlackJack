package co.edu.univalle.poe.mvc.model;

public class Main {
    public static void main(String[] args) {
        Carta c = new Carta(Palo.PICAS, Valor.REY);
        System.out.println(c.getNombreImagen()); // K_PICAS.png
        System.out.println(c.getValorBase());    // 10
        System.out.println(c);                   // K de Picas
    }
}
