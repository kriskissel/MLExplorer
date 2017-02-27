package Common;

import java.util.function.Function;
import Common.Tuple;

public class ParametricFunction implements Function<Double, Tuple>{

    private Function<Double, Double> xFunction, yFunction;
    private Double tMin, tMax;
    
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
    
    private static Tuple rotateVector(Tuple V, Double theta){
        Double w_x = Math.cos(theta)*V.getX() + Math.sin(theta)*V.getY();
        Double w_y = -Math.sin(theta)*V.getX() + Math.cos(theta)*V.getY();
        return new Tuple(w_x, w_y);
    }
    
    public Double getTMin() {return this.tMin;}
    
    public Double getTMax() {return this.tMax;}

    @Override
    public Tuple apply(Double t) {
        return new Tuple(xFunction.apply(t), yFunction.apply(t));
    }
}
