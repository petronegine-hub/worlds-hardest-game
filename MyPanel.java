import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Set;
import javax.swing.BorderFactory;
import javax.swing.JPanel; 
import javax.swing.Timer;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

class MyPanel extends JPanel {

    private int squareW = 20;
    private int squareH = 20;
    private Livello livello;
    private GestoreGioco gestore;
    private Quadrato quadrato;
    private MykeyAdapter key;
    private boolean giocoFinito = false;

    public MyPanel() {
        setBorder(BorderFactory.createLineBorder(Color.black));     
        setFocusable(true);
        requestFocusInWindow();

        // Creo il giocatore in una posizione temporanea (caricaLivello lo sposterà)
        quadrato = new Quadrato(new Coordinata(0, 0), squareH, squareW);

        // Inizializzo il gestore
        gestore = new GestoreGioco(quadrato, null, this);

        key = new MykeyAdapter(this);
        addKeyListener(key);

        caricaLivello(1);
        //Game Loop
        Timer gameLoop = new Timer(10, e -> {
            updateMovement();
            updateLogica();
            repaint();
        });
        gameLoop.start();
    }

    private void updateLogica() {
        if (livello != null) {
            for (Nemico n : livello.getNemici()) {
                n.aggiorna(getWidth(), getHeight(), livello.getMuri());
            }
            gestore.eseguiCicloLogico(); 
        }
    }

    public void caricaLivello(int numero) {
        Livello nuovoLivello = new Livello(new Coordinata(0, 0));

        switch (numero) {
            case 1:
                configuraLivello1(nuovoLivello);
                break;
            case 2:
                configuraLivello2(nuovoLivello);
                break;
            default:
                this.livello = new Livello(new Coordinata(0,0)); // Livello vuoto
                gestore.cambiaLivello(this.livello);
                this.giocoFinito = true; // Attiva la schermata finale
                repaint();
                return;
        }

        this.livello = nuovoLivello;
        gestore.cambiaLivello(nuovoLivello); 
    }

    private void configuraLivello1(Livello l) {
        l.setSpawnPoint(new Coordinata(80, 390));
        int sp = 5; // spessore
        l.aggiungiMuro(50, 350, 900, sp);   
        l.aggiungiMuro(50, 450, 900, sp);   
        l.aggiungiMuro(50, 350, sp, 100);   
        l.aggiungiMuro(950, 350, sp, 105);  
        l.setAreaPartenza(55, 355, 145, 95);
        l.setAreaArrivo(805, 355, 145, 95);

        int vel = 3 ;
        l.aggiungiNemico(250, 370, 8, 0, vel, 2);
        l.aggiungiNemico(370, 430, 8, 0, -vel, 1);
        l.aggiungiNemico(490, 370, 8, 0, vel, 1);
        l.aggiungiNemico(610, 430, 8, 0, -vel, 1);
        l.aggiungiNemico(730, 370, 8, 0, vel, 1);
        l.aggiungiMoneta(490, 440, 6);
    }

    private void configuraLivello2(Livello l) {
    
        l.setSpawnPoint(new Coordinata(80, 372));

        l.aggiungiMuro(50, 325, 900, 5);      // Muro superiore
        l.aggiungiMuro(50, 430, 900, 5);      // Muro inferiore
        l.aggiungiMuro(50, 325, 5, 110);      // Chiusura sinistra
        l.aggiungiMuro(950, 325, 5, 110);     // Chiusura destra

        l.setAreaPartenza(55, 330, 145, 100);
        l.setAreaArrivo(805, 330, 145, 100);  

        l.aggiungiNemico(250, 340, 8, 6, 0, 0);   
        l.aggiungiNemico(700, 368, 8, -6, 0, 0);  
        l.aggiungiNemico(250, 396, 8, 6, 0, 0);   
        l.aggiungiNemico(700, 422, 8, -6, 0, 0);

        l.aggiungiMoneta(500, 381, 6);
    }

    // Metodo richiesto per classi esterne
    public void relativeMoveSquare(int dx, int dy) {
        int newX = quadrato.getCoordinate().getX() + dx;
        int newY = quadrato.getCoordinate().getY() + dy;

        if (newX < 0) newX = 0;
        else if (newX + quadrato.getLargezza() > getWidth()) {
            newX = getWidth() - quadrato.getLargezza();
        }

        if (newY < 0) newY = 0;
        else if (newY + quadrato.getAltezza() > getHeight()) {
            newY = getHeight() - quadrato.getAltezza();
        }

        quadrato.getCoordinate().setX(newX);
        quadrato.getCoordinate().setY(newY);
        repaint(); 
    }

    private void updateMovement() {
        Set<Integer> activeKeys = key.getActiveKeys();
        double dx = 0, dy = 0;
        double velocita = 2.0;

        if (activeKeys.contains(KeyEvent.VK_W)) dy -= 1;
        if (activeKeys.contains(KeyEvent.VK_S)) dy += 1;
        if (activeKeys.contains(KeyEvent.VK_A)) dx -= 1;
        if (activeKeys.contains(KeyEvent.VK_D)) dx += 1;

        if (dx != 0 || dy != 0) {
            double lunghezza = Math.sqrt(dx * dx + dy * dy);
            int spostamentoX = (int) Math.round((dx / lunghezza) * velocita);
            int spostamentoY = (int) Math.round((dy / lunghezza) * velocita);
            gestore.muoviGiocatore(spostamentoX, spostamentoY);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);       
        if (livello == null) return;
        
        // Disegno Aree Verdi
        g.setColor(new Color(150, 255, 150));
        if (livello.getAreaPartenza() != null) {
            Rectangle p = livello.getAreaPartenza();
            g.fillRect(p.x, p.y, p.width, p.height);
        }
        if (livello.getAreaArrivo() != null) {
            Rectangle a = livello.getAreaArrivo();
            g.fillRect(a.x, a.y, a.width, a.height);
        }
        
        for (Muro m : livello.getMuri()) m.disegna(g);
        for (Nemico n : livello.getNemici()) n.disegna(g);
        for (Moneta m : livello.getMonete()) m.disegna(g);   
        quadrato.disegna(g);

        g.setColor(Color.WHITE);
        g.fillRect(10, 5, 100, 25); // Sfondino bianco per il testo
        g.setColor(Color.BLACK);
        g.drawString("MORTI: " + gestore.getMorti(), 15, 22);

        if (giocoFinito) {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, getWidth(), getHeight()); // Sfondo nero coprente

            g.setColor(Color.YELLOW);
            g.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 60));
            String vittoria = "HAI VINTO!";
            int larghezzaTesto = g.getFontMetrics().stringWidth(vittoria);
            g.drawString(vittoria, (getWidth() - larghezzaTesto) / 2, getHeight() / 2);
            return; // Esce dal metodo così non disegna altro
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(1000, 800);
    }

    public Quadrato getQuadrato() { return quadrato; }
}
