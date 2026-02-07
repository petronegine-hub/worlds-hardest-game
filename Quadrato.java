import java.awt.Color;
import java.awt.Graphics;

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

    public void disegna(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(coordinate.getX(), coordinate.getY(), largezza, altezza);
    }
}
