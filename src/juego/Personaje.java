package juego;

public class Personaje {
    protected int x, y;
    protected boolean vivo = true;

    public Personaje(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public void moverArriba(Tablero tablero) {
        int nuevaY = y - 1;
        if (isVivo() && nuevaY >= 0) {
            int celda = tablero.getValor(nuevaY, x);
            if (celda != Tablero.PARED && celda != Tablero.MURO) {// se revisa si hay colision con los muros
                setY(nuevaY);
            }
        }
    }

    public void moverAbajo(Tablero tablero) {
        int nuevaY = y + 1;
        if (isVivo() && nuevaY < tablero.getFilas()) {//validamos si es posible el movimiento y el estado del sprite
            int celda = tablero.getValor(nuevaY, x);
            if (celda != Tablero.PARED && celda != Tablero.MURO) {// se revisa si hay colision con los muros
                setY(nuevaY);
            }
        }
    }

    public void moverDerecha(Tablero tablero) {
        int nuevaX = x + 1;
        if (isVivo() && nuevaX < tablero.getColumnas() ) {
            int celda = tablero.getValor(y, nuevaX);
            if (celda != Tablero.PARED && celda != Tablero.MURO) {// se revisa si hay colision con los muros
                setX(nuevaX);
            }
        }
    }
   
    public void moverIzquierda(Tablero tablero) {
        int nuevaX = x - 1;
        if (isVivo() && nuevaX >= 0) {
            int celda = tablero.getValor(y, nuevaX);
            if (celda != Tablero.PARED && celda != Tablero.MURO) {// se revisa si hay colision con los muros
                setX(nuevaX);
            }
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isVivo() {
        return vivo;
    }

    public void setVivo(boolean vivo) {
        this.vivo = vivo;
    }

    public boolean colisionaConBomba(int fila, int columna) {
        return this.x == columna && this.y == fila;
    }
}
