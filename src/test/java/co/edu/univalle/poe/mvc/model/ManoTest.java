package co.edu.univalle.poe.mvc.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ManoTest {

    // Arma una mano con los valores dados. El palo no cambia el puntaje.
    private static Mano crearMano(Valor... valores) {
        Mano mano = new Mano();
        for (Valor v : valores) {
            mano.agregarCarta(new Carta(Palo.PICAS, v));
        }
        return mano;
    }

    @Test
    void asYNueveValenVeinte() {
        Mano mano = crearMano(Valor.AS, Valor.NUEVE);

        assertEquals(20, mano.calcularPuntaje());
        assertFalse(mano.sePasa());
    }

    @Test
    void asNueveYCincoValenQuince() {
        Mano mano = crearMano(Valor.AS, Valor.NUEVE);
        assertEquals(20, mano.calcularPuntaje());     // antes del 5

        mano.agregarCarta(new Carta(Palo.CORAZONES, Valor.CINCO));

        assertEquals(15, mano.calcularPuntaje());     // después del 5: el as pasó a valer 1
        assertFalse(mano.sePasa());
    }

    @Test
    void variosAses() {
        assertEquals(21, crearMano(Valor.AS, Valor.AS, Valor.NUEVE).calcularPuntaje());
        assertEquals(12, crearMano(Valor.AS, Valor.AS, Valor.REY).calcularPuntaje());
        assertEquals(22, crearMano(Valor.AS, Valor.AS, Valor.REY, Valor.REY).calcularPuntaje());
        assertTrue(crearMano(Valor.AS, Valor.AS, Valor.REY, Valor.REY).sePasa());
    }

    @Test
    void jugadasEspeciales() {
        assertTrue(crearMano(Valor.AS, Valor.DIEZ).esBlackjack());
        assertFalse(crearMano(Valor.AS, Valor.CINCO, Valor.CINCO).esBlackjack());  // 21 con tres cartas
        assertTrue(crearMano(Valor.SIETE, Valor.SIETE, Valor.SIETE).esVeintiuno());
        assertTrue(crearMano(Valor.JOTA, Valor.CUATRO).esVeinteYMedio());
    }
}