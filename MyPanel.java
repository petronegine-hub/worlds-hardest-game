import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JPanel; 
import javax.swing.Timer;

import java.awt.event.KeyEvent;

class MyPanel extends JPanel {

    private int squareW = 20;
    private int squareH = 20;
    private int squareX = 50;
    private int squareY = 50;
    private Livello livello;
    private GestoreGioco gestore;

    private Coordinata c = new Coordinata(50, 50);
    private Quadrato quadrato = new Quadrato(c, squareH, squareW);
    private MykeyAdapter key;

    public MyPanel() {
        setBorder(BorderFactory.createLineBorder(Color.black));     
        setFocusable(true);
        requestFocusInWindow();

        // creo livello con punto di spawn
        livello = new Livello(new Coordinata(50, 50));
        
        // Aggiungo dei muri 
        livello.aggiungiMuro(150, 0, 20, 150);  // muro verticale
        livello.aggiungiMuro(0, 200, 200, 20); // muro orizzontale
        
        // Aggiungo dei nemici
        // Parametri: x, y, raggio, velX, velY, tipo (0:Orizz, 1:Vert, 2:Diag)
        livello.aggiungiNemico(100, 100, 10, 3, 0, 0); // Pallina orizzontale
        livello.aggiungiNemico(200, 50, 10, 0, 3, 1);  // Pallina verticale
        livello.aggiungiNemico(30, 150, 10, 2, 2, 2);  // Pallina diagonale

        // Inizializzo il Gestore del gioco 
        gestore = new GestoreGioco(quadrato, livello);

        key = new MykeyAdapter(this);
        addKeyListener(key);

        // Il Timer ora gestisce sia il movimento del giocatore che dei nemici
        Timer gameLoop = new Timer(10, e -> {
            updateMovement();   // Muove il quadrato
            updateLogica();     // Muove i nemici e controlla collisioni con giocatore 
            repaint();
        });
        gameLoop.start();

    }

    private void updateLogica() {
        // Muove ogni nemico e gestisce i rimbalzi
        for (Nemico n : livello.getNemici()) {
            // Passiamo larghezza, altezza del pannello e la lista dei muri
            n.aggiorna(getWidth(), getHeight(), livello.getMuri());
        }
        
        // Controlla se il quadrato tocca un nemico (morte)
        gestore.eseguiCicloLogico(); 
    }


    public Quadrato getQuadrato() { 
        return quadrato; 
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(1000,800);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);       
            
        // Disegna i muri
        for (Muro m : livello.getMuri()) {
            m.disegna(g);
        }
        
        // Disegna i nemici
        for (Nemico n : livello.getNemici()) {
            n.disegna(g);
        }
        
        quadrato.disegna(g);
    }   

    public void relativeMoveSquare(int dx, int dy) {
        int newX = quadrato.getCoordinate().getX() + dx;
        int newY = quadrato.getCoordinate().getY() + dy;

        // Controllo bordi orizzontali
        if (newX < 0) newX = 0;
        else if (newX + quadrato.getLargezza() > getWidth()) {
            newX = getWidth() - quadrato.getLargezza();
        }

        // Controllo bordi verticali
        if (newY < 0) newY = 0;
        else if (newY + quadrato.getAltezza() > getHeight()) {
            newY = getHeight() - quadrato.getAltezza();
        }

        // Aggiorna le coordinate effettive dell'oggetto Quadrato
        quadrato.getCoordinate().setX(newX);
        quadrato.getCoordinate().setY(newY);

        repaint(); 
    }

    private void updateMovement() {
        Set<Integer> activeKeys = key.getActiveKeys();
        double dx = 0;
        double dy = 0;
        double velocita = 2.0;

        // Rileviamo la direzione desiderata (Input)
        if (activeKeys.contains(KeyEvent.VK_W)) dy -= 1;
        if (activeKeys.contains(KeyEvent.VK_S)) dy += 1;
        if (activeKeys.contains(KeyEvent.VK_A)) dx -= 1;
        if (activeKeys.contains(KeyEvent.VK_D)) dx += 1;

        // Se c'è movimento
        if (dx != 0 || dy != 0) {
            // per muovere alla stessa velocità anche in diagonale, normalizziamo il vettore sennò andrebbe più veloce in diagonale 
            double lunghezza = Math.sqrt(dx * dx + dy * dy);
            int spostamentoX = (int) Math.round((dx / lunghezza) * velocita);
            int spostamentoY = (int) Math.round((dy / lunghezza) * velocita);

            relativeMoveSquare(spostamentoX, spostamentoY);
        }
    }
}
