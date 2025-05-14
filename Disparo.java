import java.awt.*;

public class Disparo extends Ataque {
    public Disparo(int x, int y, boolean direccionDerecha) {
        super(x, y, 10, 5, direccionDerecha ? 6 : -6);  // Velocidad dependiendo de la dirección
    }

    @Override
    public void actualizar() {
        super.actualizar();
    }

    @Override
    public void dibujar(Graphics g) {
        g.setColor(Color.BLUE);  // Color del disparo
        g.fillRect(x, y, ancho, alto);  // Dibuja el disparo
    }

    @Override
    public void aplicarEfecto(Enemigo enemigo) {
        enemigo.recibirDaño(getDaño());  // Aplica el daño al enemigo
    }
}
