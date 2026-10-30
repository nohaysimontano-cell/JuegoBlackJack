package co.edu.univalle.poe.mvc.model;

public class Juego {
    private Mazo mazo;
    private final Jugador jugador = new Jugador();
    private final Maquina maquina = new Maquina();
    private EstadoJuego estado = EstadoJuego.ESPERANDO_APUESTA;
    private Resultado resultado;
    private String mensaje = "Ingresa una apuesta para comenzar. ";
    private Mazo mazoPruebas;

    public Juego() {
    }

    public Juego(Mazo mazoPruebas) {
        this.mazoPruebas = mazoPruebas;
    }
    //Vista--------------------------------
    public EstadoJuego getEstado() {
        return estado;
    }

    public Resultado getResultado() {
        return resultado;
    }

    public String getMensaje() {
        return mensaje;
    }

    public Jugador getJugador() {
        return jugador;
    }

    public Maquina getMaquina() {
        return maquina;
    }

    public boolean puedeSeguirJuagndo() {
        return jugador.puedeApostar();
    }

    //acciones del usuario----------------------------

    public void iniciarRonda(String textoApuesta) {
        if (estado == EstadoJuego.TURNO_JUGADOR || estado == EstadoJuego.TURNO_MAQUINA) {
            throw new IllegalStateException("Ya hay una ronda en curso.");
        }

        if (!jugador.puedeApostar()) {
            throw new IllegalStateException("No hay fichas suficientes para otra ronda. ");
        }

        int monto = jugador.validarApuesta(textoApuesta);

        if (mazoPruebas != null) {
            mazo = mazoPruebas;
            mazoPruebas = null;
        } else {
            mazo = new Mazo();
        }

        jugador.getMano().vaciar();
        maquina.getMano().vaciar();
        resultado = null;

        jugador.registrarApuesta(monto);

        repartirAlJugador();
        repartirAlJugador();

        if (jugador.getMano().esBlackjack() || jugador.calcularPuntaje() == 21) {
            turnoMaquina();
        } else {
            estado = EstadoJuego.TURNO_JUGADOR;
            if (jugador.getMano().esVeinteYMedio()) {
                mensaje = "¡Veinte y medio! puedes pedir carta o plantarte:";
            } else {
                mensaje = "Tu turno: pide carta o plantate";
            }
        }
    }

    public void pedirCarta() {
        if (estado != EstadoJuego.TURNO_JUGADOR) {
            throw new IllegalStateException("No es el turno del jugador.");
        }

        repartirAlJugador();

        if (jugador.getMano().sePasa()) {
            terminarRonda(Resultado.DERROTA, "Te pasaste de 21. Pierdes la ronda.");

        } else if (jugador.calcularPuntaje() == 21 ) {
            turnoMaquina();
        } else {
            mensaje = "Tu turno: pide carta o plantate.";
        }
        
    }

    public void plantarse() {
        if (estado != EstadoJuego.TURNO_JUGADOR) {
            throw new IllegalStateException("No es el turnp del jugador.");
        }
        turnoMaquina();
    }

    private void repartirAlJugador() {
        jugador.getMano().agregarCarta(mazo.repartirCarta());
    }

    private void repartirAlaMaquina() {
        maquina.getMano().agregarCarta(mazo.repartirCarta());
    }

    private void turnoMaquina() {
        estado = EstadoJuego.TURNO_MAQUINA;

        repartirAlaMaquina();
        repartirAlaMaquina();

        if (!jugador.getMano().esBlackjack()) {
            while (maquina.debePedirCarta()){
                repartirAlaMaquina();
            }
        }

        determinarResultado();
    }

    
    private void determinarResultado() {
        Mano manoJ = jugador.getMano();
        Mano manoM = maquina.getMano();


        if (manoJ.sePasa()) {
            terminarRonda(Resultado.DERROTA, "Te pasaste de 21. Pierdes la ronda.");
            return;
        }
        if (manoM.sePasa()) {
            terminarRonda(Resultado.VICTORIA, "La maquina se paso de 21. ¡Ganaste!");
            return;
        }

        int nivelJ = nivel(manoJ);
        int nivelM = nivel(manoM);

        if (nivelJ > nivelM) {
            if (manoJ.esBlackjack()) {
                terminarRonda(Resultado.VICTORIA_BLACJACK, "¡BlackJack! Ganas la ronda.");
            } else {
                terminarRonda(Resultado.VICTORIA, "Tu mano supera a la de la maquina. ¡Ganas!");
            }
        } else if (nivelM > nivelJ) {
            if (manoM.esBlackjack()) {
                terminarRonda(Resultado.DERROTA,"La maquina tiene BlackJack. Pierdes.");
            } else {
                terminarRonda(Resultado.DERROTA, "La mano de la maquina supera a la tuya. Pierdes.");

            }
        } else if (nivelJ == 1 ) {
            int puntajeJ = manoJ.calcularPuntaje();
            int puntajeM = manoM.calcularPuntaje();
            if (puntajeJ > puntajeM) {
                terminarRonda(Resultado.VICTORIA, "Tu puntaje es mayor. ¡Ganas!");
            } else if (puntajeM > puntajeJ ) {
                terminarRonda(Resultado.DERROTA, "Tu puntaje es menor al de la maquina. ¡Pierdes!");
            } else {
                terminarRonda(Resultado.EMPATE, "Mismo puntaje. Empate: se devuelve tu apuesta.");
            }
        } else {
            terminarRonda(Resultado.EMPATE, "Ambos tinen la misma jugada. Empate: se devuelve tu apuesta.");
        }
    }

    private int nivel(Mano mano) {
        if (mano.esBlackjack()) {
            return 4;
        }

        if (mano.esVeintiuno()) {
            return 3;
        }

        if (mano.esVeinteYMedio()) {
            return 2;
        }
        return 1;
    }

    private void terminarRonda(Resultado r, String textoMensaje) {
        resultado = r;
        mensaje = textoMensaje;

        int apuesta = jugador.getApuesta();
        int pago;
        switch (r) {
            case DERROTA:
                pago = 0;
                break;
            case EMPATE:
                pago = apuesta;
                break;
            case VICTORIA:
                pago = apuesta * 2;
                break;
            case VICTORIA_BLACJACK:
                pago = apuesta * 5 / 2;
                break;
            default:
                throw new IllegalArgumentException("Resultado no soportado: " + r);
        }
        jugador.pagarApuesta(pago);

        estado = EstadoJuego.RONDA_TERMINADA;
    } 
}

