package juego;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Jugar extends JPanel {
    private Tablero tablero;
    private Jugador jugador;
    private ArrayList<Enemigo> enemigo;
    private ArrayList<Bomba> bombas;

    public Jugar() {
        tablero = new Tablero(13, 15);
        jugador = new Jugador(1, 7);
        enemigo = new ArrayList<>();
        bombas = new ArrayList<>();

        setPreferredSize(new Dimension(416, 480));
        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                switch (key) {
                    case KeyEvent.VK_W:
                        jugador.moverArriba(tablero);
                        break;
                    case KeyEvent.VK_S:
                        jugador.moverAbajo(tablero);
                        break;
                    case KeyEvent.VK_A:
                        jugador.moverIzquierda(tablero);
                        break;
                    case KeyEvent.VK_D:
                        jugador.moverDerecha(tablero);
                        break;
                    case KeyEvent.VK_SPACE:
                        colocarBomba();
                        break;
                    default:
                        // ignorar otras teclas
                        return;
                }
                for(Enemigo enemies : enemigo){
                    if (jugador.colisiona(enemies)) 
                        jugador.setVivo(false);
                }

                repaint();
            }

        });
        generarEnemigos(5);
        timer.start();

    }

    private void generarEnemigos(int cantidad) {
        for (int i = 0; i < cantidad; i++) {
            int x, y;
            do {
                x = (int) (Math.random() * tablero.getColumnas());
                y = (int) (Math.random() * tablero.getFilas());
            } while (tablero.getValor(y, x) != Tablero.VACIO); // evita muros y paredes

            enemigo.add(new Enemigo(x, y)); // 1 = vida inicial
        }
    }

    private void colocarBomba() {
        if (jugador.isVivo())
            bombas.add(new Bomba(jugador.getX(), jugador.getY(), 3));// agragamos bombas a la lista de
    }

    // explosion de las bombas
    Timer timer = new Timer(500, e -> {
        ArrayList<Bomba> bombasAEliminar = new ArrayList<>();// bombas eliminadas
        for (Bomba bomba : bombas) {
            bomba.tiempoRestante();// activamos las bombas
            if (bomba.explosion()) {// marca true cuando ya no haya tiempo
                bomba.explotar(tablero, jugador, enemigo);// explosion dentro del tablero
                bombasAEliminar.add(bomba);// lo agregamos al listado de bombas eliminadas
            }
        }
        bombas.removeAll(bombasAEliminar);// limpiamos lista
        repaint();
    });

    // pintamos componentes
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

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                int valor = tablero.getValor(i, j);
                if (valor == Tablero.PARED) {
                    g.setColor(Color.DARK_GRAY);
                } else if (valor == Tablero.MURO) {
                    g.setColor(Color.GRAY);
                } else if (valor == Tablero.VACIO) {
                    g.setColor(Color.BLACK);
                } else if (valor == Tablero.EXPLOSION) {
                    g.setColor(Color.ORANGE);
                }
                g.fillRect(j * anchoCelda, i * altoCelda, anchoCelda, altoCelda);
                g.setColor(Color.BLACK);
                g.drawRect(j * anchoCelda, i * altoCelda, anchoCelda, altoCelda);
            }
        }
    }

    private void dibujarJugador(Graphics g) {
        int anchoCelda = getWidth() / tablero.getColumnas();
        int altoCelda = getHeight() / tablero.getFilas();
        if (jugador.isVivo()) {
            g.setColor(Color.BLUE);
            g.fillOval(jugador.getX() * anchoCelda, jugador.getY() * altoCelda, anchoCelda, altoCelda);// dibujamos el
                                                                                                       // jugador como
                                                                                                       // un
                                                                                                       // ovalo
        }
    }

    private void dibujarEnemigos(Graphics g) {
        int anchoCelda = getWidth() / tablero.getColumnas();
        int altoCelda = getHeight() / tablero.getFilas();

        for (Enemigo e : enemigo) { 
            if (e.isVivo()) {
                g.setColor(Color.GREEN);
                g.fillOval(e.getX() * anchoCelda, e.getY() * altoCelda, anchoCelda, altoCelda);
            }
            e.moverEnemigos(tablero);
        }
    }

    private void dibujarBombas(Graphics g) {
        int anchoCelda = getWidth() / tablero.getColumnas();
        int altoCelda = getHeight() / tablero.getFilas();
        g.setColor(Color.RED);
        for (Bomba bomba : bombas) {
            g.fillRect(bomba.getX() * anchoCelda, bomba.getY() * altoCelda, anchoCelda, altoCelda);// dibujamos la bomba
                                                                                                   // como un rectangulo
        }
    }
}
