package co.edu.univalle.poe.mvc.model;

import java.util.Random;

public class Mazo {
    private static final int TOTAL_CARTAS = 52;


    private Carta[] cartas;
    private int siguienteCarta;
    private final Random random = new Random();

    public Mazo() {
        cartas = new Carta[TOTAL_CARTAS];
        int posicion = 0;

        for (Palo palo : Palo.values()) {
            for (Valor valor : Valor.values()) {
                cartas[posicion] = new  Carta(palo, valor);
                posicion++;
            }
        }
        barajar();
    }

    public void barajar() {
        for (int i = cartas.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            Carta temp = cartas[i];
            cartas[i] = cartas[j];
            cartas[j] = temp;
        }
        siguienteCarta = 0;
    } 

    public Mazo(Carta[] cartasOrdenadas) {
        if (cartasOrdenadas == null || cartasOrdenadas.length != TOTAL_CARTAS) {
            throw new IllegalArgumentException("El mazo debe tener exactamente 52 cartas");
        }
        this.cartas = cartasOrdenadas;
        this.siguienteCarta = 0;
    }

    public Carta repartirCarta() {
        if (siguienteCarta >=cartas.length) {
            throw new IllegalStateException("No quedan cartas en el mazo.");
        }
        Carta carta = cartas[siguienteCarta];
        siguienteCarta++;
        return carta;
    }

    public int cartasRestantes() {
        return cartas.length - siguienteCarta;
    }

}
