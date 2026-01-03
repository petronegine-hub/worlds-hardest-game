public class Quadrato {
    
    private Coordinata coordinate;
    private int alteza;
    private int largezza;

    /**
     * @param c
     * @param a
     * @param l
     */
    public Quadrato( Coordinata c, int a, int l) 
    {
        
       coordinate = c;
       alteza = a;
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
    public int getAlteza() 
    {
        return alteza;
    }

    public int getLargezza() {
        return largezza;
    }
}
