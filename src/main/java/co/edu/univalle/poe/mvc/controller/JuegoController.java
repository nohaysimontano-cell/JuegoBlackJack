package co.edu.univalle.poe.mvc.controller;

import co.edu.univalle.poe.mvc.model.Juego;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.control.TextField;
import co.edu.univalle.poe.mvc.model.EstadoJuego;
import co.edu.univalle.poe.mvc.model.Jugador;
import co.edu.univalle.poe.mvc.model.Maquina;
import javafx.scene.control.Alert;
import co.edu.univalle.poe.mvc.model.Carta;
import co.edu.univalle.poe.mvc.model.Mano;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.Optional;
import javafx.application.Platform;
import javafx.scene.control.ButtonType;

public class JuegoController {
    @FXML 
    private Label lblMensaje;
    @FXML 
    private Label lblPuntajeMaquina;
    @FXML 
    private Label lblPuntajeJugador;
    @FXML
    private Label lblSaldo;
    @FXML
    private Label lblApuesta;
    @FXML 
    private HBox boxCartasMaquina;
    @FXML 
    private HBox boxCartasJugador;
    @FXML 
    private TextField txtApuesta;
    @FXML 
    private Button btnIniciar;
    @FXML 
    private Button btnPedir;
    @FXML 
    private Button btnPlantarse;
    @FXML 
    private Button btnReglas;
    @FXML 
    private Button btnCerrar;

    private final Juego juego = new Juego();


    @FXML
    private void initialize() {
        actualizarVista();
    }

    private void actualizarVista() {
    EstadoJuego estado = juego.getEstado();
    Jugador jugador = juego.getJugador();
    Maquina maquina = juego.getMaquina();

    // Mensaje
    String mensaje = juego.getMensaje();
    if (estado == EstadoJuego.RONDA_TERMINADA && jugador.getSaldo() <= 0) {
        mensaje += " No tienes fichas suficientes para otra ronda.";
    }
    lblMensaje.setText(mensaje);

    // Saldo y apuesta vigente
    lblSaldo.setText("Saldo: " + jugador.getSaldo());
    if (estado == EstadoJuego.ESPERANDO_APUESTA) {
        lblApuesta.setText("Apuesta vigente: -");
    } else {
        lblApuesta.setText("Apuesta vigente: " + jugador.getApuesta());
    }

    // Puntajes (la máquina muestra un guion mientras no tenga cartas)
    if (jugador.getMano().estaVacia()) {
        lblPuntajeJugador.setText("Puntaje: -");
    } else {
        lblPuntajeJugador.setText("Puntaje: " + jugador.calcularPuntaje());
    }
    if (maquina.getMano().estaVacia()) {
        lblPuntajeMaquina.setText("Puntaje: -");
    } else {
        lblPuntajeMaquina.setText("Puntaje: " + maquina.calcularPuntaje());
    }

    // Botones según el estado
    boolean puedeIniciar = (estado == EstadoJuego.ESPERANDO_APUESTA
            || estado == EstadoJuego.RONDA_TERMINADA) && jugador.getSaldo() > 0;
    boolean turnoJugador = estado == EstadoJuego.TURNO_JUGADOR;

    txtApuesta.setDisable(!puedeIniciar);
    btnIniciar.setDisable(!puedeIniciar);
    btnPedir.setDisable(!turnoJugador);
    btnPlantarse.setDisable(!turnoJugador);
    // btnReglas y btnCerrar siempre quedan habilitados

    // Cartas
    mostrarMano(boxCartasJugador, jugador.getMano());
    mostrarMano(boxCartasMaquina, maquina.getMano());
    }

    @FXML
    private void iniciarRonda() {
        try {
            juego.iniciarRonda(txtApuesta.getText());
            txtApuesta.clear();      // solo se ejecuta si la ronda inició bien
        } catch (IllegalArgumentException | IllegalStateException e) {
            mostrarError(e.getMessage());
        }
        actualizarVista();
    }

    @FXML
    private void pedirCarta() {
        try {
            juego.pedirCarta();
        } catch (IllegalStateException e) {
            mostrarError(e.getMessage());
        }
        actualizarVista();
    }

    @FXML
    private void plantarse() {
        try {
            juego.plantarse();
        } catch (IllegalStateException e) {
            mostrarError(e.getMessage());
        }
        actualizarVista();
    }

    private void mostrarError(String texto) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Error");
        alerta.setHeaderText(null);
        alerta.setContentText(texto);
        alerta.showAndWait();
    }

    private void mostrarMano(HBox contenedor, Mano mano) {
        contenedor.getChildren().clear();

        for (Carta carta : mano.obtenerCartas()) {
            String ruta = "/co/edu/univalle/poe/mvc/images/" + carta.getNombreImagen();
            Image imagen = new Image(getClass().getResourceAsStream(ruta));

            ImageView vista = new ImageView(imagen);
            vista.setFitWidth(100);
            vista.setPreserveRatio(true);

            contenedor.getChildren().add(vista);
        }

    }

    @FXML
    private void verReglas() {
        Alert info = new Alert(Alert.AlertType.INFORMATION);
        info.setTitle("Reglas");
        info.setHeaderText("Reglas del Blackjack");
        info.setResizable(true);
        info.setContentText("""
                OBJETIVO
                Obtener un puntaje mayor que el computador sin superar 21.

                VALORES
                - Del 2 al 10: su valor numérico.
                - J, Q y K: 10 puntos.
                - As: 1 u 11, el que dé el mayor puntaje sin pasarse de 21.

                APUESTAS
                - Empiezas con 1000 fichas.
                - La apuesta debe ser un número entero, múltiplo de 10, de al menos 10 fichas y sin superar tu saldo.
                - La apuesta se descuenta al iniciar la ronda y no puede cambiarse durante ella.

                PAGOS (fichas que se acreditan al terminar)
                - Derrota: ninguna.
                - Empate: se devuelve la apuesta.
                - Victoria: se devuelve la apuesta y ganas una cantidad igual (2 veces la apuesta).
                - Victoria con Blackjack: se devuelve la apuesta y ganas 1,5 veces su valor (2,5 veces la apuesta).

                JUGADAS
                - Blackjack: un As y una carta de valor 10 en las dos primeras cartas.
                - Veintiuno: 21 puntos sin ser Blackjack.
                - Veinte y medio: las dos primeras cartas suman 14. Puedes pedir o plantarte; no da pago adicional.
                - Pasarse: más de 21 puntos. Pierdes de inmediato.

                PRIORIDAD AL COMPARAR
                Blackjack > Veintiuno > Veinte y medio > mayor puntaje.
                Dos jugadas iguales de nivel especial empatan.

                COMPUTADOR
                - Recibe dos cartas cuando tú te plantas (o llegas a 21).
                - Pide carta mientras su puntaje sea menor que 17.
                - Se planta con cualquier puntaje entre 17 y 21.
                - Pierde si supera 21.
                """);
        info.showAndWait();
    }

    public boolean confirmarCierre() {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("Cerrar juego");
        alerta.setHeaderText(null);
        alerta.setContentText("¿Seguro que quieres cerrar el juego? Se perderá la partida actual.");

        Optional<ButtonType> respuesta = alerta.showAndWait();
        return respuesta.isPresent() && respuesta.get() == ButtonType.OK;
    }

    @FXML
    private void cerrarJuego() {
        if (confirmarCierre()) {
            Platform.exit();
        }
    }

    
}


