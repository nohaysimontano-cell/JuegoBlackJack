package co.edu.univalle.poe.mvc.model;

public class Jugador extends Participante {

    private static final int SALDO_INICIAL = 1000;
    private static final int APUESTA_MINIMA = 10;
    private int saldo =  SALDO_INICIAL;
    private int apuesta = 0;

    public int getSaldo() {
        return saldo;
    }

    public int getApuesta() {
        return apuesta;
    }

    public boolean puedeApostar() {
        return saldo >= APUESTA_MINIMA;

    }

    public int validarApuesta(String texto) {
        
        if (texto == null) {
            throw new IllegalArgumentException("¡INGRESE UN NÚMERO ENTERO!");
        }
        int numero;
        try {
            numero = Integer.parseInt(texto.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("¡INGRESE UN NÚMERO ENTERO!");
        }

        if (numero < APUESTA_MINIMA) {
            throw new IllegalArgumentException("¡ERROR! La apuesta minima es de 10 fichas");
        }

        if (numero % APUESTA_MINIMA != 0) {
            throw new IllegalArgumentException("¡ERROR! El valor ingresado debe ser divisible por 10");
        }

        if (numero > saldo) {
            throw new IllegalArgumentException("¡ERROR! El valor ingresado es mayor al saldo disponible");
        }

        return numero;


    }

    public void registrarApuesta(int monto) {
        if (monto < APUESTA_MINIMA) {
            throw new IllegalArgumentException("El monto es menor que la apuesta minima");
        }
        if (monto > saldo) {
            throw new IllegalArgumentException("El monto supera el saldo disponible");
        }
        apuesta = monto;
        saldo -= monto;
    }

    public void pagarApuesta(int monto) {
        saldo += monto;
    }

    public void vaciarApuesta() {
        apuesta = 0;
    }
}
