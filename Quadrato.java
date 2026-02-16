import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Quadrato {
    
    private Coordinata coordinate;
    private int altezza;
    private int largezza;

    public Quadrato( Coordinata c, int a, int l) 
    {
       coordinate = c;
       altezza = a;
       largezza = l;
    }

    public Coordinata getCoordinate() 
    { 
        return coordinate;
    }

    public int getAltezza() 
    {
        return altezza;
    }

    public int getLargezza() {
        return largezza;
    }

    public Rectangle getBounds() {
        return new Rectangle(coordinate.getX(), coordinate.getY(), largezza, altezza);
    }

    public void disegna(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(coordinate.getX(), coordinate.getY(), largezza, altezza);
    }

    public void muovi(int dx, int dy) {
        coordinate.setX(coordinate.getX() + dx);
        coordinate.setY(coordinate.getY() + dy);
    }

}
