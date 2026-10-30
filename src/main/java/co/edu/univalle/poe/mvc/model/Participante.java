package co.edu.univalle.poe.mvc.model;

public class Participante {

    private final Mano mano;

    public Participante() {
        mano = new Mano();
    }

    public Mano getMano() {
        return mano;
    }

    public int calcularPuntaje() {
        return mano.calcularPuntaje();
    }

    public void vaciarMano() {
        mano.vaciar();
    }

    

}
