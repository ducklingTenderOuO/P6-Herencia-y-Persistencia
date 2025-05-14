import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class GamePanel extends JPanel implements ActionListener {
    private Timer timer;
    private Jugador jugador;
    private ArrayList<Enemigo> enemigos;
    private boolean arriba, abajo, izquierda, derecha;
    private ArrayList<Entidad> entidades;

    private long ultimoDañoRecibido = 0;
    private final int cooldownDaño = 1000; // milisegundos

    public GamePanel() {
        this.setFocusable(true);
        this.setPreferredSize(new Dimension(1000, 800));

        entidades = new ArrayList<>();
        jugador = new Jugador(100, 100, 5);

        int[][] plataformas = {
                {0, 580, 1200, 20},
                {100, 520, 100, 20},
                {300, 460, 100, 20},
                {500, 400, 100, 20},
                {700, 340, 100, 20},
                {200, 500, 20, 80}
        };

        for (int[] p : plataformas) {
            entidades.add(new Plataforma(p[0], p[1], p[2], p[3]));
        }

        enemigos = new ArrayList<>();
        enemigos.add(new EnemigoTerrestre(700, 300, 50, 50, 3));

        timer = new Timer(16, this);
        timer.start();

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_W -> arriba = true;
                    case KeyEvent.VK_S -> abajo = true;
                    case KeyEvent.VK_A -> izquierda = true;
                    case KeyEvent.VK_D -> derecha = true;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_W -> arriba = false;
                    case KeyEvent.VK_S -> abajo = false;
                    case KeyEvent.VK_A -> izquierda = false;
                    case KeyEvent.VK_D -> derecha = false;
                }
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (jugador.estaVivo()) {
            if (arriba) jugador.moverArriba();
            if (abajo) jugador.moverAbajo();
            if (izquierda) jugador.moverIzquierda();
            if (derecha) jugador.moverDerecha();

            for (Enemigo enemigo : enemigos) {
                if (enemigo.estaVivo()) {
                    enemigo.moverHacia(jugador);

                    if (Math.abs(jugador.getX() - enemigo.getX()) < 50 &&
                            Math.abs(jugador.getY() - enemigo.getY()) < 50) {

                        long ahora = System.currentTimeMillis();
                        if (ahora - ultimoDañoRecibido >= cooldownDaño) {
                            jugador.recibirDaño(1);
                            ultimoDañoRecibido = ahora;
                        }
                    }
                }
            }
        }

        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (jugador.estaVivo()) {
            // Jugador
            g.setColor(Color.BLUE);
            g.fillRect(jugador.getX(), jugador.getY(), 50, 50);

            // Enemigos
            for (Enemigo enemigo : enemigos) {
                if (enemigo.estaVivo()) {
                    enemigo.dibujar(g);
                }
            }

            for (Entidad entidad : entidades) {
                entidad.dibujar(g);
            }

            // Vida
            g.setColor(Color.GREEN);
            g.fillRect(20, 20, jugador.getVida() * 30, 20);
            g.setColor(Color.BLACK);
            g.drawRect(20, 20, 150, 20);
        } else {
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.BOLD, 40));
            g.drawString("¡Game Over!", 300, 300);
        }
    }
}