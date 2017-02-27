package Common;

import java.util.ArrayList;

public class ModelData {
    
    /**
     * Note that this data structure is mutable.  In particular, the functions in curveList
     * are mutable, and might be affected by changes elsewhere.
     * I should probably try to make Tuple and ParametricFunction immutable.
     */

    private ArrayList<Tuple> points = new ArrayList<Tuple>();
    private ArrayList<Integer> pointClass = new ArrayList<Integer>();
    private ArrayList<ParametricFunction> curves = new ArrayList<ParametricFunction>();
    private ArrayList<Integer> curveClass = new ArrayList<Integer>();
    
    public void clearCurves() {
        this.curves = new ArrayList<ParametricFunction>();
        this.curveClass = new ArrayList<Integer>();
    }
    
    public ModelData copyAll(){
        ModelData cloneData = new ModelData();
        for (Tuple point : this.points) { cloneData.points.add(point);}
        for (Integer pc : this.pointClass) { cloneData.pointClass.add(pc);}
        for (ParametricFunction f : this.curves) {cloneData.curves.add(f);}
        for (Integer cc : this.curveClass) {cloneData.curveClass.add(cc);}
        return cloneData;
    }
    
    public ModelData copyPointsOnly(){
        ModelData cloneData = new ModelData();
        for (Tuple point : this.points) { cloneData.points.add(point);}
        for (Integer pc : this.pointClass) { cloneData.pointClass.add(pc);}
        return cloneData;
    }
    
    public ArrayList<Tuple> getPoints() {
        return points;
    }
    
    public void setPoints(ArrayList<Tuple> points){
        this.points = points;
    }
    
    public void setPointClasses(ArrayList<Integer> pointClasses){
        this.pointClass = pointClasses;
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
