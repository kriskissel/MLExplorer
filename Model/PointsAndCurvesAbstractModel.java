package Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import Common.ModelData;
import Common.ParametricFunction;
import Common.Tuple;

public abstract class PointsAndCurvesAbstractModel implements Common.ModelInterface {

    List<ModelData> modelHistory = new ArrayList<ModelData>();
    int K; // index points to current model in modelHistory, 0-indexed
    ArrayList<Common.Tuple> points; // shared set of points for all iterations of model
    ArrayList<Integer> pointClasses; // shared set of point classes for all iterations of model
    ModelData startingData;
    Double[] INITIAL_W; // coefficients for initial curve
    // animationStage will tell use what type of change to make in the next new model iteration
    // stage 0 means we need to identify a misclassified point and change its color
    // stage 1 means that we need to update the perceptron decision boundary
    // stage 2 means we need to return the point to its original color
    // we start out in stage 0
    private int animationStage = 0;
    
    private void checkRep() {
        if (this.K < 0 || this.K > modelHistory.size()) {
            throw new RuntimeException("Rep Error: K not pointing to a valid index in the model history");
        }
        if (this.modelHistory == null) {
            throw new RuntimeException("Model history null.");
        }
    }
    

    public PointsAndCurvesAbstractModel(String initialDataSet){
        System.out.println("parsing initial data");
        parseInitialData(initialDataSet);
        //System.out.println("calling reset");
        // reset();
        // keep a copy for iterating
        //startingData = modelHistory.get(0).copyPointsOnly(); 
        System.out.println("completed super constructor");
        System.out.println("this.INITIAL_W");
        System.out.println(this.INITIAL_W);
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
        System.out.println("w size: " + w.size());
        this.INITIAL_W = new Double[w.size()];
        for (int i = 0; i < w.size(); i++) {
            this.INITIAL_W[i] = w.get(i);
        }
        System.out.println(this.INITIAL_W);
    }
    
 
    @Override
    public abstract boolean hasNext();
        //boolean notAtEndOfHistory = this.K < this.modelHistory.size() - 1;
        //boolean newIterationsAvailable = !allPointsClassifiedCorrectly;
        //boolean notCompletedFinalIteration = allPointsClassifiedCorrectly && animationStage == 2;
        //return notAtEndOfHistory || newIterationsAvailable || notCompletedFinalIteration;
    
    
    @Override
    public ModelData first() {
        this.K = 0;
        return modelHistory.get(K);
    }

    @Override
    public ModelData next() {
        if (!hasNext()) {
            throw new RuntimeException("Model has no next value.");
        }
        this.K++;
        if (this.K == modelHistory.size()){
            iterateModel();
        }
        ModelData nextData = modelHistory.get(this.K);
        return nextData;
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
