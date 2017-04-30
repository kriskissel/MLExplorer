package Common;

import java.util.function.Function;
import Common.Tuple;

public class ParametricFunction implements Function<Double, Tuple>{

    private Function<Double, Double> xFunction, yFunction;
    private Double tMin, tMax;
    
    /**
     * 
     * @param xFunction a function of the form t -> x(t)
     * @param yFunction a function of the form t -> y(t)
     * @param tMin minimum t-value for plotting
     * @param tMax maximum t-value for plotting
     */
    public ParametricFunction(Function<Double, Double> xFunction, Function<Double, Double> yFunction, 
            Double tMin, Double tMax){
        this.xFunction = xFunction;
        this.yFunction = yFunction;
        this.tMin = tMin;
        this.tMax = tMax;
    }
    
    /**
     * 
     * @param initialPoint
     * @param finalPoint cannot be the same as initialPoint, must differ in at least one coordinate
     * @return parameterization of a line segment that would fill a 10x10 viewing window
     */
    public static ParametricFunction lineThroughTwoPoints(Tuple initialPoint, Tuple finalPoint){
        Double deltaX = finalPoint.getX() - initialPoint.getX();
        Double deltaY = finalPoint.getY() - initialPoint.getY();
        if (deltaX == 0) {
            return new ParametricFunction(t -> initialPoint.getX(), t -> t, -10.0, 10.0);
        }
        else {
            Double m = deltaY / deltaX;
            return new ParametricFunction(t -> t, t -> (initialPoint.getY() + m * (t - initialPoint.getX())), -10.0, 10.0);
        }
    }
    
    
    /**
     * Creates a parametric function equivalent to the polynomial
     * c0 + c1*x + c2*x^2 + ... + cn*x^n
     * @param coeffs an array of coefficients [c0,c1,c2,...,cn] for the
     *          polynomial c0 + c1*x + c2*x^2 + ... + cn*x^n
     * @return a parametric function t -> (t, c0 + c1*t + c2*t^2 + ... + cn*t^n)
     */
    public static ParametricFunction polynomialFromCoefficients(double[] coeffs) {
        ParametricFunction p = new ParametricFunction(t -> t, t -> {
            double v = 1.0;
            double y = 0.0;
            for (Double c : coeffs) {
                y += c * v;
                v *= t;
            }
            return y;
            }, -10.0, 10.0);
        
        return p;
    }
    
    
    /**
     * 
     * @param W must be a vector of length 3, with either W[1] != 0 or W[2] != 0
     * @returns parameterization of corresponding decision boundary corresponding to W^TX = 0, where X = (1,x,y)
     */
    public static ParametricFunction linearDecisionBoundary(Double[] W){
        ParametricFunction f;
        final Double w_0 = W[0], w_1 = W[1], w_2 = W[2];
        if (w_2 == 0) {
            f = new ParametricFunction(t -> (-w_0/w_1), t -> t, -10.0, 10.0);
        }
        else {
            f = new ParametricFunction(t -> t, t -> -(w_0 + w_1*t)/w_2, -10.0, 10.0);
        }
        return f;
    }
    
    /**
     * 
     * @param V a Tuple representing a unit vector in the xy-plane
     * @return a paramatric function whose graph will represent the
     * unit vector V with its tail at the origin.
     */
    public static ParametricFunction unitVector(Tuple V){
        Double normV = Math.sqrt(V.getX()*V.getX() + V.getY()*V.getY());
        Double u_x = V.getX() / normV;
        Double u_y = V.getY() / normV;
        Tuple U = new Tuple(u_x, u_y);
        Tuple W = rotateVector(U, 0.9*Math.PI);
        Double w_x = W.getX();
        Double w_y = W.getY();
        ParametricFunction f = 
                new ParametricFunction(t -> {
                    if (t < 1) { return t*u_x; }
                    else { return u_x + (t-1)*w_x ;}
                }, t -> {
                    if (t < 1) { return t * u_y ;}
                    else { return u_y + (t-1) * w_y ;}
                }, 0.0, 1.2);
        return f;
    }
    
    /**
     * 
     * @param V a Tuple representing a vector in the xy-plane
     * @param theta an angle in radians
     * @return a vector corresponding to the result of rotating V
     * counterclockwise by an angle theta radians
     */
    private static Tuple rotateVector(Tuple V, Double theta){
        Double w_x = Math.cos(theta)*V.getX() + Math.sin(theta)*V.getY();
        Double w_y = -Math.sin(theta)*V.getX() + Math.cos(theta)*V.getY();
        return new Tuple(w_x, w_y);
    }
    
    /**
     * 
     * @return minimum t-value for plotting
     */
    public Double getTMin() {return this.tMin;}
    
    /**
     * 
     * @return maximum t-value for plotting
     */
    public Double getTMax() {return this.tMax;}

    /**
     * @return the result of applying the parametric function represented by 
     * this instance to the input value t
     */
    @Override
    public Tuple apply(Double t) {
        return new Tuple(xFunction.apply(t), yFunction.apply(t));
    }
}
