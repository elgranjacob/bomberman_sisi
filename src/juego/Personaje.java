package juego;

public class Personaje {
    protected int x, y;
    protected boolean vivo = true;
    protected int direccion = 0; 
    protected int frameAnimacion = 0;
    protected boolean isMoving = false;

    public Personaje(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    private void setDireccion(int nuevaDireccion) {
        if (this.direccion != nuevaDireccion) {
            this.direccion = nuevaDireccion;
            this.frameAnimacion = 0; 
        }
    }

    // --- REEMPLAZA TU MÉTODO ANTIGUO POR ESTE ---
    public void moverArriba(Tablero tablero) {
        int nuevaY = y - 1;
        if (isVivo() && nuevaY >= 0) {
            int celda = tablero.getValor(nuevaY, x);
            if (celda != Tablero.PARED && celda != Tablero.MURO) {
                setY(nuevaY);
                setDireccion(3);
                this.isMoving = true; // <-- ESTA ES LA LÍNEA CLAVE
            }
        }
    }

    // --- ASEGÚRATE DE QUE LOS OTROS MÉTODOS TENGAN 'isMoving = true' ---
    public void moverAbajo(Tablero tablero) {
        int nuevaY = y + 1;
        if (isVivo() && nuevaY < tablero.getFilas()) {
            int celda = tablero.getValor(nuevaY, x);
            if (celda != Tablero.PARED && celda != Tablero.MURO) {
                setY(nuevaY);
                setDireccion(0);
                this.isMoving = true; // <-- AQUÍ TAMBIÉN
            }
        }
    }

    public void moverDerecha(Tablero tablero) {
        int nuevaX = x + 1;
        if (isVivo() && nuevaX < tablero.getColumnas() ) {
            int celda = tablero.getValor(y, nuevaX);
            if (celda != Tablero.PARED && celda != Tablero.MURO) {
                setX(nuevaX);
                setDireccion(2);
                this.isMoving = true; // <-- AQUÍ TAMBIÉN
            }
        }
    }
    
    public void moverIzquierda(Tablero tablero) {
        int nuevaX = x - 1;
        if (isVivo() && nuevaX >= 0) {
            int celda = tablero.getValor(y, nuevaX);
            if (celda != Tablero.PARED && celda != Tablero.MURO) {
                setX(nuevaX);
                setDireccion(1);
                this.isMoving = true; // <-- AQUÍ TAMBIÉN
            }
        }
    }

    // ... (El resto de tus métodos: getX, getY, setX, setY, setIdle, etc.) ...

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
        if (!vivo) {
             this.frameAnimacion = 0; 
        }
    }

    public boolean colisionaConBomba(int fila, int columna) {
        return this.x == columna && this.y == fila;
    }
    
    public int getDireccion() {
        return direccion;
    }

    public int getFrameAnimacion() {
        return frameAnimacion;
    }

    public boolean isMoving() {
        return isMoving;
    }

    public void setIdle() {
        this.isMoving = false;
        this.frameAnimacion = 0; 
    }

    public void siguienteFrame() {
        frameAnimacion = (frameAnimacion + 1) % 4; 
    }
}