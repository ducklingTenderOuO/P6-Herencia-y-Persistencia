import java.awt.*;

public class Gema extends Entidad {
    public Gema(int x, int y) {
        super(x, y, 20, 20);
    }

    public void dibujar(Graphics g) {
        g.setColor(Color.MAGENTA);
        g.fillOval(x, y, ancho, alto);
    }
}
