import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.awt.FontMetrics;
import java.util.Iterator;

public class GamePanel extends JPanel implements ActionListener, KeyListener {
    private Timer timer;
    private Jugador jugador;
    private ArrayList<Entidad> entidades;
    private ArchivoJuego archivo;
    private ArrayList<EnemigoVolador> sierras = new ArrayList<>();
    private int contadorSierras = 0;
    private final int intervaloSierras = 120;
    public Sonido sonidoFondo;
    public Sonido sonidoSalto;
    public Sonido sonidoGolpe;
    public Sonido sonidoVictoria;
    public Sonido sonidoDerrota;
    private Image fondoJuego;
    private Image fondoVictoria;
    private Image fondoDerrota;
    private boolean esperandoReinicio;

    public GamePanel() {
        setPreferredSize(new Dimension(1280, 720));
        setBackground(Color.WHITE);
        setFocusable(true);
        addKeyListener(this);

        cargarRecursos();
        inicializarJuego();
    }

    private void cargarRecursos() {
        try {
            sonidoFondo = new Sonido("fondo.wav");
            sonidoSalto = new Sonido("salto.wav");
            sonidoGolpe = new Sonido("golpe.wav");
            sonidoVictoria = new Sonido("victoria.wav");
            sonidoDerrota = new Sonido("derrota.wav");

            fondoJuego = new ImageIcon("fondo_juego.jpg").getImage();
            fondoVictoria = new ImageIcon("fondo_victoria.jpg").getImage();
            fondoDerrota = new ImageIcon("fondo_derrota.png").getImage();
        } catch (Exception e) {
            System.out.println("Error cargando recursos: " + e.getMessage());
        }
    }

    private void inicializarJuego() {
        jugador = new Jugador(20, 500, 40, 40, this);
        entidades = new ArrayList<>();
        archivo = new ArchivoJuego("progreso.txt");
        esperandoReinicio = false;

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

        if (sonidoFondo != null) {
            sonidoFondo.loop();
        }

        timer = new Timer(16, this);
        timer.start();
    }

    public void actionPerformed(ActionEvent e) {
        if (esperandoReinicio) {
            return;
        }

        if (!jugador.estaVivo() || jugador.haSalvadoEsposa()) {
            timer.stop();
            if (!jugador.estaVivo() && sonidoDerrota != null) {
                sonidoDerrota.play();
            } else if (sonidoVictoria != null) {
                sonidoVictoria.play();
            }
            esperandoReinicio = true;
        } else {
            jugador.actualizar();
            jugador.verificarColisiones(entidades);
            actualizarSierras();
        }
        repaint();
    }

    private void actualizarSierras() {
        contadorSierras++;
        if (contadorSierras >= intervaloSierras) {
            int ancho = 40;
            int alto = 40;
            int x = (int) (Math.random() * (getWidth() - ancho));
            EnemigoVolador sierra = new EnemigoVolador(x, -alto, ancho, alto);
            sierras.add(sierra);
            contadorSierras = 0;
        }

        Iterator<EnemigoVolador> iter = sierras.iterator();
        while (iter.hasNext()) {
            EnemigoVolador s = iter.next();
            s.actualizar();

            if (s.estaFueraPantalla(getHeight())) {
                iter.remove();
            } else if (s.getBounds().intersects(jugador.getBounds())) {
                if (sonidoGolpe != null) sonidoGolpe.play();
                iter.remove();
            }
        }
    }

