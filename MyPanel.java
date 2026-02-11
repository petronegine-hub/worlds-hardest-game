import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.BorderFactory;
import javax.swing.JPanel; 

class MyPanel extends JPanel {

    private int squareW = 20;
    private int squareH = 20;
    private int squareX = 50;
    private int squareY = 50;

    private Coordinata c = new Coordinata(50, 50);
    private Quadrato quadrato = new Quadrato(c, squareH, squareW);

    public MyPanel() {
        setBorder(BorderFactory.createLineBorder(Color.black));
        
        setFocusable(true);
        requestFocusInWindow();

        MykeyAdapter key = new MykeyAdapter(this);
        addKeyListener(key);
    }

    public Quadrato getQuadrato() { 
        return quadrato; 
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(250,200);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);       
        
        quadrato.disegna(g);
    }   

    public void moveSquare(int x, int y) {
        if ((squareX==x) && (squareY==y)) 
            return;

        if(x < 0){
            x = 0;
        } else if(x+squareW > this.getWidth()){
            x = this.getWidth()-squareH;
        }
            
        if(y < 0){
            y = 0;
        } else if(y+squareH > this.getHeight()){
            y = this.getHeight()-squareH;
        }

        squareX=x;
        squareY=y;
        repaint();
    }

    public void relativeMoveSquare(int dx, int dy) {
        // Muoviamo l'oggetto Quadrato usando il suo metodo interno
        this.quadrato.muovi(dx, dy);
        
        // Sincronizziamo le variabili squareX/Y per i controlli dei bordi
        this.squareX = quadrato.getCoordinate().getX();
        this.squareY = quadrato.getCoordinate().getY();

        repaint(); // Ridipinge il pannello con la nuova posizione
    }

}
