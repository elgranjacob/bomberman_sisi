package juego;

public class Enemigo extends Personaje {

    public Enemigo(int x, int y) {
        super(x, y);
    }

    public void moverEnemigos(Tablero tablero) {
        int movimiento = 1 + (int) (Math.random() * 4);
    
        switch (movimiento) {
            case 1:
                // arriba
                moverArriba(tablero);
                break;
            case 2:
                // abajo
                 moverAbajo(tablero);
                break;
            case 3:
            //derecha
                 moverDerecha(tablero);
                break;
            case 4:
                moverIzquierda(tablero);
                break;
        }
    }
}
