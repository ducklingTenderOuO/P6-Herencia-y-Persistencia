import java.awt.*;
import javax.swing.ImageIcon;
import java.awt.Image;

public class EnemigoVolador extends Enemigo {
    private Image imagen;
    private int velocidadY = 5; // Velocidad de caída

    public EnemigoVolador(int x, int y, int ancho, int alto) {
        super(x, y, ancho, alto);
        ImageIcon icono = new ImageIcon("sierra.png");
        imagen = icono.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
    }

    public void actualizar() {
        y += velocidadY;  // La sierra cae hacia abajo
    }

    public void dibujar(Graphics g) {
        g.drawImage(imagen, x, y, null);
    }

    public boolean estaFueraPantalla(int alturaPantalla) {
        return y > alturaPantalla;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, ancho, alto);
    }

}
