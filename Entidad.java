import java.awt.*;

public abstract class Entidad {
    protected int x, y, ancho, alto;

    public Entidad(int x, int y, int ancho, int alto) {
        this.x = x;
        this.y = y;
        this.ancho = ancho;
        this.alto = alto;
    }

    public Rectangle getRect() {
        return new Rectangle(x, y, ancho, alto);
    }

    public abstract void dibujar(Graphics g);

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setPosicion(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
