package co.edu.univalle.poe.mvc;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import co.edu.univalle.poe.mvc.controller.JuegoController;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("view/juego-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 750);
        JuegoController controlador = fxmlLoader.getController();

        stage.setOnCloseRequest(evento -> {
            if (!controlador.confirmarCierre()) {
                evento.consume();   // cancela el cierre: la ventana y la partida siguen igual
            }
        });

        stage.setTitle("Blackjack");
        stage.setScene(scene);
        stage.show();
}
}
