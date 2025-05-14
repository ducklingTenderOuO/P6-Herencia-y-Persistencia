import java.awt.*;

public abstract class Enemigo {
    protected int x, y;
    protected int ancho, alto;
    protected int vida;
    private int velocidad;

    public Enemigo(int x, int y, int ancho, int alto, int vida) {
        this.x = x;
        this.y = y;
        this.ancho = ancho;
        this.alto = alto;
        this.vida = vida;
    }

    public abstract void morir();

    public int getX() { return x; }
    public int getY() { return y; }
    public int getVida() { return vida; }

    public boolean estaVivo() {
        return vida > 0;
    }

    public void recibirDaño(int cantidad) {
        vida -= cantidad;
        if (vida <= 0) {
            morir();
        }
    }

    public void moverHacia(Jugador otro) {
        if (x < otro.getX()) moverDerecha();
        else if (x > otro.getX()) moverIzquierda();

        if (y < otro.getY()) moverAbajo();
        else if (y > otro.getY()) moverArriba();
    }

    public void moverArriba() { y -= velocidad; }
    public void moverAbajo() { y += velocidad; }
    public void moverIzquierda() { x -= velocidad; }
    public void moverDerecha() { x += velocidad; }

    public void dibujar(Graphics g) {
        g.fillRect(x, y, 50, 50);
    }
}