package juego;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Jugar extends JPanel {
    private Tablero tablero;
    private Jugador jugador;
    private Enemigo enemigo;
    private ArrayList<Bomba> bombas;

    public Jugar() {
        tablero = new Tablero(15, 15);
        jugador = new Jugador(7, 7);
        enemigo = new Enemigo(10,9);
        bombas = new ArrayList<>();

        setPreferredSize(new Dimension(600, 600));
        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {// invoca un keyevent cada que se presiona la llave
                int key = e.getKeyCode();
                if (key == KeyEvent.VK_UP) {// usamos las llavaes de cada campo para que suceda el evento
                    jugador.moverArriba();
                } else if (key == KeyEvent.VK_DOWN) {
                    jugador.moverAbajo();
                } else if (key == KeyEvent.VK_LEFT) {
                    jugador.moverIzquierda();
                } else if (key == KeyEvent.VK_RIGHT) {
                    jugador.moverDerecha();
                } else if (key == KeyEvent.VK_SPACE) {
                    colocarBomba();
                }
                repaint();// repinta el panel cada que se presiona una tecla
            }
        });
    }

    private void colocarBomba() {
        bombas.add(new Bomba(getX(), getY(), 4));// agragamos bombas a la lista de
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        dibujarTablero(g); 
        dibujarJugador(g);
        dibujarEnemigos(g);
        dibujarBombas(g);
    }

    private void dibujarTablero(Graphics g) {
        int filas = tablero.getFilas();
        int columnas = tablero.getColumnas();
        int anchoCelda = getWidth() / columnas;
        int altoCelda = getHeight() / filas;

        for (int i = 0; i < filas; i++) {//dibujamos el tablero que establecimos
            for (int j = 0; j < columnas; j++) {
                if (tablero.getValor(i, j) == 1) {// si el valor es 1 es una pared (ya que en el tablero inicializamos los bordes con 1)
                    g.setColor(Color.DARK_GRAY);
                } else {
                    g.setColor(Color.LIGHT_GRAY);// si es 0 es un espacio libre
                }
                g.fillRect(j * anchoCelda, i * altoCelda, anchoCelda, altoCelda);//llenamos el rectangulo
                g.setColor(Color.BLACK);
                g.drawRect(j * anchoCelda, i * altoCelda, anchoCelda, altoCelda);// dibujamos el rectangulo
            }
        }
    }

    private void dibujarJugador(Graphics g) {
        int anchoCelda = getWidth() / tablero.getColumnas();
        int altoCelda = getHeight() / tablero.getFilas();
        g.setColor(Color.BLUE);
        g.fillOval(jugador.getX() * anchoCelda, jugador.getY() * altoCelda, anchoCelda, altoCelda);// dibujamos el jugador como un ovalo
    }

    private void dibujarEnemigos(Graphics g) {
        int anchoCelda = getWidth() / tablero.getColumnas();
        int altoCelda = getHeight() / tablero.getFilas();
        g.setColor(Color.GREEN);
        g.fillOval(enemigo.getX() * anchoCelda, enemigo.getY() * altoCelda, anchoCelda, altoCelda);// dibujamos el jugador como un ovalo
        
    }

    private void dibujarBombas(Graphics g) {
        int anchoCelda = getWidth() / tablero.getColumnas();
        int altoCelda = getHeight() / tablero.getFilas();
        g.setColor(Color.RED);
        for (Bomba bomba : bombas) {
            g.fillRect(bomba.getX() * anchoCelda, bomba.getY() * altoCelda, anchoCelda, altoCelda);// dibujamos la bomba como un rectangulo
        }
    }
}
