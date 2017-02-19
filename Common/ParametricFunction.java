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
    
    public Double getTMin() {return this.tMin;}
    
    public Double getTMax() {return this.tMax;}

    @Override
    public Tuple apply(Double t) {
        return new Tuple(xFunction.apply(t), yFunction.apply(t));
    }
}
