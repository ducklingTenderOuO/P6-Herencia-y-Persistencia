import java.awt.*;
import javax.swing.ImageIcon;
import java.awt.Image;

public class EnemigoVolador extends Enemigo {
    private Image imagen;
    public EnemigoVolador(int x, int y, int ancho, int alto) {

        super(x, y, ancho, alto);
        ImageIcon icono = new ImageIcon("sierra.png");
        imagen = icono.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
    }

    public void dibujar(Graphics g) {
        g.drawImage(imagen, x, y, null);
    }
}
