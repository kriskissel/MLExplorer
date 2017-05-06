package Common;

import java.util.ArrayList;

public class ModelData {
    
    /**
     * This data structure is mutable.  In particular, the functions in 
     * curveList are mutable, and might be affected by changes elsewhere.
     * To avoid problems, these methods should only be called by the
     * model class.
     */

    private ArrayList<Tuple> points = new ArrayList<Tuple>();
    private ArrayList<Integer> pointClass = new ArrayList<Integer>();
    private ArrayList<Double> pointAlpha = new ArrayList<Double>();
    private ArrayList<ParametricFunction> curves = new ArrayList<ParametricFunction>();
    private ArrayList<Integer> curveClass = new ArrayList<Integer>();
    private ArrayList<Double> curveAlpha = new ArrayList<Double>();
    
    /**
     * removes all curves from the data
     */
    public void clearCurves() {
        this.curves = new ArrayList<ParametricFunction>();
        this.curveClass = new ArrayList<Integer>();
        this.curveAlpha = new ArrayList<Double>();
    }
    
    /**
     * @return a new ModelData instance with its own internal lists of
     * points and curves as well as their classes.  
     * The lists are new instances, which means modifying
     * these lists will not affect the original lists.  However, the
     * points and curves instances themselves are the same, so those
     * data structures should be immutable.
     */
    public ModelData copyAll(){
        ModelData cloneData = new ModelData();
        for (Tuple point : this.points) { cloneData.points.add(point);}
        for (Integer pc : this.pointClass) { cloneData.pointClass.add(pc);}
        for (ParametricFunction f : this.curves) {cloneData.curves.add(f);}
        for (Integer cc : this.curveClass) {cloneData.curveClass.add(cc);}
        for (Double ca : this.curveAlpha) {cloneData.curveAlpha.add(ca);}
        for (Double ca : this.pointAlpha) {cloneData.pointAlpha.add(ca);}
        return cloneData;
    }
    
    /**
     * 
     * @return a new ModelData instance with a copy of the points and
     * their classes, but curves and their classes are not copied.
     */
    public ModelData copyPointsOnly(){
        ModelData cloneData = new ModelData();
        for (Tuple point : this.points) { cloneData.points.add(point);}
        for (Integer pc : this.pointClass) { cloneData.pointClass.add(pc);}
        for (Double ca : this.pointAlpha) {cloneData.pointAlpha.add(ca);}
        return cloneData;
    }
    
    /**
     *
     * @return the data points (Tuples in the xy-plane)
     */
    public ArrayList<Tuple> getPoints() {
        return points;
    }
    
    /**
     * 
     * @param points replace this ModelData points with a new list of Tuples
     */
    public void setPoints(ArrayList<Tuple> points){
        this.points = points;
    }
    
    /**
     * 
     * @param pointClasses integers defining the class of each point, used
     * to color them on the plots.  Legal values are -1,0,1,2.  The length
     * of this list shoudl match the length of the points list.
     */
    public void setPointClasses(ArrayList<Integer> pointClasses){
        this.pointClass = pointClasses;
    }
    
    /**
     * 
     * @param pointClasses doubles defining the opacity of each point, used
     * to draw them on the plots.  Legal values are 0 <= x <= 1.
     */
    public void setPointAlphas(ArrayList<Double> pointAlpha){
        this.pointAlpha = pointAlpha;
    }

    /**
     * 
     * @return pointClasses integers defining the class of each point, used
     * to color them on the plots.  Values are -1,0,1,2.
     */
    public ArrayList<Integer> getPointClass() {
        return pointClass;
    }

    /**
     * 
     * @return list of curves for plotting
     */
    public ArrayList<ParametricFunction> getCurves() {
        return curves;
    }

    /**
     * 
     * @return curveClasses integers defining the class of each curve, used
     * to color them on the plots.  Values are -1,0,1,2.
     */
    public ArrayList<Integer> getCurveClass() {
        return curveClass;
    }
    
    /**
     * 
     * @return list of alpha values (opacity) for curves shown in plots.
     */
    public ArrayList<Double> getCurveAlpha() {
        return curveAlpha;
    }
    
    /**
     * 
     * @return list of alpha values (opacity) for points shown in plots.
     */
    public ArrayList<Double> getPointAlpha() {
        return pointAlpha;
    }
    
    
    /**
     * create a complete copy of this ModelData instance
     */
    @Override
    public ModelData clone(){
        ModelData copy = new ModelData();
        copy.points =  new ArrayList<Tuple>(this.points);
        copy.pointClass = new ArrayList<Integer>(this.pointClass);
        copy.curves =  new ArrayList<ParametricFunction>(this.curves);
        copy.curveClass = new ArrayList<Integer>(this.curveClass);
        copy.curveAlpha = new ArrayList<Double>(this.curveAlpha);
        copy.pointAlpha = new ArrayList<Double>(this.pointAlpha);
        return copy;
    }
    
    /**
     * 
     * @param point Tuple for point to add
     * @param pointClass Legal values are -1, 0, 1, 2.
     */
    public void addPoint(Tuple point, Integer pointClass){
        this.points.add(point);
        this.pointClass.add(pointClass);
    }
    
    /**
     * 
     * @param curves replace this ModelData curves with a new list of curves
     */
    public void setCurves(ArrayList<ParametricFunction> curves){
        this.curves = curves;
    }
    
    /**
     * 
     * @param curveClass integers defining the class of each curve, used
     * to color them on the plots.  Legal values are -1,0,1,2.  The length
     * of this list should match the length of the curves list.
     */
    public void setCurveClass(ArrayList<Integer> curveClass){
        this.curveClass = curveClass;
    }
    
}
