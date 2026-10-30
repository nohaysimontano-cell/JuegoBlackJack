package co.edu.univalle.poe.mvc.model;

public class Mano {
    private NodoCarta cabeza;
    private int cantidad;

    public void agregarCarta(Carta carta) {
        NodoCarta nuevoNodo = new NodoCarta(carta);
        if (cabeza == null) {
            cabeza = nuevoNodo;
        }else {
            NodoCarta actual = cabeza;
            while (actual.getSiguiente() != null) {
                actual = actual.getSiguiente();
            }
            actual.setSiguiente(nuevoNodo);
        }
        cantidad++;
        
    }

    public int getCantidad() {
        return cantidad;
    }

    public boolean estaVacia() {
        return cabeza == null;
    }

    public void vaciar() {
        cabeza = null;
        cantidad = 0;
    }

    public Carta[] obtenerCartas() {
        Carta[] cartas = new Carta[cantidad];
        NodoCarta actual = cabeza;
        int i = 0;

        while (actual != null) {
            cartas[i] = actual.getCarta();
            actual = actual.getSiguiente();
            i++;
        }
        return cartas;
    }

    public void mostrarCartas() {
        NodoCarta actual = cabeza;
        System.out.println("Cartas en la mano:");
        while (actual != null) {
            System.out.println(actual.getCarta());
            actual = actual.getSiguiente();
        }
    }

    public int calcularPuntaje() {
    int total = 0;
    int ases = 0;
    NodoCarta actual = cabeza;

    while (actual != null) {
        Carta carta = actual.getCarta();
        total += carta.getValorBase();
        if (carta.getValor() == Valor.AS) {
            ases++;
        }
        actual = actual.getSiguiente();
    }

    while (total > 21 && ases > 0) {
        total -= 10;
        ases--;
    }
    return total;
    }

    public boolean sePasa() {
        return calcularPuntaje() > 21;
    }

    public boolean esBlackjack() {
        return cantidad == 2 && calcularPuntaje() == 21;
    }

    public boolean esVeintiuno() {
        return calcularPuntaje() == 21 && !esBlackjack();
    }

    public boolean esVeinteYMedio() {
        return cantidad == 2 && calcularPuntaje() == 14;
    } 

}
