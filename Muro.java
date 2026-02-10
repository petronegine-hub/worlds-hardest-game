 
import java.awt.Color;
import java.awt.Graphics;

import javafx.scene.shape.Rectangle;

public class Muro {
    
    private Coordinata posizione;
    private int larghezza;
    private int altezza;

    public Muro(Coordinata posizione, int larghezza, int altezza) {
        this.posizione = posizione;
        this.larghezza = larghezza;
        this.altezza = altezza;
    }

    // Disegnare il muro
    public void disegna(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(posizione.getX(), posizione.getY(), larghezza, altezza);
    }

    // get che servono per il metodo per capire se un punto è dentro il muro
    public int getX() { 
        return posizione.getX(); 
    }
    public int getY() { 
        return posizione.getY(); 
    }
    public int getLarghezza() { 
        return larghezza; 
    }
    public int getAltezza() { 
        return altezza; 
    }

    // Metodo per capire se un punto sia x che y si trova dentro il muro quindi utile per bloccare il quadrato
    public Rectangle getHitbox() {
        return new Rectangle(posizione.getX(), posizione.getY(), larghezza, altezza);
    }
}