import java.util.ArrayList;
import java.util.List;
import java.awt.Rectangle;

public class Livello {
    private List<Muro> muri;
    private List<Nemico> nemici;
    private List<Moneta> monete;
    private Rectangle areaPartenza; // Area verde iniziale
    private Rectangle areaArrivo;   // Area verde finale
    private Coordinata spawnPoint;  // Punto spawn quadrato
    private MyPanel pannello;

    public Livello(Coordinata spawn) {
        this.spawnPoint = spawn;
        this.muri = new ArrayList<>();
        this.nemici = new ArrayList<>();
        this.monete = new ArrayList<>();
    }

    //metodi x aggiungere muri o nemici al livello 
    public void aggiungiMuro(int x, int y, int w, int h) {
        muri.add(new Muro(new Coordinata(x, y), w, h));
    }

    public void aggiungiNemico(int x, int y, int r, int vx, int vy) {
        nemici.add(new Nemico(new Coordinata(x, y), r, vx, vy, 0)); // Default a movimento orizzontale
    }

    public void aggiungiMoneta(int x, int y, int r) {
        monete.add(new Moneta(new Coordinata(x, y), r));
    }

    // Getters per leggere i dati del livello
    public List<Muro> getMuri() { return muri; }
    public List<Nemico> getNemici() { return nemici; }
    public List<Moneta> getMonete() { return monete; }
    public Rectangle getAreaPartenza() { return areaPartenza; }
    public Rectangle getAreaArrivo() { return areaArrivo; }
    public Coordinata getSpawnPoint() { return spawnPoint; }
    
    // Setter per le zone verdi
    public void setAreaArrivo(int x, int y, int w, int h) {
        this.areaArrivo = new Rectangle(x, y, w, h);
    }

    public void setAreaPartenza(int x, int y, int w, int h) {
        this.areaPartenza = new Rectangle(x, y, w, h);
    }

    //metodo reset

}