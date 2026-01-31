import java.awt.Color;
import java.awt.Graphics;

public class Moneta {
    
    private Coordinata posizione;
    private int raggio;
    private boolean presa;

    public Moneta(Coordinata posizione, int raggio) {
        this.posizione = posizione;
        this.raggio = raggio;
        this.presa = false; 
    }

    public void disegna(Graphics g) {
        //se la moneta non è stata presa la disegno altrimenti no 
        if (!presa) {
            g.setColor(Color.YELLOW);
            g.fillOval(posizione.getX() - raggio, posizione.getY() - raggio, raggio * 2, raggio * 2);

            //bordino nero per vederla meglio (da rivedere perchè non mi ispira troppo)
            g.setColor(Color.BLACK);
            g.drawOval(posizione.getX() - raggio, posizione.getY() - raggio, raggio * 2, raggio * 2);
        }
    }

    // get e set
    public boolean isPresa() {
        return presa;
    }

    public void setPresa(boolean presa) {
        this.presa = presa;
    }

    public int getX() {
        return posizione.getX();
    }

    public int getY() {
        return posizione.getY();
    }

    public int getRaggio() {
        return raggio;
    }
    
    public Coordinata getCoordinata() {
        return posizione;
    }
}