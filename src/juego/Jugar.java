package juego;

// NUEVO: Imports de Swing (ventanas, panel)
import javax.swing.JPanel;
import javax.swing.Timer;

// NUEVO: Imports de AWT (gráficos, eventos, utilidades)
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

// NUEVO: Imports de Util (listas, conjuntos)
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

// NUEVO: Imports de ImageIO (imágenes)
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException; // Necesario para ImageIO

public class Jugar extends JPanel {
    private boolean gameOver = false;
    private Tablero tablero;
    private Jugador jugador;
    private ArrayList<Enemigo> enemigo;
    private ArrayList<Bomba> bombas;
    private final Set<Integer> teclasPresionadas = new HashSet<>();

    private static final int TAMANO_CELDA = 32; 

    // Sprites del Tablero
    private BufferedImage spriteVacio;
    private BufferedImage spritePared;
    private BufferedImage spriteMuro;
    private BufferedImage spriteExplosion;
    private BufferedImage spriteBomba;

    // Arrays de sprites para el JUGADOR
    private BufferedImage[][] framesJugador_Idle = new BufferedImage[4][4];
    private BufferedImage[][] framesJugador_Walk = new BufferedImage[4][4];
    private BufferedImage[] framesJugador_Death = new BufferedImage[4];
    private BufferedImage[] framesJugador_Win = new BufferedImage[4];
    
    // Arrays de sprites para el ENEMIGO
    private BufferedImage[][] framesEnemigo_Fly = new BufferedImage[4][4];

