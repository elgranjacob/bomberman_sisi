package juego;

public class Bomba {
    private int x, y;
    private int tiempoRestante;


    public Bomba(int x, int y, int tiempoRestante) {
        this.x = x;
        this.y = y;
        this.tiempoRestante = tiempoRestante;

    }
    
    public void tiempoRestante(){
        if (tiempoRestante > 0){
            tiempoRestante--;
        }
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
