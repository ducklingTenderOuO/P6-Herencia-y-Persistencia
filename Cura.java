import java.awt.*;

public class Cura extends Entidad {

    public Cura(int x, int y, int ancho, int alto) {
        super(x, y, ancho, alto);
    }

    @Override
    public void dibujar(Graphics g) {
        g.setColor(Color.GREEN);
        g.fillOval(x, y, ancho, alto);

    }
}
