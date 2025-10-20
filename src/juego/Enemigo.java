package juego;

public class Enemigo extends Personaje {

    public Enemigo(int x, int y) {
        super(x, y);
        this.isMoving = true; // El enemigo siempre está "volando" (moviéndose)
    }

    public void moverEnemigos(Tablero tablero) {
        int movimiento = 1 + (int) (Math.random() * 4);
    
        switch (movimiento) {
            case 1:
                moverArriba(tablero);
                break;
            case 2:
                moverAbajo(tablero);
                break;
            case 3:
                moverDerecha(tablero);
                break;
            case 4:
                moverIzquierda(tablero);
                break;
        }

        // Ya que isMoving es true, avanzamos el frame
        siguienteFrame();
    }
}