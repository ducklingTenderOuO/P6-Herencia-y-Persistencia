import java.awt.*;

public class EnemigoTerrestre extends Enemigo {
    private int velocidad = 2;

    public EnemigoTerrestre(int x, int y, int ancho, int alto, int vida) {
        super(x, y, ancho, alto, vida);
    }

    @Override
    public void morir() {
        System.out.println("El enemigo terrestre ha muerto");
    }

    public void actualizar(Jugador jugador) {
        int dx = jugador.getX() - x;
        int dy = jugador.getY() - y;
        double distancia = Math.sqrt(dx * dx + dy * dy);

        if (distancia != 0) {
            x += (int)(velocidad * dx / distancia);
            y += (int)(velocidad * dy / distancia);
        }
    }

    public void dibujar(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(x, y, ancho, alto);
    }
}