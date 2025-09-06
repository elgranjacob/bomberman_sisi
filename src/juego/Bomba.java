package juego;

import javax.swing.Timer;

public class Bomba {
    private int x, y;
    private int tiempoRestante;

    public Bomba(int x, int y, int tiempoRestante) {
        this.x = x;
        this.y = y;
        this.tiempoRestante = tiempoRestante;
        
    }

    public void tiempoRestante() {
        if (tiempoRestante > 0) {
            tiempoRestante--;
        }
    }

    public boolean explosion() {
        return tiempoRestante == 0;
    }

    public void explotar(Tablero tablero) {
        int radio = 2;
        tablero.setValor(y, x, Tablero.EXPLOSION); // centro

        for (int i = 1; i <= radio; i++) {
            if (y - i >= 0)
                tablero.setValor(y - i, x, Tablero.EXPLOSION);// expansion arriba
            if (y + i < tablero.getFilas())
                tablero.setValor(y + i, x, Tablero.EXPLOSION);// expansion abajo
            if (x - i >= 0)
                tablero.setValor(y, x - i, Tablero.EXPLOSION);//expansion izquierda
            if (x + i < tablero.getColumnas())
                tablero.setValor(y, x + i, Tablero.EXPLOSION);// 

        }
         Timer t = new Timer(1000, e -> {
            tablero.setValor(y, x, Tablero.VACIO);
            for (int i = 1; i <= radio; i++) {
            if (y - i >= 0)
                tablero.setValor(y - i, x, Tablero.VACIO);
            if (y + i < tablero.getFilas())
                tablero.setValor(y + i, x, Tablero.VACIO);
            if (x - i >= 0)
                tablero.setValor(y, x - i, Tablero.VACIO);
            if (x + i < tablero.getColumnas())
                tablero.setValor(y, x + i, Tablero.VACIO);
                
        }
        });
        t.setRepeats(false);
        t.start();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getTiempoRestante() {
        return tiempoRestante;
    }
}
