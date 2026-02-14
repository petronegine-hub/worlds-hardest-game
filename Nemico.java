import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Nemico extends Thread{

    private Coordinata posizione;
    private int raggio;
    private int tipoMovimento; // 0: Orizzontale, 1: Verticale, 2: Diagonale Libero
    private boolean inEsecuzione = true;
    // Velocità di movimento
    private int velocitaX;
    private int velocitaY;

    public Nemico(Coordinata posizioneIniziale, int raggio, int velocitaX, int velocitaY, int tipo) {
        this.posizione = posizioneIniziale;
        this.raggio = raggio;
        this.velocitaX = velocitaX;
        this.velocitaY = velocitaY;
        this.tipoMovimento = tipo;
    }

    public void muovi() {
        posizione.Sposta(velocitaX, velocitaY);
    }
    
    //disegna il nemico
    public void disegna(Graphics g) {
        g.setColor(Color.BLUE); 
        g.fillOval(posizione.getX() - raggio, posizione.getY() - raggio, raggio * 2, raggio * 2);
    }

    public Rectangle getBounds() {
        return new Rectangle(posizione.getX() - raggio, posizione.getY() - raggio, raggio * 2, raggio * 2);
    }

    //get
    public int getVelocitaX() { 
        return velocitaX; 
    }
    public int getVelocitaY() { 
        return velocitaY; 
    }

    @Override
    public void run() {
        while (inEsecuzione) {
            // Esegue il metodo in base all'intero passato nel costruttore
            if (tipoMovimento == 0) {
                muoviOrizzontale();
            } else if (tipoMovimento == 1) {
                muoviVerticale();
            } else if (tipoMovimento == 2) {
                muoviDiagonale();
            }

            try {
                // Frequenza di aggiornamento standard (~60 FPS)
                Thread.sleep(16);
            } catch (InterruptedException e) {
                inEsecuzione = false;
                Thread.currentThread().interrupt();
            }
        }
    }

    public void muoviOrizzontale() {
        posizione.Sposta(velocitaX, 0);
    }

    public void muoviVerticale() {
        posizione.Sposta(0, velocitaY);
    }

    public void muoviDiagonale() {
        posizione.Sposta(velocitaX, velocitaY);
    }

    public void invertiX() {
        this.velocitaX = -this.velocitaX;
    }

    public void invertiY() {
        this.velocitaY = -this.velocitaY;
    }

}