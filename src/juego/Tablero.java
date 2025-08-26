package juego;

public class Tablero {
    private int[][] mapa;

    public Tablero(int filas, int columnas){
        mapa = new int[filas][columnas];
        inicializarMapa();
    }

    private void inicializarMapa(){
        for (int i = 0; i < mapa.length; i++) {
            for (int j = 0; j < mapa[i].length; j++) {
                 if (i == 0 || j == 0 || i == mapa.length - 1 || j == mapa[i].length - 1) {
                     mapa[i][j] = 1;
                 } else {
                     mapa[i][j] = 0;// por modificar
                 }
            }
        }
    }

    public int getValor(int fila, int columna){
        return mapa[fila][columna];
    }

    public void setValor(int fila, int columna, int valor){
        mapa[fila][columna] = valor;
    }
     
    public int getFilas(){
        return mapa.length;
    }

    public int getColumnas(){
        return mapa[0].length;
    }
}
