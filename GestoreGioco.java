import java.awt.Rectangle;

public class GestoreGioco {
    
    private Quadrato giocatore;
    private Livello livelloCorrente;
    private int contatoreMorti;

    public GestoreGioco(Quadrato giocatore, Livello livelloIniziale) {
    this.giocatore = giocatore;
    this.livelloCorrente = livelloIniziale;
    this.contatoreMorti = 0;
    }

    public void eseguiCicloLogico() {
      
        muoviNemici();
        controllaCollisioniNemici();
        controllaRaccoltaMonete();
        controllaVittoria();
        
    }

    public void muoviGiocatore(int dx, int dy) {
        // Riceve lo spostamento, verifica eventuali collisioni e agggiorna poszione giocatore
        // Calcolo la posizione futura
        int nextX = giocatore.getCoordinate().getX() + dx;
        int nextY = giocatore.getCoordinate().getY() + dy;
        
        // Creo una hitbox ipotetica per il prossimo frame
        Rectangle hitboxFutura = new Rectangle(nextX, nextY, giocatore.getLargezza(), giocatore.getAltezza());
        
        boolean collisioneMuro = false;
        for (Muro m : livelloCorrente.getMuri()) {
            //Rectangle per l'intersezione
            Rectangle rectMuro = new Rectangle(m.getX(), m.getY(), m.getLarghezza(), m.getAltezza());
            if (hitboxFutura.intersects(rectMuro)) {
                collisioneMuro = true;
                break;
            }
        }

        // Se non collide con i muri, aggiorna la posizione
        if (!collisioneMuro) {
            giocatore.getCoordinate().setX(nextX);
            giocatore.getCoordinate().setY(nextY);
        }
        
    }

    private void muoviNemici() {
        // Chiama il metodo muovi() di ogni nemico presente nel livello
    }

    private void controllaCollisioniNemici() {
        // COntrolla se il rettangolo del giocatore coincied quello di un nemico
        Rectangle rectGiocatore = giocatore.getBounds();
        for (Nemico n : livelloCorrente.getNemici()) {
            if (rectGiocatore.intersects(n.getBounds())) {
                resetDopoMorte();
                return; 
            }
        }
    }

    private void controllaRaccoltaMonete() {
        // Controlla se il giocatore passa sopra una moneta non ancora presa
        Rectangle rectGiocatore = giocatore.getBounds();
        for (Moneta m : livelloCorrente.getMonete()) {
            if (!m.isPresa()) {
                // Creo un piccolo rettangolo per la moneta per usare intersects
                Rectangle rectMoneta = new Rectangle(m.getX() - m.getRaggio(), m.getY() - m.getRaggio(), m.getRaggio()*2, m.getRaggio()*2);
                if (rectGiocatore.intersects(rectMoneta)) {
                    m.setPresa(true);
                    System.out.println("Moneta raccolta!");
                }
            }
        }
    }

    private void controllaVittoria() {
        // Verifica se il giocatore è nell'area finale del livello e le varie condizioni sono soddisfatte
        
    }

    private void resetDopoMorte() {
        contatoreMorti++;
        System.out.println("Morti totali: " + contatoreMorti);
        
        // Riporta il giocatore allo spawn
        Coordinata spawn = livelloCorrente.getSpawnPoint();
        giocatore.getCoordinate().setX(spawn.getX());
        giocatore.getCoordinate().setY(spawn.getY());
        
        /*
        //opzionale: resetta tutte le monete anche se muore
        for (Moneta m : livelloCorrente.getMonete()) {
            m.setPresa(false);
        }
        */
        
    }

    public void cambiaLivello(Livello nuovoLivello) {
        // Sostituisce il livello corrente con quello nuovo e mette il giocatore alle coordinate di partenza
        this.livelloCorrente = nuovoLivello;
        resetDopoMorte(); 
    }

    public void ControllaCollisioneMuri() {
        // Controlla se il giocatore collide con un muro e, in caso affermativo, annulla lo spostamento
        
    }

    public int getMorti() {
        // Ritorna il numero di tentativi falliti
        return contatoreMorti;
    }
}