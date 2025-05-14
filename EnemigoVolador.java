import java.awt.*;
import java.util.List;

public class EnemigoVolador extends Enemigo {
    public EnemigoVolador(int x, int y, int ancho, int alto, int vida) {
        super(x, y, ancho, alto, vida);
    }

    public void dibujar(Graphics g) {
        g.setColor(Color.MAGENTA);
        g.fillOval(x, y, ancho, alto);
    }

    @Override
    public void morir() {
        System.out.println("El enemigo volador ha muerto");
    }

    // @Override
    //public void actualizar(Jugador jugador, List<Entidad> entidades) {
    // }
}
