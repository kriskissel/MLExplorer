package Common;

import java.awt.Point;

/**
 * Immutable type represents pairs of real numbers, such as in a coordinate plane.
 * @author kriskissel
 *
 */
public class Tuple {

    private double x, y;
    
    /**
     * standard constructor
     * @param x x-coordinate of the represented point
     * @param y y-coordinate of the represented point
     */
    public Tuple(Double x, Double y){
        this.x = x;
        this.y = y;
    }
    
    /**
     * alternative constructor for convenience, allows user to define tuple with integers,
     * internally converts representation to doubles
     * @param x x-coordinate of the represented point
     * @param y y-coordinate of the represented point
     */
    public Tuple(int x, int y){
        this.x = (double) x;
        this.y = (double) y;
    }
    
    
    /**
     * 
     * @return x-coordinate of the represented point
     */
    public double getX() { return this.x;}
    
    /**
     * 
     * @return y-coordinate of the represented point
     */
    public double getY() {return this.y;}
    
    /**
     * 
     * @param other another Eucliden point
     * @return the square of the Euclidean distance between this and other
     */
    public double squareDistanceTo(Tuple other){
        double deltaX = this.x - other.x;
        double deltaY = this.y - other.y;
        return deltaX*deltaX + deltaY*deltaY;
    }
    
    
    /**
     * toString returns a string representation of
     * the tuple in the form "(x,y)"
     */
    @Override
    public String toString() {
        return "("+x+","+y+")";
    }
}
