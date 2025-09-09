package juego;

//import java.util.ArrayList;

public class Tablero {
    public static final int PARED = 1;
    public static final int MURO = 2;
    public static final int VACIO = 0;
    public static final int EXPLOSION = 3;

    private int[][] mapa;

    public Tablero(int filas, int columnas) {
        mapa = new int[filas][columnas];
        inicializarMapa();
    }

    private void inicializarMapa() {
        for (int i = 0; i < mapa.length; i++) {
            for (int j = 0; j < mapa[i].length; j++) {
                if (i == 0 || j == 0 || i == mapa.length - 1 || j == mapa[i].length - 1) {
                    mapa[i][j] = PARED;
                } else if (i >= 1 || j >= 1 || i < mapa.length - 1 || j < mapa[i].length - 1) {
                    if (i % 2 == 0 && j % 2 == 0) {
                        mapa[i][j] = PARED;
                    } else {
                        double probabilidadMuro = 0.2;
                        if (Math.random() < probabilidadMuro) {
                            mapa[i][j] = MURO;
                        }
                    }
                } else {
                    mapa[i][j] = VACIO;
                }
            }
        }
    }


    public int getValor(int fila, int columna) {
        return mapa[fila][columna];
    }

    public void setValor(int fila, int columna, int valor) {
        mapa[fila][columna] = valor;
    }

    public int getFilas() {
        return mapa.length;
    }

    public int getColumnas() {
        return mapa[0].length;
    }
}