    private void reiniciarJuego() {
        if (sonidoFondo != null) sonidoFondo.stop();
        if (sonidoDerrota != null) sonidoDerrota.stop();
        if (sonidoVictoria != null) sonidoVictoria.stop();

        entidades.clear();
        inicializarJuego();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        if (jugador.estaVivo() && !jugador.haSalvadoEsposa()) {
            // Dibujar fondo y juego normal
            g2d.drawImage(fondoJuego, 0, 0, getWidth(), getHeight(), this);
            jugador.dibujar(g2d);

            for (Entidad ent : entidades) {
                ent.dibujar(g2d);
            }

            for (EnemigoVolador s : sierras) {
                s.dibujar(g2d);
            }

            // Dibujar barra de vida
            g2d.setColor(Color.GREEN);
            g2d.fillRect(20, 20, jugador.getVida() * 30, 20);
            g2d.setColor(Color.BLACK);
            g2d.drawRect(20, 20, 90, 20);

        } else if (!jugador.estaVivo()) {
            // Mensaje derrota
            g2d.drawImage(fondoDerrota, 0, 0, getWidth(), getHeight(), this);

            // Fondo semitransparente oscuro
            g2d.setColor(new Color(10, 10, 10, 180));
            g2d.fillRect(0, 0, getWidth(), getHeight());

            // Texto "GAME OVER" con sombra y degradado
            String texto = "GAME OVER";
            Font fontTitle = new Font("Arial Black", Font.BOLD, 72);
            g2d.setFont(fontTitle);
            FontMetrics fm = g2d.getFontMetrics();

            int x = (getWidth() - fm.stringWidth(texto)) / 2;
            int y = getHeight() / 2;

            // Sombra
            g2d.setColor(new Color(100, 0, 0, 150));
            g2d.drawString(texto, x + 4, y + 4);

            // Degradado rojo
            GradientPaint gradient = new GradientPaint(x, y - fm.getAscent(), Color.RED, x, y, Color.ORANGE);
            g2d.setPaint(gradient);
            g2d.drawString(texto, x, y);

            // Mensaje reiniciar
            texto = "Presiona R para reiniciar";
            Font fontSub = new Font("Arial", Font.PLAIN, 32);
            g2d.setFont(fontSub);
            fm = g2d.getFontMetrics();
            x = (getWidth() - fm.stringWidth(texto)) / 2;
            y = getHeight() / 2 + 70;

            // Fondo negro semi-transparente para mejor lectura
            int padding = 10;
            int w = fm.stringWidth(texto) + padding * 2;
            int h = fm.getHeight() + padding;
            g2d.setColor(new Color(0, 0, 0, 180));
            g2d.fillRoundRect(x - padding, y - fm.getAscent() - padding / 2, w, h, 15, 15);

            // Texto blanco con sombra
            g2d.setColor(Color.WHITE);
            g2d.drawString(texto, x, y);
            g2d.setColor(new Color(0, 0, 0, 100));
            g2d.drawString(texto, x + 2, y + 2);

        } else if (jugador.haSalvadoEsposa()) {
            // Pantalla de Victoria
            g2d.drawImage(fondoVictoria, 0, 0, getWidth(), getHeight(), this);

            // Fondo semitransparente azul oscuro
            g2d.setColor(new Color(0, 0, 70, 180));
            g2d.fillRect(0, 0, getWidth(), getHeight());

            // Texto "¡VICTORIA!" con sombra y brillo
            String texto = "¡VICTORIA!";
            Font fontTitle = new Font("Verdana", Font.BOLD, 72);
            g2d.setFont(fontTitle);
            FontMetrics fm = g2d.getFontMetrics();

            int x = (getWidth() - fm.stringWidth(texto)) / 2;
            int y = getHeight() / 2;

            // Sombra negra
            g2d.setColor(new Color(0, 0, 0, 140));
            g2d.drawString(texto, x + 3, y + 3);

            // Texto amarillo brillante con borde difuso
            g2d.setColor(Color.YELLOW);
            g2d.drawString(texto, x, y);

            // Añadir brillo (efecto glow)
            for (int i = 1; i <= 5; i++) {
                g2d.setColor(new Color(255, 255, 150, 50 / i));
                g2d.setStroke(new BasicStroke(i));
                g2d.drawString(texto, x, y);
            }

            // Mensaje reiniciar
            texto = "¡Salvaste a tu esposa! Presiona R para reiniciar";
            Font fontSub = new Font("Arial", Font.PLAIN, 32);
            g2d.setFont(fontSub);
            fm = g2d.getFontMetrics();
            x = (getWidth() - fm.stringWidth(texto)) / 2;
            y = getHeight() / 2 + 70;

            // Fondo negro semi-transparente con bordes redondeados
            int padding = 10;
            int w = fm.stringWidth(texto) + padding * 2;
            int h = fm.getHeight() + padding;
            g2d.setColor(new Color(0, 0, 0, 180));
            g2d.fillRoundRect(x - padding, y - fm.getAscent() - padding / 2, w, h, 15, 15);

            // Texto blanco con sombra sutil
            g2d.setColor(Color.WHITE);
            g2d.drawString(texto, x, y);
            g2d.setColor(new Color(0, 0, 0, 100));
            g2d.drawString(texto, x + 2, y + 2);
        }
    }

    public void keyPressed(KeyEvent e) {
        if (esperandoReinicio && e.getKeyCode() == KeyEvent.VK_R) {
            reiniciarJuego();
            return;
        }

        if (e.getKeyCode() == KeyEvent.VK_LEFT) jugador.setIzquierda(true);
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) jugador.setDerecha(true);
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            jugador.saltar();
            if (sonidoSalto != null) sonidoSalto.play();
        }
        if (e.getKeyCode() == KeyEvent.VK_S) archivo.guardar(jugador);
    }

    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) jugador.setIzquierda(false);
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) jugador.setDerecha(false);
    }

    public void keyTyped(KeyEvent e) {}
}
