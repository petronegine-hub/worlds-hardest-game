import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Nemico {

    private Coordinata posizione;
    private int raggio;
    
    //velcoità di movimento;
    private int velocitaX;
    private int velocitaY;

    public Nemico(Coordinata posizioneIniziale, int raggio, int velocitaX, int velocitaY) {
        this.posizione = posizioneIniziale;
        this.raggio = raggio;
        this.velocitaX = velocitaX;
        this.velocitaY = velocitaY;
    }

    public Coordinata getPosizione() {
        return posizione;
    }

    public int getRaggio() {
        return raggio;
    }

    public void muovi() {
        posizione.Sposta(velocitaX, velocitaY);
    }

    public Rectangle getHitBox() {
        return new Rectangle(posizione.getX() - raggio, posizione.getY() - raggio, raggio * 2, raggio * 2);
    }

    public void disegna(Graphics g) {
        g.setColor(Color.RED);
        g.fillOval(posizione.getX() - raggio, posizione.getY() - raggio, raggio * 2, raggio * 2);
    }
}