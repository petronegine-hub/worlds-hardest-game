import java.awt.Rectangle;

/**
 * La classe GestoreGioco funge da "motore logico" del gioco.
 * Gestisce le interazioni tra giocatore, nemici, monete e progressione dei livelli.
 */
public class GestoreGioco {
    
    // Attributi per lo stato del gioco e riferimenti agli oggetti principali
    private int livelloAttuale = 1;
    private Quadrato giocatore;
    private Livello livelloCorrente;
    private int contatoreMorti;
    private MyPanel pannello; // Riferimento al pannello per aggiornare la grafica
    private boolean mostraAvvisoMonete = false;
    private String messaggioAvviso = "";
    private int timerMessaggio = 0; // Durata del messaggio in frame
    private Coordinata puntoRinascitaCorrente;
 
    
    //Costruttore: inizializza il giocatore, il livello e imposta il punto di rinascita iniziale.
     
    public GestoreGioco(Quadrato giocatore, Livello livelloIniziale, MyPanel pannello) {
        this.giocatore = giocatore;
        this.livelloCorrente = livelloIniziale;
        this.pannello = pannello;
        
        // Impostazione del punto di spawn iniziale (se disponibile nel livello)
        if (livelloIniziale != null && livelloIniziale.getSpawnPoint() != null) {
            this.puntoRinascitaCorrente = new Coordinata(livelloIniziale.getSpawnPoint().getX(), livelloIniziale.getSpawnPoint().getY());
        } else {
            this.puntoRinascitaCorrente = new Coordinata(0, 0);
        }
    }

    
    //Metodo chiamato ripetutamente dal ciclo di gioco principale. Controlla tutte le condizioni di interazione.
    
    public void eseguiCicloLogico() {
        controllaCollisioniNemici();
        controllaRaccoltaMonete();
        controllaCheckpoint();
        controllaVittoria();
        
        // Gestione del timer per la scomparsa dei messaggi testuali a schermo
        if (timerMessaggio > 0) {
            timerMessaggio--;
        } else {
            messaggioAvviso = ""; 
        }
    }

    
    //Verifica se il giocatore tocca un checkpoint per aggiornare il punto di rinascita.
    
    private void controllaCheckpoint() {
        Rectangle rectGiocatore = giocatore.getBounds();
        for (Rectangle cp : livelloCorrente.getCheckpoints()) {
            if (rectGiocatore.intersects(cp)) {
                // Calcola il centro del checkpoint per spawnare il giocatore esattamente in mezzo
                int nuovoX = (int) cp.getCenterX() - (giocatore.getLargezza() / 2);
                int nuovoY = (int) cp.getCenterY() - (giocatore.getAltezza() / 2);
                
                // Aggiorna lo spawn solo se è un nuovo checkpoint rispetto a quello salvato
                if (puntoRinascitaCorrente.getX() != nuovoX || puntoRinascitaCorrente.getY() != nuovoY) {
                    puntoRinascitaCorrente = new Coordinata(nuovoX, nuovoY);
                    messaggioAvviso = "CHECKPOINT RAGGIUNTO!";
                    timerMessaggio = 100;
                }
            }
        }
    }

    
    //Gestisce il movimento del giocatore testando separatamente gli assi per permettere lo scorrimento sui muri.
    
