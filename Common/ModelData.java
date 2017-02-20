package Common;

import java.util.ArrayList;

public class ModelData {

    private ArrayList<Tuple> points = new ArrayList<Tuple>();
    private ArrayList<Integer> pointClass = new ArrayList<Integer>();
    private ArrayList<ParametricFunction> curves = new ArrayList<ParametricFunction>();
    private ArrayList<Integer> curveClass = new ArrayList<Integer>();
    
    
    public ArrayList<Tuple> getPoints() {
        return points;
    }

    public ArrayList<Integer> getPointClass() {
        return pointClass;
    }

    public ArrayList<ParametricFunction> getCurves() {
        return curves;
    }

    public ArrayList<Integer> getCurveClass() {
        return curveClass;
    }
    
    @Override
    public ModelData clone(){
        ModelData copy = new ModelData();
        copy.points =  new ArrayList<Tuple>(this.points);
        copy.pointClass = new ArrayList<Integer>(this.pointClass);
        copy.curves =  new ArrayList<ParametricFunction>(this.curves);
        copy.curveClass = new ArrayList<Integer>(this.curveClass);
        
        return copy;
    }
    
    public void addPoint(Tuple point, Integer pointClass){
        this.points.add(point);
        this.pointClass.add(pointClass);
    }
    
    public void setCurves(ArrayList<ParametricFunction> curves){
        this.curves = curves;
    }
    
    public void setCurveClass(ArrayList<Integer> curveClass){
        this.curveClass = curveClass;
    }
    
}
