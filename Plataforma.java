import java.awt.*;
import javax.swing.ImageIcon;

public class Plataforma extends Entidad {
    private Image imagen;

    public Plataforma(int x, int y, int ancho, int alto) {
        super(x, y, ancho, alto);
        try {
            ImageIcon icono = new ImageIcon("plataforma.jpg");
            imagen = icono.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
        } catch (Exception e) {
            System.out.println("Error cargando imagen de plataforma");
            imagen = null;
        }
    }

    public void dibujar(Graphics g) {
        if (imagen != null) {
            g.drawImage(imagen, x, y, null);
        } else {
            g.setColor(Color.DARK_GRAY);
            g.fillRect(x, y, ancho, alto);
        }
    }
}
