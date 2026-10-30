package co.edu.univalle.poe.mvc.model;

public class Maquina extends Participante {

    public Maquina() {
        super();
    }

    public boolean debePedirCarta() {
        int puntaje = calcularPuntaje();
        return puntaje < 17;
    }



}
