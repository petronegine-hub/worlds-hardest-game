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
        if (e.getKeyChar()=='w') 
        {
            pannelloSuCuiLavorare.relativeMoveSquare(0, -10);
        }
        else if(e.getKeyChar()=='s')
            { 
            pannelloSuCuiLavorare.relativeMoveSquare(0, +10);
            }
        else if(e.getKeyChar()=='a')
            { 
            pannelloSuCuiLavorare.relativeMoveSquare(-10, 0);
            }   
         else if(e.getKeyChar()=='d')
            { 
            pannelloSuCuiLavorare.relativeMoveSquare(+10, 0);
            }   
    }
    @Override
    public void keyReleased(java.awt.event.KeyEvent e) {
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'keyReleased'");
    }

   
}
