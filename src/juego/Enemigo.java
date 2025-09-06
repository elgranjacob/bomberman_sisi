package juego;

// import java.util.Random;

public class Enemigo {

    private int x, y, vida;

    public Enemigo(int x, int y) {
        this.x = x;
        this.y = y;
    }

   /*  public void moverEnemigos(){
        int max;
        int movimientos = (int)(Math.random())*(max);

        switch (movimientos) {
            case 0:
                
                break;
            case 1:
                break;
            case
        }
    } */

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getVida() {
        return vida;
    }
}
