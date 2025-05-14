import java.awt.*;
import java.awt.Graphics;

public class Jugador {
    protected int x, y;
    private int velocidad;
    private int vida;
    private long tiempoUltimoDaño;

    public Jugador(int x, int y, int velocidad) {
        this.x = x;
        this.y = y;
        this.velocidad = velocidad;
        this.vida = 5;
        this.tiempoUltimoDaño = 0;
    }

    public void moverArriba() { y -= velocidad; }
    public void moverAbajo() { y += velocidad; }
    public void moverIzquierda() { x -= velocidad; }
    public void moverDerecha() { x += velocidad; }

    public int getX() { return x; }
    public int getY() { return y; }
    public int getVida() { return vida; }

    public void recibirDaño(int cantidad) {
        long ahora = System.currentTimeMillis();
        if (ahora - tiempoUltimoDaño >= 1000) {
            vida -= cantidad;
            tiempoUltimoDaño = ahora;
        }
    }

    public boolean estaVivo() {
        return vida > 0;
    }

    public void dibujar(Graphics g) {
        g.fillRect(x, y, 50, 50);
    }

}