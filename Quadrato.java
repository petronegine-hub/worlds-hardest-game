public class Quadrato {
    
    private Coordinata coordinate;
    private int altezza;
    private int largezza;

    /**
     * @param c
     * @param a
     * @param l
     */
    public Quadrato( Coordinata c, int a, int l) 
    {
        
       coordinate = c;
       altezza = a;
       largezza = l;
    }

    /**
     * @return
     */
    public Coordinata getCoordinate() 
    { 
        return coordinate;
    }

    /**
     * @return
     */
    public int getAltezza() 
    {
        return altezza;
    }

    public int getLargezza() {
        return largezza;
    }
}
