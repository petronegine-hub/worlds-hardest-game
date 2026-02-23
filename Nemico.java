import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.List;

public class Nemico {

    // Attributi privati per lo stato del nemico
    private Coordinata posizione;  
    private int raggio;      
    private int tipoMovimento;     // 0: Orizzontale, 1: Verticale, 2: Diagonale
    private int velocitaX;         // Spostamento orizzontale
    private int velocitaY;         // Spostamento verticale

    // Costruttore per inizializzare il nemico
    public Nemico(Coordinata posizioneIniziale, int raggio, int velocitaX, int velocitaY, int tipo) {
        this.posizione = posizioneIniziale;
        this.raggio = raggio;
        this.velocitaX = velocitaX;
        this.velocitaY = velocitaY;
        this.tipoMovimento = tipo;
        
        // Setup iniziale della velocità in base al tipo di movimento scelto
        if (tipo == 0) this.velocitaY = 0; // Se orizzontale, annulla la velocità verticale
        if (tipo == 1) this.velocitaX = 0; // Se verticale, annulla la velocità orizzontale
    }

    //Metodo principale di aggiornamento logico: gestisce movimento e collisioni.
    public void aggiorna(int limiteW, int limiteH, List<Muro> muri, Rectangle partenza, Rectangle arrivo) {
        //Salva la posizione precedente
        int vecchiaX = posizione.getX();
        int vecchiaY = posizione.getY();
        //aggiorna le coordinate correnti in base alla velocità
        posizione.Sposta(velocitaX, velocitaY);

        // Se tocca il bordo sinistro o destro cambio direzione orizzontale
        if (posizione.getX() - raggio < 0 || posizione.getX() + raggio > limiteW) {
            invertiX(); 
            posizione.setX(vecchiaX); 
        }
        // Se tocca il bordo superiore o inferiore cambio direzione verticale
        if (posizione.getY() - raggio < 0 || posizione.getY() + raggio > limiteH) {
            invertiY();
            posizione.setY(vecchiaY);
        }

        // Crea un rettangolo (hitbox) che circonda il nemico per il calcolo delle collisioni
        Rectangle hitbox = getBounds();
        Rectangle ostacoloColpito = null;

        // Cerca se il nemico collide con uno dei muri presenti nella lista
        for (Muro m : muri) {
            Rectangle rectMuro = new Rectangle(m.getX(), m.getY(), m.getLarghezza(), m.getAltezza());
            if (hitbox.intersects(rectMuro)) {
                ostacoloColpito = rectMuro; // Salva il riferimento al muro colpito
                break; // Esci dal ciclo al primo impatto trovato
            }
        }
        
        // Controlla se il nemico tocca anche le zone di partenza o arrivo (aree protette)
        if (ostacoloColpito == null && partenza != null && hitbox.intersects(partenza)) ostacoloColpito = partenza;
        if (ostacoloColpito == null && arrivo != null && hitbox.intersects(arrivo)) ostacoloColpito = arrivo;

        // Logica di rimbalzo intelligente contro gli ostacoli
        if (ostacoloColpito != null) {
            // Se prima del movimento il nemico era sopra o sotto l'ostacolo, il colpo è verticale
            if (vecchiaY + raggio <= ostacoloColpito.y || vecchiaY - raggio >= ostacoloColpito.y + ostacoloColpito.height) {
                invertiY();
            } 
            // Altrimenti, significa che l'impatto è avvenuto lateralmente (destra o sinistra)
            else {
                invertiX();
            }
            // Ripristina la posizione precedente per evitare che il nemico rimanga incastrato nell'oggetto
            posizione.setX(vecchiaX);
            posizione.setY(vecchiaY);
            // Applica subito il movimento nella nuova direzione invertita
            posizione.Sposta(velocitaX, velocitaY);
        }
    }

    public void disegna(Graphics g) {
        g.setColor(Color.BLUE); // Imposta il colore blu
        // Disegna un cerchio pieno centrato sulla posizione corrente
        g.fillOval(posizione.getX() - raggio, posizione.getY() - raggio, raggio * 2, raggio * 2);
    }

    //Calcola il rettangolo di collisione del nemico basato sulla posizione e sul raggio, utile per le collisioni con muri e zone protette
    public Rectangle getBounds() {
        return new Rectangle(posizione.getX() - raggio, posizione.getY() - raggio, raggio * 2, raggio * 2);
    }

    // Metodi di utilità per invertire i vettori di velocità
    public void invertiX() { this.velocitaX = -this.velocitaX; }
    public void invertiY() { this.velocitaY = -this.velocitaY; }

    // Metodi Getter per leggere la velocità dall'esterno
    public int getVelocitaX() { return velocitaX; }
    public int getVelocitaY() { return velocitaY; }
}