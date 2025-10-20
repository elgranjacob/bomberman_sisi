package juego;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Jugar extends JPanel {
    private boolean gameOver = false;
    private Tablero tablero;
    private Jugador jugador;
    private ArrayList<Enemigo> enemigo;
    private ArrayList<Bomba> bombas;
    // creamos un conjunto para almacenar las teclas presionadas (no almacena datos
    // duplicados)
    private final Set<Integer> teclasPresionadas = new HashSet<>();// implentamos HashSet para un manejo más rápido en
                                                                   // conjuntos

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
                if (!teclasPresionadas.contains(key)) { // si no estaba presionada antes
                    teclasPresionadas.add(key);

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
                        case KeyEvent.VK_R:
                            if (gameOver) {
                                reiniciarJuego();
                            }
                            break;

                    }
                    for (Enemigo enemies : enemigo) {
                        if (jugador.isVivo() && enemies.isVivo() && jugador.colisiona(enemies)) {
                            jugador.setVivo(false);
                            gameOver = true;
                        }
                    }

                    repaint();
                }

            }

            @Override
            public void keyReleased(KeyEvent e) {
                teclasPresionadas.remove(e.getKeyCode()); // liberamos la tecla
            }
        });
        generarEnemigos(3);
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
                if (!jugador.isVivo())
                    gameOver = true;
                bombasAEliminar.add(bomba);// lo agregamos al listado de bombas eliminadas
            }
        }
        bombas.removeAll(bombasAEliminar);// limpiamos lista
        repaint();
    });

    private void gameOverFunction(Graphics g) {
        if (gameOver) {
            g.setColor(new Color(0, 0, 0, 150)); // Fondo semitransparente
            g.fillRect(0, 0, getWidth(), getHeight());

            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 40));
            g.drawString("GAME OVER", getWidth() / 2 - 120, getHeight() / 2);

            g.setFont(new Font("Arial", Font.PLAIN, 20));
            g.setColor(Color.WHITE);
            g.drawString("Presiona R para reiniciar", getWidth() / 2 - 120, getHeight() / 2 + 40);
        }

    }

    private void reiniciarJuego() {
        tablero = new Tablero(13, 15);
        jugador = new Jugador(1, 7);
        enemigo.clear();
        generarEnemigos(3);
        bombas.clear();
        gameOver = false;
        repaint();
    }

    // pintamos componentes
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        dibujarTablero(g);
        dibujarJugador(g);
        dibujarEnemigos(g);
        dibujarBombas(g);
        gameOverFunction(g);
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
