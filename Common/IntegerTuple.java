package Common;

public class IntegerTuple {

    /**
     * Immutable type represents pairs of integers, such as in a coordinate plane.
     * @author kriskissel
     *
     */

    private int x, y;

    /**
     * standard constructor
     * @param x x-coordinate of the represented point
     * @param y y-coordinate of the represented point
     */
    public IntegerTuple(int x, int y){
        this.x = x;
        this.y = y;
    }



    /**
     * 
     * @return x-coordinate of the represented point
     */
    public int getX() { return this.x;}

    /**
     * 
     * @return y-coordinate of the represented point
     */
    public int getY() {return this.y;}

    /**
     * toString returns a string representation of
     * the tuple in the form "(x,y)"
     */
    @Override
    public String toString() {
        return "("+x+","+y+")";
    }
}
