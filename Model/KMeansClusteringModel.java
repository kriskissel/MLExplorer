package Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Common.ModelData;
import Common.ParametricFunction;
import Common.Tuple;
import LinearAlgebra.Regression;

public class KMeansClusteringModel extends PointsAndCurvesAbstractModel {

    private static enum AnimationStage {
        START_NEW_CYCLE, RECENTER_CLUSTERS, NORMALIZE_COLORS;
        
        private AnimationStage nextStage;
        
        static {
            START_NEW_CYCLE.nextStage = RECENTER_CLUSTERS;
            RECENTER_CLUSTERS.nextStage = NORMALIZE_COLORS;
            NORMALIZE_COLORS.nextStage = START_NEW_CYCLE;
        }
        
        private AnimationStage getNextStage() {
            return nextStage;
        }
    }
    

    
    private AnimationStage animationStage = AnimationStage.START_NEW_CYCLE;
    private static double ALPHA_FOR_DATA_POINTS = 0.8;
    private int k;
    private int N;
    private ArrayList<Double> pointAlpha;
    private boolean stoppedChangingClusters = false;
    
    public KMeansClusteringModel(String initialDataSet){
        super(initialDataSet);
        //parseInitialData(initialDataSet);
        reset();
        startingData = modelHistory.get(0).copyPointsOnly(); // keep a copy for iterating
    }
    
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
        this.pointAlpha = new ArrayList<Double>();
        this.N = 0;
        ArrayList<Double> w = new ArrayList<Double>();
        String[] lines = dataSet.split("\n");
        String mode = "not set";
        for (String line : lines) {
            if (line.equals("points")) {mode = "points";}
            else if (line.equals("k")) {mode = "k";}
            else {
                String[] entries = line.split(",");
                if (mode.equals("points")) {
                    Double x = Double.parseDouble(entries[0]);
                    Double y = Double.parseDouble(entries[1]);
                    int c = Integer.parseInt(entries[2]);
                    points.add(new Tuple(x,y));
                    pointClasses.add(c);
                    N++;
                    
                }
                else if (mode.equals("k")) {
                    this.k = Integer.parseInt(entries[0]);
                    N -= k;
                }
            }
        }
        // set data point opacity
        for (int i = 0; i < N; i++) {
            pointAlpha.add(0.2);
        }
    }

    @Override
    public boolean hasNext() {
        if (this.K == 0) { 
            return true; }
        if (this.K < modelHistory.size()-1) { 
            return true; } // not at end of history
        if (this.animationStage == AnimationStage.NORMALIZE_COLORS &&
                this.modelHistory.get(this.modelHistory.size() 
                - 1).getPoints().size() == this.N){
            // already removed cluster center indicators
            return false;
        }
        return true;
        
    }

    @Override
    void iterateModel() {
     // should only be run when current model is the last one in the array list
        // that means K is equal to the length of the modelHistory list.
        if (this.K != modelHistory.size()){
            throw new RuntimeException("Tried to generate new iteration while not at end of model history.");
        }
        
        // newModel is a copy of the points and all previously

        
        ModelData newModel = modelHistory.get(modelHistory.size() - 1).copyAll();

        
        // first stage: assign points to clusters
        if (animationStage == AnimationStage.START_NEW_CYCLE){
            
            // track if any point classes have changed
            boolean classChanged;
            if (this.K < 3) {
                // classes must change on the first cycle
                classChanged = true; 
            } else {
                // if it's not the first cycle, we won't know if the
                // class changed until after recalculating changes.
                classChanged = false;
                }
            
            
            // recolor points to match their closest cluster center
            int oldCluster = -1; // temporary value
            for (int i = 0; i < N; i++){
                if (this.K > 3) {
                    oldCluster = 
                            this.modelHistory.get(this.K - 3).getPointClass().get(i);
                }
                double minDist = Double.MAX_VALUE;
                int closestCenter = N; // this is just a temporary value
                for (int j = N; j < N+k; j++){
                    double dist = newModel.getPoints().get(i).squareDistanceTo(
                            newModel.getPoints().get(j));
                    if (dist < minDist) {
                        minDist = dist;
                        closestCenter = j;
                        // j is the index of the cluster
                        // j-N is how many indices that into the 
                        // subsequence of clusters at the tail of this.points
                    }
                }
                
                // reset the color to match the color of the appropriate center
                newModel.getPointClass().set(i, newModel.getPointClass().get(closestCenter));
                
                // check to see if there was a class change
                if (this.K > 3) {
                    int newCluster = 
                            newModel.getPointClass().get(i);
                    if (oldCluster != newCluster) {
                        classChanged = true;
                    }
                }
                
                
                
            }
            

            if (!classChanged) { 
                this.stoppedChangingClusters = true;
            }
        }
        
        // second stage: find new centers
        if (animationStage == AnimationStage.RECENTER_CLUSTERS) {
            
            
            
            // calculate mean coordinates for clusters
            // first just calculate sums of centers
            double[] sumsX = new double[k];
            double[] sumsY = new double[k];
            int[] countInCluster = new int[k];
            for (int i = 0; i < N; i++) {
                sumsX[newModel.getPointClass().get(i)] += newModel.getPoints().get(i).getX();
                sumsY[newModel.getPointClass().get(i)] += newModel.getPoints().get(i).getY();
                countInCluster[newModel.getPointClass().get(i)] += 1;
            }
            // replace old centers with new ones
            for (int j = 0; j < k; j++) {
                // if a cluster center has no closest points, 
                // we'll just leave it where it is
                if (countInCluster[j] != 0) {
                    double meanX = sumsX[j] / countInCluster[j];
                    double meanY = sumsY[j] / countInCluster[j];
                    newModel.getPoints().set(N+j, new Tuple(meanX, meanY));
                }
            }
            
            // if we didn't change any classes, we just delete the cluster centers and
            // leave the clusters colored as is
            if (this.stoppedChangingClusters) {
                for (int i = N+k-1; i >=  N; i--) {
                    newModel.getPoints().remove(i);
                }
            }
        }
        

        
        
        // in stage 3, we return all sample points to their default color
        // and change the colors of all curves to be the same
        if (animationStage == AnimationStage.NORMALIZE_COLORS) {
            // recolor points to red
            for (int i = 0; i < N; i++) {
                newModel.getPointClass().set(i, -1);
            }
        }
        
        
        // add newModel to modelHistory
        this.modelHistory.add(newModel);
        animationStage = animationStage.getNextStage(); // advance to the next animationStage
        checkRep();

    }

    @Override
    public void reset() {

        // might need to fix this.
        this.modelHistory = new ArrayList<ModelData>();
        ModelData startingModel = new ModelData();
        startingModel.setPoints(this.points);
        startingModel.setPointClasses(this.pointClasses);
        startingModel.setPointAlphas(this.pointAlpha);
        modelHistory.add(startingModel);
        this.K = 0;
        checkRep();
        this.stoppedChangingClusters = false;
    }
    
    @Override
    public void increaseOption1() {
        // pass
    }
    
    @Override
    public void decreaseOption1() {
        // pass
    }
    
    @Override
    public String getOption1Value() {
        return "";
    }
    
    @Override
    public String getOption1Sublabel() {
        return "";
    }

}
