package co.edu.univalle.poe.mvc.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class JuegoTest {

    // Arma un mazo de 52 cartas: primero las que se piden, luego el resto sin repetir.
    private static Mazo mazoConPrimeras(Carta... primeras) {
        Carta[] todas = new Carta[52];
        int pos = 0;

        for (Carta c : primeras) {
            todas[pos++] = c;
        }
        for (Palo palo : Palo.values()) {
            for (Valor valor : Valor.values()) {
                if (!estaEn(primeras, palo, valor)) {
                    todas[pos++] = new Carta(palo, valor);
                }
            }
        }
        return new Mazo(todas);
    }

    private static boolean estaEn(Carta[] cartas, Palo palo, Valor valor) {
        for (Carta c : cartas) {
            if (c.getPalo() == palo && c.getValor() == valor) {
                return true;
            }
        }
        return false;
    }

    @Test
    void asNueveYCincoDejanQuinceYElJugadorContinua() {
        Mazo mazo = mazoConPrimeras(
                new Carta(Palo.PICAS, Valor.AS),
                new Carta(Palo.PICAS, Valor.NUEVE),
                new Carta(Palo.PICAS, Valor.CINCO));
        Juego juego = new Juego(mazo);

        juego.iniciarRonda("100");
        assertEquals(20, juego.getJugador().calcularPuntaje());
        assertEquals(EstadoJuego.TURNO_JUGADOR, juego.getEstado());

        juego.pedirCarta();

        assertEquals(15, juego.getJugador().calcularPuntaje());
        assertEquals(3, juego.getJugador().getMano().getCantidad());
        assertEquals(EstadoJuego.TURNO_JUGADOR, juego.getEstado());   // sigue su turno
        assertNull(juego.getResultado());                              // la ronda no terminó
    }

    @Test
    void siElJugadorSePasaPierdeYLaMaquinaNoJuega() {
        Mazo mazo = mazoConPrimeras(
                new Carta(Palo.PICAS, Valor.REY),
                new Carta(Palo.PICAS, Valor.NUEVE),
                new Carta(Palo.PICAS, Valor.CINCO));
        Juego juego = new Juego(mazo);

        juego.iniciarRonda("100");
        juego.pedirCarta();                                             // K + 9 + 5 = 24

        assertEquals(24, juego.getJugador().calcularPuntaje());
        assertEquals(Resultado.DERROTA, juego.getResultado());
        assertEquals(EstadoJuego.RONDA_TERMINADA, juego.getEstado());
        assertTrue(juego.getMaquina().getMano().estaVacia());           // no recibió cartas
        assertEquals(900, juego.getJugador().getSaldo());
    }
}