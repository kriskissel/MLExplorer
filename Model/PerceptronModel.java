package Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Common.ModelData;
import Common.ParametricFunction;
import Common.Tuple;

public class PerceptronModel extends PointsAndCurvesAbstractModel {
    
    private List<ModelData> modelHistory = new ArrayList<ModelData>();
    private int K; // points to current model in modelHistory, 0-indexed
    private Random random = new Random(); // used for updating perceptron
    private boolean allPointsClassifiedCorrectly = false;
    private ArrayList<Common.Tuple> points; // shared set of points for all iterations of model
    private ArrayList<Integer> pointClasses; // shared set of point classes for all iterations of model
    private Double[] W; // the normal vector for the last generated iteration of perceptron decision boundary, should have 3 entries
    private Double[] INITIAL_W; // starting decision boundary: y = 0.5
    private ModelData startingData;
    // animationStage will tell use what type of change to make in the next new model iteration
    // stage 0 means we need to identify a misclassified point and change its color
    // stage 1 means that we need to update the perceptron decision boundary
    // stage 2 means we need to return the point to its original color
    // we start out in stage 0
    private int animationStage = 0;
    private int misclassifiedIndex;
    
    private void checkRep() {
        if (this.K < 0 || this.K > modelHistory.size()) {
            throw new RuntimeException("Rep Error: K not pointing to a valid index in the model history");
        }
        if (this.modelHistory == null) {
            throw new RuntimeException("Model history null.");
        }
        if (this.W.length != 3) {
            throw new RuntimeException("Normal Vector W isn't length 3");
        }
        if (this.W[1] == 0 && this.W[2] == 0) {
            throw new RuntimeException("Invalid normal vector W: W[1] and W[2] both equal 0");
        }
    }
    
    
    public PerceptronModel(String initialDataSet) {
        super(initialDataSet);
        parseInitialData(initialDataSet);
        reset();
        startingData = modelHistory.get(0).copyPointsOnly(); // keep a copy for iterating
    }
    
    @Override
    public boolean hasNext() {
        if (this.K < this.modelHistory.size() || 
                this.animationStage != 2 ||
                !this.allPointsClassifiedCorrectly) { return true; }
        else {
            return false;
        }
    }

    
    @Override
    void iterateModel(){
        // should only be run when current model is the last one in the array list
        // that means K is equal to the length of the modelHistory list.
        if (this.K != modelHistory.size()){
            throw new RuntimeException("Tried to generate new iteration while not at end of model history.");
        }
        
        ModelData newModel = startingData.copyPointsOnly();
        
        // stage 0: pick a misclassified point
        if (animationStage == 0){
            List<Integer> misclassifiedPoints = getMisclassifiedPointIndices();
            int randomIndex = misclassifiedPoints.get(random.nextInt(misclassifiedPoints.size()));
            misclassifiedIndex = randomIndex;
            
            
            
            
            // clear the previous decision boundary before adding the new one
            //newModel.clearCurves();
            
            // add the current decision boundary
            newModel.getCurves().add(ParametricFunction.linearDecisionBoundary(W));
            newModel.getCurveClass().add(0);
        }
        if (animationStage == 1) {
            // change point class just like in the previous iteration
            newModel.getPointClass().set(misclassifiedIndex, 2);
            
            // update perceptron decision vector 
            Tuple X = this.startingData.getPoints().get(misclassifiedIndex);
            int y = this.startingData.getPointClass().get(misclassifiedIndex);
            
            this.W[0] += y;
            this.W[1] += y * X.getX();
            this.W[2] += y * X.getY();
            if (getMisclassifiedPointIndices().isEmpty()) {
                this.allPointsClassifiedCorrectly = true;
            }
        }
        if (animationStage == 0 || animationStage == 1) {
            // change the class of the point being used to
            // update perceptron decision vector, this way the view can display it in a different color
            newModel.getPointClass().set(misclassifiedIndex, 2);
        }
        // if animationStage == 2, this last conditional statement won't execute, leaving all
        // point classes as either 1 or -1, so that none are highlighted in the view


        // clear the previous decision boundary before adding the new one
        //newModel.clearCurves();
        
        // add the decision boundary
        ParametricFunction decisionBoundary = ParametricFunction.linearDecisionBoundary(W);
        newModel.getCurves().add(decisionBoundary);
        newModel.getCurveClass().add(2);
        
        // add the decision vectors projection onto R^2
        ParametricFunction positiveVector = ParametricFunction.unitVector(new Tuple(W[1], W[2]));
        newModel.getCurves().add(positiveVector);
        newModel.getCurveClass().add(1);
        
        // add newModel to modelHistory
        this.modelHistory.add(newModel);
        animationStage = (animationStage + 1) % 3; // advance to the next animationStage
        checkRep();
    }
    
    private int evaluateModel(Tuple x){
        Double score = W[0] + W[1] * x.getX() + W[2] * x.getY();
        if (score > 0) { return 1;}
        else { return -1;}
    }
    
    private List<Integer> getMisclassifiedPointIndices(){
        // We use the starting model to determine which points are misclassified
        // because in later models, some of the points are put in different classes
        // just so that they show up differently in the view.
        ArrayList<Integer> misclassifiedPoints = new ArrayList<Integer>();
        for (int i = 0; i < this.startingData.getPoints().size(); i++) {
            if (evaluateModel(this.startingData.getPoints().get(i)) != this.startingData.getPointClass().get(i)){
                misclassifiedPoints.add(i);
            }
        }
        return misclassifiedPoints;
    }

    @Override
    public void reset() {

        this.modelHistory = new ArrayList<ModelData>();
        this.allPointsClassifiedCorrectly = false;
        this.W = INITIAL_W.clone(); 
        ModelData startingModel = new ModelData();
        startingModel.setPoints(this.points);
        startingModel.setPointClasses(this.pointClasses);
        ParametricFunction startingDecisionBoundary = new ParametricFunction(t -> t, t -> 0.5, -10.0, 10.0);
        startingModel.getCurves().add(startingDecisionBoundary);
        startingModel.getCurveClass().add(2);
        ParametricFunction positiveVector = ParametricFunction.unitVector(new Tuple(W[1], W[2]));
        startingModel.getCurves().add(positiveVector);
        startingModel.getCurveClass().add(1);
        modelHistory.add(startingModel);
        this.K = 0;
        checkRep();
    }
    
    

}
