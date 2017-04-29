package Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Common.ModelData;
import Common.ParametricFunction;
import Common.Tuple;
import LinearAlgebra.Regression;

public class LinearRegressionVarianceModel extends PointsAndCurvesAbstractModel {

    private static enum AnimationStage {
        START_NEW_CYCLE, ADD_REGRESSION_CURVE, NORMALIZE_COLORS;
        
        private AnimationStage nextStage;
        
        static {
            START_NEW_CYCLE.nextStage = ADD_REGRESSION_CURVE;
            ADD_REGRESSION_CURVE.nextStage = NORMALIZE_COLORS;
            NORMALIZE_COLORS.nextStage = START_NEW_CYCLE;
        }
        
        private AnimationStage getNextStage() {
            return nextStage;
        }
    }
    
    //private Random random = new Random(); // used for selecting subset of points for fitting
    private AnimationStage animationStage = AnimationStage.START_NEW_CYCLE;
    private int pointsPerSample = 2; // 2 is the default
    private int degreeOfPolynomial = 1; // 1 is the default
    private List<Integer> samplePointIndices;
    private double ALPHA_FOR_CURVES = 0.2;

    
    @Override
    protected void checkRep() {
        if (this.K < 0 || this.K > modelHistory.size()) {
            throw new RuntimeException("Rep Error: K not pointing to a valid index in the model history");
        }
        if (this.modelHistory == null) {
            throw new RuntimeException("Model history null.");
        }
    }
    
    @Override
    void parseInitialData(String dataSet){
        this.points = new ArrayList<Tuple>();
        this.pointClasses = new ArrayList<Integer>();
        ArrayList<Double> w = new ArrayList<Double>();
        String[] lines = dataSet.split("\n");
        String mode = "not set";
        for (String line : lines) {
            if (line.equals("points")) {mode = "points";}
            else if (line.equals("samplesize")) {mode = "samplesize";}
            else if (line.equals("polynomialdegree")) {mode = "polynomialdegree";}
            else {
                String[] entries = line.split(",");
                if (mode.equals("points")) {
                    Double x = Double.parseDouble(entries[0]);
                    Double y = Double.parseDouble(entries[1]);
                    int c = Integer.parseInt(entries[2]);
                    points.add(new Tuple(x,y));
                    pointClasses.add(c);
                }
                else if (mode.equals("samplesize")) {
                    this.pointsPerSample = Integer.parseInt(entries[0]);
                }
                else if (mode.equals("polynomialdegree")) {
                    this.degreeOfPolynomial = Integer.parseInt(entries[0]);
                }
            }
        }
    }
    
    
    public LinearRegressionVarianceModel(String initialDataSet) {
        super(initialDataSet);
        parseInitialData(initialDataSet);
        reset();
        startingData = modelHistory.get(0).copyPointsOnly(); // keep a copy for iterating
    }
    
    @Override
    public boolean hasNext() {
        // this model can always select a new subset of points to fit, so
        // the demo can run indefinitely, even though it will eventually
        // begin repeating itself
        return true;
    }

    
    @Override
    void iterateModel(){
        // should only be run when current model is the last one in the array list
        // that means K is equal to the length of the modelHistory list.
        if (this.K != modelHistory.size()){
            throw new RuntimeException("Tried to generate new iteration while not at end of model history.");
        }
        
        // newModel is a copy of the points and all previously
        // added polynomial curves
        
        ModelData newModel = modelHistory.get(modelHistory.size() - 1).copyAll();
        // start with red points and blue curves
        for (int i = 0; i < newModel.getPointClass().size(); i++) {
            newModel.getPointClass().set(i, -1);
        }
        for (int i = 0; i < newModel.getCurveClass().size(); i++) {
            newModel.getCurveClass().set(i, 1);
            newModel.getCurveAlpha().set(i, this.ALPHA_FOR_CURVES);
        }
        
        // first stage: pick a subset of points to fit a polynomial
        if (animationStage == AnimationStage.START_NEW_CYCLE){
            
            // first we shuffle the samplePointsIndices list
            Collections.shuffle(samplePointIndices);
            
            // next we assign colors to points via the pointClasses list
            for (int k = 0; k < pointsPerSample; k++) {
                newModel.getPointClass().set(samplePointIndices.get(k), 0);
            }

        }
        
        // second stage: add the regression curve in color 0
        if (animationStage == AnimationStage.ADD_REGRESSION_CURVE) {
            
            // change point class just like in the previous iteration
            for (int k = 0; k < pointsPerSample; k++) {
                newModel.getPointClass().set(samplePointIndices.get(k), 1);
            }
            
            // obtain the coefficients of the regression curve
            // we start with the necessary points and
            // take advantage of the linear algebra package
            
            // the list samplePointIndices contains all the indices of
            // 0,1,2,...,points.size()-1 in random order
            // we use the first pointsPerSample of them to fit a polynomial
            
            // these lists a copies of the x-coordinates and
            // y-coordinates of first few points
            List<Double> xCoordinates = new ArrayList<Double>();
            List<Double> yCoordinates = new ArrayList<Double>();
            for (int i = 0; i < pointsPerSample; i++) {
                xCoordinates.add(points.get(samplePointIndices.get(i)).getX());
                yCoordinates.add(points.get(samplePointIndices.get(i)).getY());
            }
            
            // next we obtain the coefficients of the regression
            // polynomial of degree degreeOfPolynomial
            
            double[] coeffs = 
                    Regression.polynomialRegressionAutoReduceDegree(xCoordinates, yCoordinates, 
                            degreeOfPolynomial);
            
            // finally we add the regression curve and an
            // appropriate color code to newModel
            
            
            newModel.getCurves().add(ParametricFunction.polynomialFromCoefficients(coeffs));
            newModel.getCurveClass().add(2); // set curve color to black
            newModel.getCurveAlpha().add(1.0); // full opacity for new regression curve
            
        }
        
        
        // in stage 1 or stage 2, the sample points
        // should be colored differently
        if (animationStage == AnimationStage.START_NEW_CYCLE || 
                animationStage == AnimationStage.ADD_REGRESSION_CURVE) {
            for (int i = 0; i < pointsPerSample; i++) {
                newModel.getPointClass().set(samplePointIndices.get(i), 2);
            }
            //newModel.getCurveClass().set(newModel.getCurveClass().size()-1, 0);
        }
        
        
        // in stage 3, we return all sample points to their default color
        // and change the colors of all curves to be the same
        if (animationStage == AnimationStage.NORMALIZE_COLORS) {
            for (int i = 0; i < newModel.getPointClass().size(); i++) {
                newModel.getPointClass().set(i, -1);
            }
            for (int i = 0; i < newModel.getCurveClass().size(); i++) {
                newModel.getCurveClass().set(i, 1);
            }
        }
        
        
        // add newModel to modelHistory
        this.modelHistory.add(newModel);
        animationStage = animationStage.getNextStage(); // advance to the next animationStage
        checkRep();
    }
    


    @Override
    public void reset() {
        
        this.modelHistory = new ArrayList<ModelData>();
        this.samplePointIndices = new ArrayList<Integer>();
        for (int i = 0; i < points.size(); i++) {
            samplePointIndices.add(i);
        }
        ModelData startingModel = new ModelData();
        startingModel.setPoints(this.points);
        startingModel.setPointClasses(this.pointClasses);
        modelHistory.add(startingModel);
        this.K = 0;
        checkRep();
        
        
    }
    
}