    public void muoviGiocatore(int dx, int dy) {
        int currentX = giocatore.getCoordinate().getX();
        int currentY = giocatore.getCoordinate().getY();
        int w = giocatore.getLargezza();
        int h = giocatore.getAltezza();

        // --- TEST MOVIMENTO ASSE X ---
        int nextX = currentX + dx;
        Rectangle hitboxX = new Rectangle(nextX, currentY, w, h);
        boolean collisioneX = false;

        for (Muro m : livelloCorrente.getMuri()) {
            if (hitboxX.intersects(new Rectangle(m.getX(), m.getY(), m.getLarghezza(), m.getAltezza()))) {
                collisioneX = true;
                break;
            }
        }
        if (!collisioneX) {
            giocatore.getCoordinate().setX(nextX);
        }

        // --- TEST MOVIMENTO ASSE Y ---
        int updatedX = giocatore.getCoordinate().getX(); // Prende la X (potenzialmente già aggiornata)
        int nextY = currentY + dy;
        Rectangle hitboxY = new Rectangle(updatedX, nextY, w, h);
        boolean collisioneY = false;

        for (Muro m : livelloCorrente.getMuri()) {
            if (hitboxY.intersects(new Rectangle(m.getX(), m.getY(), m.getLarghezza(), m.getAltezza()))) {
                collisioneY = true;
                break;
            }
        }
        if (!collisioneY) {
            giocatore.getCoordinate().setY(nextY);
        }
    }

    
    //Controlla l'impatto con i nemici.
    private void controllaCollisioniNemici() {
        Rectangle rectGiocatore = giocatore.getBounds();
        for (Nemico n : livelloCorrente.getNemici()) {
            if (rectGiocatore.intersects(n.getBounds())) {
                resetDopoMorte();
                return; 
            }
        }
    }


    //Gestisce la raccolta delle monete non ancora prese.
    private void controllaRaccoltaMonete() {
        Rectangle rectGiocatore = giocatore.getBounds();
        for (Moneta m : livelloCorrente.getMonete()) {
            if (!m.isPresa()) {
                // Calcola l'hitbox della moneta (che è circolare nel disegno)
                Rectangle rectMoneta = new Rectangle(m.getX() - m.getRaggio(), m.getY() - m.getRaggio(), m.getRaggio()*2, m.getRaggio()*2);
                if (rectGiocatore.intersects(rectMoneta)) {
                    m.setPresa(true);
                    messaggioAvviso = "MONETA RACCOLTA!";
                    timerMessaggio = 150; 
                }
            }
        }
    }

    
    //Controlla se il giocatore è arrivato al traguardo e se ha soddisfatto le condizioni di vittoria.
    private void controllaVittoria() {
        Rectangle areaArrivo = livelloCorrente.getAreaArrivo();
        if (areaArrivo != null && giocatore.getBounds().intersects(areaArrivo)) {
            boolean tuttePrese = true;
            // Verifica che non ci siano monete rimaste nel livello
            for (Moneta m : livelloCorrente.getMonete()) {
                if (!m.isPresa()) {
                    tuttePrese = false;
                    break;
                }
            }

            if (tuttePrese) {
                mostraAvvisoMonete = false; 
                System.out.println("Livello completato!");
                livelloAttuale++;
                pannello.caricaLivello(livelloAttuale); // Passa al livello successivo
            } else {
                mostraAvvisoMonete = true; // Impedisce il completamento se mancano monete
            }
        } else {
            mostraAvvisoMonete = false;
        }
    }

    //Determina quale stringa deve essere visualizzata a schermo (priorità agli avvisi).
    public String getMessaggioCorrente() {
        if (mostraAvvisoMonete) {
            return "MANCANO ANCORA DELLE MONETE!!!";
        }
        if (timerMessaggio > 0) {
            return messaggioAvviso;
        }
        return "";
    }

    
    //Ripristina la posizione del giocatore e resetta le monete in caso di morte.
    private void resetDopoMorte() {
        contatoreMorti++;
        giocatore.getCoordinate().setX(puntoRinascitaCorrente.getX());
        giocatore.getCoordinate().setY(puntoRinascitaCorrente.getY());
        
        // Reset delle monete (opzionale: rende il gioco più difficile)
        for (Moneta m : livelloCorrente.getMonete()) {
            m.setPresa(false);
        } 
    }

    
    //Sostituisce il livello attuale con uno nuovo e resetta il giocatore.
    public void cambiaLivello(Livello nuovoLivello) {
        this.livelloCorrente = nuovoLivello;
        this.puntoRinascitaCorrente = new Coordinata(nuovoLivello.getSpawnPoint().getX(), nuovoLivello.getSpawnPoint().getY());
        resetDopoMorte(); 
        contatoreMorti--; // Sottrae una morte per non contare il cambio livello come decesso
    }

    public int getMorti() {
        return contatoreMorti;
    }
}