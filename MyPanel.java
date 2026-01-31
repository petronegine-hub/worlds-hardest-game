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

}
