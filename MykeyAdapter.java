import java.awt.event.KeyListener;

public class MykeyAdapter implements KeyListener{

    MyPanel pannelloSuCuiLavorare;
    public MykeyAdapter(MyPanel p){
        this.pannelloSuCuiLavorare = p;
    }
    @Override
    public void keyTyped(java.awt.event.KeyEvent e) {
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'keyTyped'");
    }
    @Override
    public void keyPressed(java.awt.event.KeyEvent e) {
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'keyPressed'");
        //System.out.println(e.getKeyCode()); 
    }
    
    @Override
    public void keyReleased(java.awt.event.KeyEvent e) {
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'keyReleased'");
    }

   
}
