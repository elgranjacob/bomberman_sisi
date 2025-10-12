package juego;

import java.util.ArrayList;
import java.util.function.BiConsumer;

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

    public void explotar(Tablero tablero, Jugador jugador, ArrayList<Enemigo> enemigos) {
    int radio = 2;
    tablero.setValor(y, x, Tablero.EXPLOSION); // centro

    //Método auxiliar para verificar colisiones(interfaz funcional: que solo tiene uso de un metodo abstrato)
    //plantilla de función para dos parametros de diferentes o mismos tipos
    //es como un contrato, pero nosotros lo escribimos
    //des pues de '=' usamos una funcion lambda para llevar a cabo la acción
    //hace uso como método abstracto, no devuelve nada
    BiConsumer<Integer, Integer> verificarColisiones = (posY, posX) -> {
        //Jugador
        //se define la acción del metodo abstracto de la interfaz
        if (jugador.colisionaConBomba(posY, posX)) {
            jugador.setVivo(false);
        }
        //Enemigos
        for (Enemigo ene : enemigos) {
            if (ene.colisionaConBomba(posY, posX)) {
                ene.setVivo(false);
            }
        }
    };

    //Expansión de la explosión (arriba, abajo, izquierda, derecha)
    for (int i = 1; i <= radio; i++) { // arriba
        if (y - i >= 0) {
            int valor = tablero.getValor(y - i, x);
            if (valor == Tablero.PARED) break;
            verificarColisiones.accept(y - i, x);//usamos el unico método de la  interfaz
            tablero.setValor(y - i, x, Tablero.EXPLOSION);
        }
    }

    for (int i = 1; i <= radio; i++) { // abajo
        if (y + i < tablero.getFilas()) {
            int valor = tablero.getValor(y + i, x);
            if (valor == Tablero.PARED) break;
            verificarColisiones.accept(y + i, x);
            tablero.setValor(y + i, x, Tablero.EXPLOSION);
        }
    }

    for (int i = 1; i <= radio; i++) { // izquierda
        if (x - i >= 0) {
            int valor = tablero.getValor(y, x - i);
            if (valor == Tablero.PARED) break;
            verificarColisiones.accept(y, x - i);
            tablero.setValor(y, x - i, Tablero.EXPLOSION);
        }
    }

    for (int i = 1; i <= radio; i++) { // derecha
        if (x + i < tablero.getColumnas()) {
            int valor = tablero.getValor(y, x + i);
            if (valor == Tablero.PARED) break;
            verificarColisiones.accept(y, x + i);
            tablero.setValor(y, x + i, Tablero.EXPLOSION);
        }
    }

    //Limpiar la explosión después de un tiempo
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
