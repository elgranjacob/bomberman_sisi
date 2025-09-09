package juego;

public class Jugador {
    private int x, y;
    private boolean vivo = true;

    public Jugador(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void moverArriba() {
        if (vivo)y--;
    }

    public void moverAbajo() {
        if(vivo)y++;
    }

    public void moverDerecha() {
        if (vivo)x++;
    }

    public void moverIzquierda() {
        if (vivo)x--;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    public boolean isVivo() {
        return vivo;
    }
    public void setVivo(boolean vivo) {
        this.vivo = vivo;
    }
}
