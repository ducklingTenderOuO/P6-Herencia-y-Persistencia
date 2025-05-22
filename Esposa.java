import java.awt.*;
import javax.swing.ImageIcon;
import java.awt.Image;

public class Esposa extends Entidad {
    private Image imagen;
    public Esposa(int x, int y, int ancho, int alto) {
        super(x, y, ancho, alto);
        ImageIcon icono = new ImageIcon("esposa.png");
        imagen = icono.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);

    }

    @Override
    public void dibujar(Graphics g) {
        g.drawImage(imagen, x, y, null);
    }
}
