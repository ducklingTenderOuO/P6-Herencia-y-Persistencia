import java.awt.*;

public abstract class Ataque {
    protected int x, y, ancho, alto, velocidad;
    private boolean estaActivo;
    protected boolean direccionDerecha;

    public Ataque(int x, int y, int ancho, int alto, int velocidad) {
        this.x = x;
        this.y = y;
        this.ancho = ancho;
        this.alto = alto;
        this.velocidad = velocidad;
        this.estaActivo = true;
    }

    public void actualizar() {
        x += velocidad;
        if (x < 0 || x > 1200) {  // Si el disparo sale de la pantalla, lo desactivamos
            desactivar();
        }
    }

    public void dibujar(Graphics g) {
        g.setColor(Color.RED);  // Color del ataque, puedes cambiarlo
        g.fillRect(x, y, ancho, alto);  // Dibuja el ataque como un rectángulo
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, ancho, alto);  // Devuelve un área de colisión
    }

    public boolean estaActivo() {
        return estaActivo;
    }

    public void desactivar() {
        this.estaActivo = false;  // Desactiva el ataque
    }

    public abstract void aplicarEfecto(Enemigo enemigo);

    public int getDaño() {
        return 1;  // Daño por defecto, puede ser modificado
    }
}