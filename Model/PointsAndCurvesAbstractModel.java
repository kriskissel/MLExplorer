package Model;

import java.util.ArrayList;
import java.util.List;
import Common.ModelData;
import Common.Tuple;

public abstract class PointsAndCurvesAbstractModel implements Common.ModelInterface {
    
    protected List<ModelData> modelHistory = new ArrayList<ModelData>();
    protected int K; // index points to current model in modelHistory, 0-indexed
    protected ArrayList<Common.Tuple> points; // shared set of points for all iterations of model
    protected ArrayList<Integer> pointClasses; // shared set of point classes for all iterations of model
    protected ModelData startingData;
    protected Double[] INITIAL_W; // coefficients for initial curve

    
    protected abstract void checkRep();
    

    public PointsAndCurvesAbstractModel(String initialDataSet){
        parseInitialData(initialDataSet);
    }
    
    
    
    void parseInitialData(String dataSet){
        this.points = new ArrayList<Tuple>();
        this.pointClasses = new ArrayList<Integer>();
        ArrayList<Double> w = new ArrayList<Double>();
        String[] lines = dataSet.split("\n");
        String mode = "not set";
        for (String line : lines) {
            if (line.equals("points")) {mode = "points";}
            else if (line.equals("decision vector")) {mode = "decision vector";}
            else {
                String[] entries = line.split(",");
                if (mode.equals("points")) {
                    Double x = Double.parseDouble(entries[0]);
                    Double y = Double.parseDouble(entries[1]);
                    int c = Integer.parseInt(entries[2]);
                    points.add(new Tuple(x,y));
                    pointClasses.add(c);
                }
                else if (mode.equals("decision vector")) {
                    for (int i = 0; i < entries.length; i++) {
                        w.add(Double.parseDouble(entries[i]));
                    }
                }
            }
        }
        
        this.INITIAL_W = new Double[w.size()];
        for (int i = 0; i < w.size(); i++) {
            this.INITIAL_W[i] = w.get(i);
        }
        
    }
    
 
    @Override
    public abstract boolean hasNext();
            
    
    @Override
    public ModelData first() {
        this.K = 0;
        return modelHistory.get(K);
    }

    /**
     * Clients should always check hasNext() first before using next().
     * next() throws a RuntimeException if hasNext(0 is false.
     */
    @Override
    public ModelData next() {
        if (hasNext()) {
            this.K++;
            if (this.K == modelHistory.size()){
                iterateModel();
            }
            ModelData nextData = modelHistory.get(this.K);
            return nextData;
        } else {
            throw new RuntimeException("model has no next state.");
        }
    }
    
    abstract void iterateModel();
    
    @Override
    public abstract void reset(); 
    
    
    @Override
    public boolean hasPrevious() {
        return this.K > 0;
    }

    @Override
    public ModelData previous() {
        if (!hasPrevious()) {
            throw new RuntimeException("Can't execute previous(): History has no previous model.");
        }
        K--;
        return modelHistory.get(K);
    }
    
}
