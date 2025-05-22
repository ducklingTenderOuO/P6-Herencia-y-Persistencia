import java.awt.*;
import java.util.List;
import java.util.Iterator;
import javax.swing.ImageIcon;
import java.awt.Image;

public class Jugador extends Entidad {
    private int dy = 0;
    private int vida = 3;
    private Image imagen;
    private boolean salvoEsposa = false;
    private boolean izquierda = false, derecha = false, enSuelo = false;

    public Jugador(int x, int y, int ancho, int alto) {

        super(x, y, ancho, alto);
        ImageIcon icono = new ImageIcon("jugador.png");
        imagen = icono.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
    }

    public void actualizar() {
        if (izquierda) x -= 4;
        if (derecha) x += 4;
        dy += 1;
        y += dy;
    }

    public void verificarColisiones(List<Entidad> entidades) {
        enSuelo = false;

        Iterator<Entidad> it = entidades.iterator();

        while (it.hasNext()) {
            Entidad e = it.next();

            if (e instanceof Plataforma && getRect().intersects(e.getRect())) {
                y = e.getRect().y - alto;
                dy = 0;
                enSuelo = true;
            }

            if (e instanceof Enemigo && getRect().intersects(e.getRect())) {
                vida--;
                System.out.println("¡El jugador fue golpeado! Salud restante: " + vida);
                x = 50;
                y = 500;
                dy = 0;

                if (vida <= 0) {
                    System.out.println("¡El jugador ha muerto!");
                }
            }

            if (e instanceof Cura && getRect().intersects(e.getRect())) {
                vida++;
                System.out.println("¡Vida recogida! Vidas: " + vida);
                it.remove();
            }
            if (e instanceof Esposa && getRect().intersects(e.getRect())) {
                salvoEsposa = true;
                System.out.println("¡Salvaste a tu esposa!");
            }

        }
    }

    public void dibujar(Graphics g) {
        g.drawImage(imagen, x, y, null);
        g.setColor(Color.BLACK);
        g.drawString("Vidas: " + vida, 10, 10);
    }

    public void saltar() {
        if (enSuelo) dy = -15;
    }

    public void setIzquierda(boolean b) {
        izquierda = b;
    }

    public void setDerecha(boolean b) {
        derecha = b;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getVida() {
        return vida;
    }

    public boolean estaVivo() {
        return vida > 0;
    }

    public boolean haSalvadoEsposa() {
        return salvoEsposa;

    }
}
