package juego;

public class Jugador {
    private int x, y;

    public Jugador(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void moverArriba() {
        y--;
    }

    public void moverAbajo() {
        y++;
    }

    public void moverDerecha() {
        x++;
    }

    public void moverIzquierda() {
        x--;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