    public Jugar() {
        tablero = new Tablero(13, 15);
        jugador = new Jugador(1, 7);
        enemigo = new ArrayList<>();
        bombas = new ArrayList<>();

        int anchoPanel = tablero.getColumnas() * TAMANO_CELDA;
        int altoPanel = tablero.getFilas() * TAMANO_CELDA;
        setPreferredSize(new Dimension(anchoPanel, altoPanel));

        setFocusable(true);
        
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if (!teclasPresionadas.contains(key)) { 
                    teclasPresionadas.add(key);

                    if (!gameOver && jugador.isVivo()) { 
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
                        }
                    }

                    if (key == KeyEvent.VK_R && gameOver) {
                         reiniciarJuego();
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                teclasPresionadas.remove(e.getKeyCode());
                
                if (!teclasPresionadas.contains(KeyEvent.VK_W) &&
                    !teclasPresionadas.contains(KeyEvent.VK_S) &&
                    !teclasPresionadas.contains(KeyEvent.VK_A) &&
                    !teclasPresionadas.contains(KeyEvent.VK_D)) {
                    
                    if (jugador.isVivo()) {
                        jugador.setIdle(); 
                    }
                }
            }
        });

        cargarSprites(); 
        generarEnemigos(3);
        timer.setDelay(150); 
        timer.start();
    }
    
    private void cortarFrames(BufferedImage[] arrayDestino, String path) throws IOException {
        BufferedImage hoja = ImageIO.read(getClass().getResource(path));
        // MODIFICADO: Manejar hojas de sprites con menos de 4 frames (ej. win-front)
        for (int i = 0; i < arrayDestino.length; i++) {
            // Comprobamos si el frame existe en la hoja
            if (i * TAMANO_CELDA < hoja.getWidth()) {
                arrayDestino[i] = hoja.getSubimage(i * TAMANO_CELDA, 0, TAMANO_CELDA, TAMANO_CELDA);
            } else {
                // Si no existe (ej. frame 3 y 4 de 'win'), repetimos el último frame
                arrayDestino[i] = arrayDestino[i - 1]; 
            }
        }
    }

   private void cargarSprites() {
        try {
            
            spriteVacio = ImageIO.read(getClass().getResource("/juego/Sprites/terrain/grass.png"));
            spritePared = ImageIO.read(getClass().getResource("/juego/Sprites/terrain/rock.png"));
            spriteMuro = ImageIO.read(getClass().getResource("/juego/Sprites/terrain/wall.png"));
            spriteBomba = ImageIO.read(getClass().getResource("/juego/Sprites/items/dynamite.png"));
            spriteExplosion = ImageIO.read(getClass().getResource("/juego/Sprites/fxs/explosion.png"));
            
            cortarFrames(framesJugador_Idle[0], "/juego/Sprites/character/idle-front.png"); // 0: abajo
            cortarFrames(framesJugador_Idle[1], "/juego/Sprites/character/idle-left.png");  // 1: izquierda
            cortarFrames(framesJugador_Idle[2], "/juego/Sprites/character/idle-right.png"); // 2: derecha
            cortarFrames(framesJugador_Idle[3], "/juego/Sprites/character/idle-back.png");  // 3: arriba

            // Cargar Sprites del JUGADOR (Walk)
            cortarFrames(framesJugador_Walk[0], "/juego/Sprites/character/walk-front.png"); // 0: abajo
            cortarFrames(framesJugador_Walk[1], "/juego/Sprites/character/walk-left.png");  // 1: izquierda
            cortarFrames(framesJugador_Walk[2], "/juego/Sprites/character/walk-right.png"); // 2: derecha
            cortarFrames(framesJugador_Walk[3], "/juego/Sprites/character/walk-back.png");  // 3: arriba
           
            cortarFrames(framesJugador_Death, "/juego/Sprites/character/death-front.png"); 
            cortarFrames(framesJugador_Win, "/juego/Sprites/character/win-front.png");

            cortarFrames(framesEnemigo_Fly[0], "/juego/Sprites/bat/fly-front.png"); // 0: abajo
            cortarFrames(framesEnemigo_Fly[1], "/juego/Sprites/bat/fly-left.png");  // 1: izquierda
            cortarFrames(framesEnemigo_Fly[2], "/juego/Sprites/bat/fly-right.png"); // 2: derecha
            cortarFrames(framesEnemigo_Fly[3], "/juego/Sprites/bat/fly-back.png");  // 3: arriba

        } catch (Exception e) {
            System.err.println("Error fatal al cargar los sprites: " + e.getMessage());
            System.err.println("Verifica que la carpeta 'Sprites' esté dentro de 'src/juego'");
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void generarEnemigos(int cantidad) {
        for (int i = 0; i < cantidad; i++) {
            int x, y;
            do {
                x = (int) (Math.random() * tablero.getColumnas());
                y = (int) (Math.random() * tablero.getFilas());
            } while (tablero.getValor(y, x) != Tablero.VACIO || (x == 1 && y == 7)); // Evitar spawn en jugador
            enemigo.add(new Enemigo(x, y)); 
        }
    }

    private void colocarBomba() {
        if (jugador.isVivo())
            bombas.add(new Bomba(jugador.getX(), jugador.getY(), 3));
    }

    Timer timer = new Timer(150, e -> { 
        
        if (gameOver) {
            if (!jugador.isVivo()) {
                jugador.siguienteFrame(); // Animar muerte
            } else if (todosEnemigosMuertos()) {
                jugador.siguienteFrame(); // Animar victoria
            }
        } else {
            if (jugador.isMoving()) {
                jugador.siguienteFrame();
            }
            
            // Lógica de Bombas
            ArrayList<Bomba> bombasAEliminar = new ArrayList<>();
            for (Bomba bomba : bombas) {
                bomba.tiempoRestante();
                if (bomba.explosion()) {
                    bomba.explotar(tablero, jugador, enemigo);
                    if (!jugador.isVivo())
                        gameOver = true;
                    bombasAEliminar.add(bomba);
                }
            }
            bombas.removeAll(bombasAEliminar);

            // Lógica de Enemigos
            for (Enemigo en : enemigo) {
                if (en.isVivo()) {
                    en.moverEnemigos(tablero); 
                    if (jugador.isVivo() && jugador.colisiona(en)) {
                        jugador.setVivo(false);
                        gameOver = true;
                    }
                }
            }
            
            // Lógica de Victoria
            if (todosEnemigosMuertos() && jugador.isVivo()) {
                gameOver = true;
            }
        }
        repaint(); 
    });
    
    private boolean todosEnemigosMuertos() {
        for (Enemigo en : enemigo) {
            if (en.isVivo()) {
                return false;
            }
        }
        return true;
    }

    private void gameOverFunction(Graphics g) {
        if (gameOver) {
            g.setColor(new Color(0, 0, 0, 150)); 
            g.fillRect(0, 0, getWidth(), getHeight());
            
            String msg = "GAME OVER";
            if (jugador.isVivo()) { 
                msg = "¡VICTORIA!";
                g.setColor(Color.YELLOW);
            } else {
                g.setColor(Color.RED);
            }

            g.setFont(new Font("Arial", Font.BOLD, 40));
            FontMetrics fm = g.getFontMetrics();
            int msgX = (getWidth() - fm.stringWidth(msg)) / 2;
            int msgY = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();
            g.drawString(msg, msgX, msgY);

            String restartMsg = "Presiona R para reiniciar";
            fm = g.getFontMetrics(new Font("Arial", Font.PLAIN, 20));
            int restartMsgX = (getWidth() - fm.stringWidth(restartMsg)) / 2;
            int restartMsgY = msgY + fm.getHeight() + 20;
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.PLAIN, 20));
            g.drawString(restartMsg, restartMsgX, restartMsgY);
        }
    }

    private void reiniciarJuego() {
        tablero = new Tablero(13, 15);
        jugador = new Jugador(1, 7);
        enemigo.clear();
        generarEnemigos(3);
        bombas.clear();
        gameOver = false;
        jugador.setVivo(true);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        dibujarTablero(g); 
        dibujarBombas(g);  
        dibujarJugador(g); 
        dibujarEnemigos(g);
        gameOverFunction(g);
    }

    private void dibujarTablero(Graphics g) {
        for (int i = 0; i < tablero.getFilas(); i++) {
            for (int j = 0; j < tablero.getColumnas(); j++) {
                int x = j * TAMANO_CELDA;
                int y = i * TAMANO_CELDA;
                int valor = tablero.getValor(i, j);

                g.drawImage(spriteVacio, x, y, null);
                
                switch (valor) {
                    case Tablero.PARED:
                        g.drawImage(spritePared, x, y, null);
                        break;
                    case Tablero.MURO:
                        g.drawImage(spriteMuro, x, y, null);
                        break;
                    case Tablero.EXPLOSION:
                        g.drawImage(spriteExplosion, x, y, null);
                        break;
                }
            }
        }
    }

    private void dibujarJugador(Graphics g) {
        int x = jugador.getX() * TAMANO_CELDA;
        int y = jugador.getY() * TAMANO_CELDA;
        int dir = jugador.getDireccion();
        int frame = jugador.getFrameAnimacion();
        
        BufferedImage spriteADibujar = null;

        if (!jugador.isVivo()) {
            spriteADibujar = framesJugador_Death[frame];
        } else if (gameOver && todosEnemigosMuertos()) {
            spriteADibujar = framesJugador_Win[frame];
        } else if (jugador.isMoving()) {
            spriteADibujar = framesJugador_Walk[dir][frame];
        } else {
            spriteADibujar = framesJugador_Idle[dir][frame];
        }

        if (spriteADibujar != null) {
            g.drawImage(spriteADibujar, x, y, null);
        } else {
            g.setColor(Color.MAGENTA); // Color de error si un sprite es nulo
            g.fillRect(x, y, TAMANO_CELDA, TAMANO_CELDA);
        }
    }

    private void dibujarEnemigos(Graphics g) {
        for (Enemigo e : enemigo) {
            if (e.isVivo()) {
                int x = e.getX() * TAMANO_CELDA;
                int y = e.getY() * TAMANO_CELDA;
                int dir = e.getDireccion();
                int frame = e.getFrameAnimacion();

                BufferedImage spriteADibujar = framesEnemigo_Fly[dir][frame];
                
                if (spriteADibujar != null) {
                    g.drawImage(spriteADibujar, x, y, null);
                }
            }
        }
    }

    private void dibujarBombas(Graphics g) {
        g.setColor(Color.RED);
        for (Bomba bomba : bombas) {
            int x = bomba.getX() * TAMANO_CELDA;
            int y = bomba.getY() * TAMANO_CELDA;
            g.drawImage(spriteBomba, x, y, null);
        }
    }
}