package Model;

import java.util.ArrayList;
import java.util.Random;

import Common.ModelData;
import Common.ParametricFunction;
import Common.Tuple;

public class RandomModel implements Common.ModelInterface {
    
    /*
     * This model will be used for testing.  It will randomly generate sequences of points to plot, and
     * it will generate a line that moves position and slope at random.
     */

    private ArrayList<ModelData> modelDataHistory = new ArrayList<ModelData>();
    private int K = -1; // points to the index of the current model
    private Random random = new Random();
    
    private void iterateModel() {
        ModelData nextModel;
        if (modelDataHistory.size() > 0) {
            nextModel = modelDataHistory.get(modelDataHistory.size() - 1).clone();
        }
        else {
            nextModel = new ModelData();
        }
        Double xNew = 5*random.nextDouble();
        Double yNew = 5*random.nextDouble();
        Integer classNew = random.nextInt(2);
        Tuple pointNew = new Tuple(xNew, yNew);
        nextModel.addPoint(pointNew, classNew);
        Double xInitial = 5 * random.nextDouble();
        Double xFinal = 5 * random.nextDouble();
        Double yInitial = 5 * random.nextDouble();
        Double yFinal = 5 * random.nextDouble();
        Double xDelta = xFinal-xInitial;
        Double yDelta = yFinal - yInitial;
        ParametricFunction fNew = new ParametricFunction(t -> (xInitial + t * xDelta), t -> (yInitial + t * yDelta), 0.0, 1.0);
        ArrayList<ParametricFunction> curvesNew = new ArrayList<ParametricFunction>();
        curvesNew.add(fNew);
        nextModel.setCurves(curvesNew);
        ArrayList<Integer> curveClassNew = new ArrayList<Integer>();
        curveClassNew.add(random.nextInt(2));
        nextModel.setCurveClass(curveClassNew);
        modelDataHistory.add(nextModel);
    }
    
    @Override
    public boolean hasNext() {
        return true;
    }

    @Override
    public ModelData next() {
        if (K == modelDataHistory.size() - 1){
            iterateModel();
        }
        K++;
        return modelDataHistory.get(K);
    }

    @Override
    public void reset() {
        this.modelDataHistory = new ArrayList<ModelData>();
        this.K = -1;
    }

    @Override
    public ModelData first() {
        return modelDataHistory.get(0);
    }
    
    @Override
    public boolean hasPrevious() {
        return K > 0;
    }

    @Override
    public ModelData previous() {
        // if there is no previous, it continues to output the initial model data rather than null or an error
        if (K > 0) {K--;}
        return modelDataHistory.get(K);
    }

    
    
    
}
