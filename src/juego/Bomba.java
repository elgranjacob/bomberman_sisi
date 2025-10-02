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

    public void explotar(Tablero tablero, Jugador jugador, Enemigo enemigo) {
        int radio = 2;
        tablero.setValor(y, x, Tablero.EXPLOSION); // centro
        // un bucle por cada expansión porque usamos break, por lo tanto requiere uno
        // para cada uno
        for (int i = 1; i <= radio; i++) {// i = 1 por que 0 viene siendo el centro
            if (y - i >= 0) {
                int valor = tablero.getValor(y - i, x);
                if (valor == Tablero.PARED)
                    break;
                if (jugador.colisionaConBomba(y - i, x)){
                    jugador.setVivo(false);
                }
                if (enemigo.colisionaConBomba(y - i, x)){
                    enemigo.setVivo(false);
                }
                tablero.setValor(y - i, x, Tablero.EXPLOSION);// expansion arriba
            }
        }
        for (int i = 1; i <= radio; i++) {// i = 1 por que 0 viene siendo el centro
            if (y + i < tablero.getFilas()) {
                int valor = tablero.getValor(y + i, x);
                if (valor == Tablero.PARED)
                    break;
                if (jugador.colisionaConBomba(y + i, x)){
                    jugador.setVivo(false);
                }
                if (enemigo.colisionaConBomba(y + i, x)){
                    enemigo.setVivo(false);
                }
                tablero.setValor(y + i, x, Tablero.EXPLOSION);// expansion abajo
            }
        }
        for (int i = 1; i <= radio; i++) {// i = 1 por que 0 viene siendo el centro
            if (x - i >= 0) {
                int valor = tablero.getValor(y, x - i);
                if (valor == Tablero.PARED)
                    break;
                if (jugador.colisionaConBomba(y, x - i)){
                    jugador.setVivo(false);
                }
                if (enemigo.colisionaConBomba(y, x - i)){
                    enemigo.setVivo(false);
                }
                tablero.setValor(y, x - i, Tablero.EXPLOSION);// expansion izquierda
            }
        }
        for (int i = 1; i <= radio; i++) {// i = 1 por que 0 viene siendo el centro
            if (x + i < tablero.getColumnas()) {
                int valor = tablero.getValor(y, x + i);
                if (valor == Tablero.PARED)
                    break;
                if (jugador.colisionaConBomba(y, x + i)){
                    jugador.setVivo(false);   
                }

                if (enemigo.colisionaConBomba(y, x + i)){
                    enemigo.setVivo(false);   
                }
                tablero.setValor(y, x + i, Tablero.EXPLOSION);// expansion derecha
            }
        }
        Timer t = new Timer(300, e -> {
            tablero.setValor(y, x, Tablero.VACIO);
            for (int i = 1; i <= radio; i++) {
                if (y - i >= 0 && tablero.getValor(y - i, x) == Tablero.EXPLOSION)
                    tablero.setValor(y - i, x, Tablero.VACIO);
                if (y + i < tablero.getFilas() && tablero.getValor(y + i, x) == Tablero.EXPLOSION)
                    tablero.setValor(y + i, x, Tablero.VACIO);
                if (x - i >= 0 && tablero.getValor(y, x - i) == Tablero.EXPLOSION)
                    tablero.setValor(y, x - i, Tablero.VACIO);
                if (x + i < tablero.getColumnas() && tablero.getValor(y, x + i) == Tablero.EXPLOSION)
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
