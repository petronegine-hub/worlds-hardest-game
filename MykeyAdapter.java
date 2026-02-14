import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;


public class MykeyAdapter implements KeyListener{

    MyPanel pannelloSuCuiLavorare;
    Set<Integer> keys = new HashSet<>();    

    public MykeyAdapter(MyPanel p){
        this.pannelloSuCuiLavorare = p;
    }
    
    @Override
    public void keyTyped(java.awt.event.KeyEvent e) {
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'keyTyped'");
    }
    

    @Override
    public void keyPressed(KeyEvent e) {
        // TODO Auto-generated method stub
        keys.add(e.getKeyCode()); // Aggiunge il tasto premuto al set
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
        keys.remove(e.getKeyCode()); // Rimuove il tasto quando rilasciato
    }

    // Aggiungi questo metodo per far leggere i tasti al pannello
    public Set<Integer> getActiveKeys() {
        return keys;
    }

    private void muoviQuadrato() {
        int dx = 0;
        int dy = 0;
        int velocita = 5; // Pixel di spostamento per pressione

        // Controlla i tasti nel set per gestire le diagonali (es. W + D insieme)
        if (keys.contains(KeyEvent.VK_W)) dy -= velocita;
        if (keys.contains(KeyEvent.VK_S)) dy += velocita;
        if (keys.contains(KeyEvent.VK_A)) dx -= velocita;
        if (keys.contains(KeyEvent.VK_D)) dx += velocita;

        pannelloSuCuiLavorare.relativeMoveSquare(dx, dy);
    }   

}
