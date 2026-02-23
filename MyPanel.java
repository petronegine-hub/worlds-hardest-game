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
        //Impostazioni del pannello: bordo e focus per ricevere input da tastiera
        setBorder(BorderFactory.createLineBorder(Color.black));     
        setFocusable(true);
        requestFocusInWindow();

        // Inizializzazione oggetti
        quadrato = new Quadrato(new Coordinata(0, 0), squareH, squareW);
        this.livello = new Livello(new Coordinata(0,0));
        gestore = new GestoreGioco(quadrato, livello, this);

        // Configurazione ascolto tastiera
        key = new MykeyAdapter(this);
        addKeyListener(key);

        caricaLivello(1);
        
        // Game Loop: aggiorna movimento, logica e grafica ogni 10ms (100 FPS teorici)
        Timer gameLoop = new Timer(10, e -> {
            updateMovement();
            updateLogica();
            repaint();
        });
        gameLoop.start();
    }

    // Aggiorna la posizione dei nemici e invoca i controlli del GestoreGioco
    private void updateLogica() {
        if (livello != null) {
            for (Nemico n : livello.getNemici()) {
                n.aggiorna(getWidth(), getHeight(), livello.getMuri(), livello.getAreaPartenza(), livello.getAreaArrivo());
            }
            gestore.eseguiCicloLogico(); 
        }
    }

    // Seleziona e configura il livello specifico, resettando lo stato del gestore
    public void caricaLivello(int numero) {
        Livello nuovoLivello = new Livello(new Coordinata(0, 0));

        switch (numero) {
            case 1: configuraLivelloVerticale(nuovoLivello);break;
            case 2: configuraLivello2(nuovoLivello); break;
            case 3: configuraLivello3(nuovoLivello); break;
            case 4: configuraLivelloVerticale(nuovoLivello); break;
            case 5: LivelloPixelArt(nuovoLivello); break;
            default:
                // Stato di vittoria finale se i livelli sono terminati
                this.livello = new Livello(new Coordinata(0,0));
                gestore.cambiaLivello(this.livello);
                this.giocoFinito = true;
                repaint();
            return;
        }

        this.livello = nuovoLivello;
        gestore.cambiaLivello(nuovoLivello); 
    }

    private void configuraLivelloVerticale(Livello l) {
        l.setSpawnPoint(new Coordinata(500, 720));
        int sp = 5;
        l.aggiungiMuro(350, 50, sp, 700);    // Muro sinistro
        l.aggiungiMuro(650, 50, sp, 700);    // Muro destro
        l.aggiungiMuro(350, 50, 300, sp);   // Muro sopra
        l.aggiungiMuro(350, 750, 305, sp);  // Muro sotto
        l.setAreaPartenza(355, 650, 295, 100); 
        l.setAreaArrivo(355, 55, 295, 80);
        l.aggiungiCheckpoint(355, 350, 295, 60);
        l.aggiungiNemico(400, 600, 10, 6, 0, 0); // Orizzontali
        l.aggiungiNemico(600, 500, 10, -6, 0, 0); // Orizzontali
        l.aggiungiNemico(500, 400, 8, 0, 12, 1);  // Verticali
        l.aggiungiNemico(450, 250, 7, 5, 5, 2);   // Diagonali
        l.aggiungiNemico(550, 250, 7, -5, -5, 2); // Diagonali
        l.aggiungiNemico(360, 150, 6, 4, 0, 0);   // Orizzontali
        l.aggiungiNemico(645, 180, 6, -4, 0, 0);  // Orizzontali
        l.aggiungiMoneta(500, 550, 7); 
        l.aggiungiMoneta(400, 380, 7); 
        l.aggiungiMoneta(600, 380, 7);
        l.aggiungiMoneta(500, 120, 7);
    }

    private void configuraLivello3(Livello l) {
        l.setSpawnPoint(new Coordinata(80, 400));
        int sp = 5;
        l.aggiungiMuro(50, 150, 900, sp);   // Muro sopra
        l.aggiungiMuro(50, 650, 900, sp);   // Muro sotto
        l.aggiungiMuro(50, 150, sp, 505);   // Muro sinistro
        l.aggiungiMuro(950, 150, sp, 505);  // Muro destro
        l.aggiungiMuro(475, 150, 50, 120);  // Muro interno
        l.aggiungiMuro(475, 530, 50, 120);  // Muro interno
        l.aggiungiMuro(180, 385, 120, 30);  // Muro interno
        l.aggiungiMuro(700, 385, 120, 30);  // Muro interno
        l.aggiungiCheckpoint(485, 385, 30, 30);
        l.setAreaPartenza(55, 300, 100, 200);
        l.setAreaArrivo(850, 300, 100, 200);
        int velVortice = 4;
        for (int i = 0; i < 2; i++) {
            l.aggiungiNemico(500, 400, 10, velVortice, velVortice, 2);   // Diagonali
            l.aggiungiNemico(500, 400, 10, -velVortice, -velVortice, 2); // Diagonali
            l.aggiungiNemico(500, 400, 10, velVortice, -velVortice, 2);  // Diagonali
            l.aggiungiNemico(500, 400, 10, -velVortice, velVortice, 2);  // Diagonali
        }
        for (int y = 200; y <= 300; y += 50) {
            l.aggiungiNemico(300, y, 7, 6, 0, 0);  // Orizzontali
            l.aggiungiNemico(700, y, 7, -6, 0, 0); // Orizzontali
        }
        for (int y = 500; y <= 600; y += 50) {
            l.aggiungiNemico(300, y, 7, 6, 0, 0);  // Orizzontali
            l.aggiungiNemico(700, y, 7, -6, 0, 0); // Orizzontali
        }
        for (int x = 200; x <= 300; x += 50) {
            l.aggiungiNemico(x, 400, 6, 0, 5, 1);  // Verticali
        }
        for (int x = 700; x <= 800; x += 50) {
            l.aggiungiNemico(x, 400, 6, 0, -5, 1); // Verticali
        }
        l.aggiungiMoneta(500, 300, 7); 
        l.aggiungiMoneta(500, 500, 7); 
        l.aggiungiMoneta(250, 250, 7); 
        l.aggiungiMoneta(750, 550, 7); 
    }

   private void configuraLivello1(Livello l) {
        l.setSpawnPoint(new Coordinata(80, 390));
        int sp = 5;
        l.aggiungiMuro(50, 350, 900, sp);   // Muro sopra
        l.aggiungiMuro(50, 450, 900, sp);   // Muro sotto
        l.aggiungiMuro(50, 350, sp, 100);   // Muro sinistro
        l.aggiungiMuro(950, 350, sp, 105);  // Muro destro
        l.setAreaPartenza(55, 355, 145, 95);
        l.setAreaArrivo(805, 355, 145, 95);
        int vel = 3;
        l.aggiungiNemico(250, 370, 8, 0, vel, 1);  // Verticali
        l.aggiungiNemico(370, 430, 8, 0, -vel, 1); // Verticali
        l.aggiungiNemico(490, 370, 8, 0, vel, 1);  // Verticali
        l.aggiungiNemico(610, 430, 8, 0, -vel, 1); // Verticali
        l.aggiungiNemico(730, 370, 8, 0, vel, 1);  // Verticali
        l.aggiungiMoneta(310, 440, 6);
        l.aggiungiMoneta(430, 365, 6);
        l.aggiungiMoneta(550, 440, 6);
        l.aggiungiMoneta(670, 365, 6);
    }

    private void configuraLivello2(Livello l) {
        l.setSpawnPoint(new Coordinata(80, 372));
        l.aggiungiMuro(50, 325, 900, 5);  // Muro sopra
        l.aggiungiMuro(50, 430, 900, 5);  // Muro sotto
        l.aggiungiMuro(50, 325, 5, 110);  // Muro sinistro
        l.aggiungiMuro(950, 325, 5, 110); // Muro destro
        l.setAreaPartenza(55, 330, 145, 100);
        l.setAreaArrivo(805, 330, 145, 100);  
        l.aggiungiNemico(250, 340, 8, 6, 0, 0);   // Orizzontali
        l.aggiungiNemico(700, 368, 8, -6, 0, 0);  // Orizzontali
        l.aggiungiNemico(250, 396, 8, 6, 0, 0);   // Orizzontali
        l.aggiungiNemico(700, 422, 8, -6, 0, 0);  // Orizzontali
        l.aggiungiMoneta(500, 381, 6);
    }

    private void LivelloPixelArt(Livello l) {
        l.setSpawnPoint(new Coordinata(65, 230));
        int sp = 5;
        l.aggiungiMuro(50, 200, 900, sp);   // Muro sopra
        l.aggiungiMuro(50, 600, 900, sp);   // Muro sotto
        l.aggiungiMuro(50, 200, sp, 400);   // Muro sinistro
        l.aggiungiMuro(950, 200, sp, 405);  // Muro destro
        l.aggiungiMuro(200, 200, sp, 320);  // Muro interno
        l.aggiungiMuro(350, 280, sp, 325);  // Muro interno
        l.aggiungiMuro(500, 200, sp, 320);  // Muro interno
        l.aggiungiMuro(650, 280, sp, 325);  // Muro interno
        l.aggiungiMuro(800, 200, sp, 320);  // Muro interno
        l.setAreaPartenza(55, 205, 140, 60);
        l.setAreaArrivo(805, 535, 140, 60);
        for (int y = 220; y < 580; y += 45) {
            l.aggiungiNemico(275, y, 7, 0, 10, 1); // Verticali
        }
        for (int y = 300; y < 550; y += 50) {
            l.aggiungiNemico(425, y, 8, (y % 100 == 0 ? 12 : -12), 0, 0); // Orizzontali
        }
        l.aggiungiNemico(575, 400, 10, 8, 8, 2);   // Diagonali
        l.aggiungiNemico(575, 400, 10, -8, -8, 2); // Diagonali
        l.aggiungiNemico(575, 400, 10, 8, -8, 2);  // Diagonali
        for (int i = 0; i < 6; i++) {
            l.aggiungiNemico(725, 220 + (i * 60), 9, 0, 14, 1); // Verticali
        }
        for (int y = 250; y < 530; y += 60) {
            int velocitaOrizzontale = (y % 120 == 0) ? 11 : -11;
            l.aggiungiNemico(875, y, 8, velocitaOrizzontale, 0, 0); // Orizzontali
        }
        l.aggiungiMoneta(275, 220, 6);  
        l.aggiungiMoneta(425, 400, 6);  
        l.aggiungiMoneta(575, 550, 6);  
        l.aggiungiMoneta(875, 250, 6); 
    }

    // Aggiorna le coordinate del quadrato gestendo i bordi fisici del pannello
    public void relativeMoveSquare(int dx, int dy) {
        int newX = quadrato.getCoordinate().getX() + dx;
        int newY = quadrato.getCoordinate().getY() + dy;

        // Blocco uscita orizzontale
        if (newX < 0) newX = 0;
        else if (newX + quadrato.getLargezza() > getWidth()) {
            newX = getWidth() - quadrato.getLargezza();
        }

        // Blocco uscita verticale
        if (newY < 0) newY = 0;
        else if (newY + quadrato.getAltezza() > getHeight()) {
            newY = getHeight() - quadrato.getAltezza();
        }

        quadrato.getCoordinate().setX(newX);
        quadrato.getCoordinate().setY(newY);
        repaint(); 
    }

    // Calcola il vettore di movimento basato sui tasti attivi e normalizza la velocità diagonale
    private void updateMovement() {
        Set<Integer> activeKeys = key.getActiveKeys();
        double dx = 0, dy = 0;
        double velocita = 2.5;

        if (activeKeys.contains(KeyEvent.VK_W)) dy -= 1;
        if (activeKeys.contains(KeyEvent.VK_S)) dy += 1;
        if (activeKeys.contains(KeyEvent.VK_A)) dx -= 1;
        if (activeKeys.contains(KeyEvent.VK_D)) dx += 1;

        if (dx != 0 || dy != 0) {
            // Normalizzazione: evita che il giocatore vada più veloce in diagonale
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
        
        // Disegno aree di gioco e checkpoint
        g.setColor(new Color(150, 255, 150));
        if (livello.getAreaPartenza() != null) {
            Rectangle p = livello.getAreaPartenza();
            g.fillRect(p.x, p.y, p.width, p.height);
        }
        if (livello.getAreaArrivo() != null) {
            Rectangle a = livello.getAreaArrivo();
            g.fillRect(a.x, a.y, a.width, a.height);
        }

        for (Rectangle cp : livello.getCheckpoints()) {
            g.fillRect(cp.x, cp.y, cp.width, cp.height);
        }

        // Disegno componenti del livello
        for (Muro m : livello.getMuri()) m.disegna(g);
        for (Nemico n : livello.getNemici()) n.disegna(g);
        for (Moneta m : livello.getMonete()) m.disegna(g);   
        quadrato.disegna(g);

        // Disegno UI (Contatore morti)
        g.setColor(Color.WHITE);
        g.fillRect(10, 5, 110, 25); 
        g.setColor(Color.BLACK);
        g.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 14));
        g.drawString("MORTI: " + gestore.getMorti(), 15, 22);

        // Disegno messaggi dinamici (moneta raccolta o avvisi)
        String msg = gestore.getMessaggioCorrente();
        if (!msg.isEmpty()) {
            if (msg.contains("MANCANO")) g.setColor(Color.RED);
            else g.setColor(new Color(0, 150, 0));
            
            g.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 16));
            g.drawString(msg, 140, 22);
        }

        // Schermata finale di vittoria
        if (giocoFinito) {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, getWidth(), getHeight());
            g.setColor(Color.YELLOW);
            g.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 80)); 
            String vittoria = "HAI VINTO!";
            int larghezzaTesto = g.getFontMetrics().stringWidth(vittoria);
            g.drawString(vittoria, (getWidth() - larghezzaTesto) / 2, getHeight() / 2);
        }
    }

    // Definisce la dimensione iniziale della finestra di gioco
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(1000, 800);
    }

    public Quadrato getQuadrato() { return quadrato; }
}