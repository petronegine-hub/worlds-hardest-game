import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.List;

public class Nemico {

    private Coordinata posizione;
    private int raggio;
    private int tipoMovimento; // 0: Orizzontale, 1: Verticale, 2: Diagonale
    private int velocitaX;
    private int velocitaY;

    public Nemico(Coordinata posizioneIniziale, int raggio, int velocitaX, int velocitaY, int tipo) {
        this.posizione = posizioneIniziale;
        this.raggio = raggio;
        this.velocitaX = velocitaX;
        this.velocitaY = velocitaY;
        this.tipoMovimento = tipo;
       
        // Setup iniziale della velocità in base al tipo se necessario
        if (tipo == 0) this.velocitaY = 0; // Solo orizzontale
        if (tipo == 1) this.velocitaX = 0; // Solo verticale
    }

    public void aggiorna(int limiteW, int limiteH, List<Muro> muri) {
        posizione.Sposta(velocitaX, velocitaY);
        if (posizione.getX() - raggio < 0 || posizione.getX() + raggio > limiteW) {
            invertiX();
        }
        if (posizione.getY() - raggio < 0 || posizione.getY() + raggio > limiteH) {
            invertiY();
        }
        Rectangle hitbox = getBounds();
        for (Muro m : muri) {
            Rectangle rectMuro = new Rectangle(m.getX(), m.getY(), m.getLarghezza(), m.getAltezza());
            if (hitbox.intersects(rectMuro)) {          
                if (tipoMovimento == 0 || tipoMovimento == 2) invertiX();
                if (tipoMovimento == 1 || tipoMovimento == 2) invertiY();
                posizione.Sposta(velocitaX, velocitaY);
                break;
            }
        }
    }

    public void disegna(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillOval(posizione.getX() - raggio, posizione.getY() - raggio, raggio * 2, raggio * 2);
    }

    public Rectangle getBounds() {
        return new Rectangle(posizione.getX() - raggio, posizione.getY() - raggio, raggio * 2, raggio * 2);
    }

    public void invertiX() { this.velocitaX = -this.velocitaX; }
    public void invertiY() { this.velocitaY = -this.velocitaY; }
    public int getVelocitaX() { return velocitaX; }
    public int getVelocitaY() { return velocitaY; }
}