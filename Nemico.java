import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Nemico {

    private Coordinata posizione;
    private int raggio;
    
    // Velocità di movimento
    private int velocitaX;
    private int velocitaY;

    public Nemico(Coordinata posizioneIniziale, int raggio, int velocitaX, int velocitaY) {
        this.posizione = posizioneIniziale;
        this.raggio = raggio;
        this.velocitaX = velocitaX;
        this.velocitaY = velocitaY;
    }

    public void muovi() {
        posizione.Sposta(velocitaX, velocitaY);
    }
    //disegna il nemico
    public void disegna(Graphics g) {
        g.setColor(Color.BLUE); 
        g.fillOval(posizione.getX() - raggio, posizione.getY() - raggio, raggio * 2, raggio * 2);
    }

    //--- metodi x gestire collisioni ---/

    public int getSinistra() {
        return posizione.getX() - raggio;
    }

    public int getDestra() {
        return posizione.getX() + raggio;
    }

    public int getSopra() {
        return posizione.getY() - raggio;
    }

    public int getSotto() {
        return posizione.getY() + raggio;
    }

    // Get
    public int getVelocitaX() { 
        return velocitaX; 
    }
    public int getVelocitaY() { 
        return velocitaY; 
    }
    
}