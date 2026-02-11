import java.awt.event.KeyListener;
import javafx.scene.input.KeyEvent;
import java.awt.event.KeyAdapter;

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
        //System.out.println(e.getKeyCode());
        int key = e.getKeyCode();
        int s = 10; // Velocità di movimento

        // --- DIAGONALI ---
        if (key == 81) pannelloSuCuiLavorare.relativeMoveSquare(-s, -s); // Q: Nord-Ovest (Su + Sinistra)
        if (key == 69) pannelloSuCuiLavorare.relativeMoveSquare(s, -s);  // E: Nord-Est (Su + Destra)
        if (key == 90) pannelloSuCuiLavorare.relativeMoveSquare(-s, s);  // Z: Sud-Ovest (Giù + Sinistra)
        if (key == 67) pannelloSuCuiLavorare.relativeMoveSquare(s, s);   // C: Sud-Est (Giù + Destra)

        // --- MOVIMENTI STANDARD (WASD) ---
        if (key == 87) pannelloSuCuiLavorare.relativeMoveSquare(0, -s);  // W: Su
        if (key == 83) pannelloSuCuiLavorare.relativeMoveSquare(0, s);   // S: Giù
        if (key == 65) pannelloSuCuiLavorare.relativeMoveSquare(-s, 0);  // A: Sinistra
        if (key == 68) pannelloSuCuiLavorare.relativeMoveSquare(s, 0);   // D: Destra
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
