package juego;

public class Jugador extends Personaje {
    public Jugador(int x, int y) {
        super(x, y);
    }

    public boolean colisiona(Enemigo enemigo) {
        return this.x == enemigo.getX() && this.y == enemigo.getY();
    }

}
