import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

public class GamePanel extends JPanel implements ActionListener, KeyListener {
    private Timer timer;
    private Jugador jugador;
    private ArrayList<Entidad> entidades;
    private ArchivoJuego archivo;

    public GamePanel() {
        setPreferredSize(new Dimension(1280, 720));
        setBackground(Color.WHITE);
        setFocusable(true);
        addKeyListener(this);

        jugador = new Jugador(20, 500, 40, 40);
        entidades = new ArrayList<>();
        archivo = new ArchivoJuego("progreso.txt");

        entidades.add(new Esposa(1100, 20, 90, 50));
        entidades.add(new Plataforma(1080, 65, 120, 20));

        entidades.add(new Plataforma(0, 580, 500, 20));
        entidades.add(new EnemigoTerrestre(500, 580, 1000, 20));

        entidades.add(new Plataforma(200, 450, 120, 20));
        entidades.add(new Plataforma(350, 350, 120, 20));
        entidades.add(new Plataforma(550, 350, 120, 20));
        entidades.add(new Plataforma(750, 250, 120, 20));
        entidades.add(new Plataforma(950, 150, 120, 20));

        entidades.add(new EnemigoTerrestre(250, 430, 20, 20));
        entidades.add(new EnemigoTerrestre(250, 470, 40, 110));
        entidades.add(new EnemigoTerrestre(800, 230, 20, 20));




        entidades.add(new EnemigoVolador(500, 320, 40, 40));

        entidades.add(new Cura(500, 250, 40, 40));

        archivo.cargar(jugador);

        timer = new Timer(16, this);
        timer.start();
    }

    public void actionPerformed(ActionEvent e) {
        if (!jugador.estaVivo() || jugador.haSalvadoEsposa()) {
            timer.stop();
        } else {
            jugador.actualizar();
            jugador.verificarColisiones(entidades);
        }
        repaint();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (jugador.estaVivo() && !jugador.haSalvadoEsposa()) {
            jugador.dibujar(g);
            for (Entidad ent : entidades) {
                ent.dibujar(g);
            }
            g.setColor(Color.GREEN);
            g.fillRect(20, 20, jugador.getVida() * 30, 20);
            g.setColor(Color.BLACK);
            g.drawRect(20, 20, 90, 20);
        } else if (!jugador.estaVivo()) {
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.BOLD, 40));
            g.drawString("¡Game Over!", 400, 300);
        } else if (jugador.haSalvadoEsposa()) {
            g.setColor(Color.BLUE);
            g.setFont(new Font("Arial", Font.BOLD, 40));
            g.drawString("¡Salvaste a tu esposa!", 300, 300);
        }
    }


    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) jugador.setIzquierda(true);
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) jugador.setDerecha(true);
        if (e.getKeyCode() == KeyEvent.VK_SPACE) jugador.saltar();
        if (e.getKeyCode() == KeyEvent.VK_S) archivo.guardar(jugador);
    }

    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) jugador.setIzquierda(false);
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) jugador.setDerecha(false);
    }

    public void keyTyped(KeyEvent e) {}
}
