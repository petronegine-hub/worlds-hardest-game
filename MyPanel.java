import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.BorderFactory;
import javax.swing.JPanel; 

class MyPanel extends JPanel {

    private int squareW = 20;
    private int squareH = 20;

    private Coordinata c = new Coordinata(50, 50);
    private Quadrato quadrato = new Quadrato(c, squareH, squareW);

    public MyPanel() {
        setBorder(BorderFactory.createLineBorder(Color.black));
        
        setFocusable(true);
        requestFocusInWindow();

        MykeyAdapter key = new MykeyAdapter(this);
        addKeyListener(key);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(250,200);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);       
       
        g.fillRect(quadrato.getCoordinate().getX(), quadrato.getCoordinate().getY(), quadrato.getLargezza(), quadrato.getAltezza());
    }   

    public void relativeMoveSquare(int deltaX, int deltaY) {
       
        int xAbs = quadrato.getCoordinate().getX() + deltaX;
        int yAbs = quadrato.getCoordinate().getY() + deltaY;

        moveSquare(xAbs, yAbs);
    }

    public void moveSquare(int x, int y) {
        //controlla i bordi di sinistra e destra
        if (x < 0) 
        {
            x = 0;
        } 
        else if (x + quadrato.getLargezza() > this.getWidth()) 
        {
            x = this.getWidth() - quadrato.getLargezza();
        }
        //controllo dei bordi in alto e in basso
        if (y < 0) 
        {
            y = 0;
        } 
        else if (y + quadrato.getAltezza() > this.getHeight()) 
        {
            y = this.getHeight() - quadrato.getAltezza();
        }
        //modivica la x e la y 
        quadrato.getCoordinate().setX(x);
        quadrato.getCoordinate().setY(y);

        repaint();
    }
}
