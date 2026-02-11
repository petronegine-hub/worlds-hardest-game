public class GestoreGioco {
    
    private Quadrato giocatore;
    private Livello livelloCorrente;
    private int contatoreMorti;

    public GestoreGioco(Quadrato giocatore, Livello livelloIniziale) {
        // Inizializza i riferimenti al giocatore e al livello
    }

    public void eseguiCicloLogico() {
        // Coordina l'aggiornamento delle cose
    }

    public void muoviGiocatore(int dx, int dy) {
        // Riceve lo spostamento, verifica eventuali collisioni e agggiorna poszione giocatore
        
    }

    private void muoviNemici() {
        // Chiama il metodo muovi() di ogni nemico presente nel livello
    }

    private void controllaCollisioniNemici() {
        // COntrolla se il rettangolo del giocatore coincied quello di un nemico
    }

    private void controllaRaccoltaMonete() {
        // Controlla se il giocatore passa sopra una moneta non ancora presa
    }

    private void controllaVittoria() {
        // Verifica se il giocatore è nell'area finale del livello e le varie condizioni sono soddisfatte
        
    }

    private void resetDopoMorte() {
        // Riporta giocatore alle coordinate di spawn
        
    }

    public void cambiaLivello(Livello nuovoLivello) {
        // Sostituisce il livello corrente con quello nuovo e mette il giocatore alle coordinate di partenza
    }

    public void ControllaCollisioneMuri() {
        // Controlla se il giocatore collide con un muro e, in caso affermativo, annulla lo spostamento
    }   
 
    public int getMorti() {
        // Ritorna il numero di tentativi falliti
        return contatoreMorti;
    }
}