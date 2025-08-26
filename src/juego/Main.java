package juego;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame ventana = new JFrame("Bomberman");
        Jugar juego = new Jugar();
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.add(juego);
        ventana.pack();
        ventana.setVisible(true);
    }
}

