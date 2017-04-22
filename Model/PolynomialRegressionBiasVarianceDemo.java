package Model;

import java.util.ArrayList;
import java.util.List;

import Common.ModelInterface;
import Controller.Demo;

public class PolynomialRegressionBiasVarianceDemo implements Demo {
    
    @Override
    public ModelInterface getModel(String initialDataSet) {
        return new PolynomialRegressionBiasVarianceModel(initialDataSet);
    }

    @Override
    public String getTitle() {
        return "Polynomial Regression: Bias and Variance";
    }

    @Override
    public List<String> getDescriptions() {
        ArrayList<String> descriptions = new ArrayList<>();
        descriptions.add("Animation of fitting various linear functions to " +
                "various subsamples of a dataset");
        return descriptions;
    }
    
    @Override
    public List<String> getInitialDataSets() {
        List<String> initialDataSets = new ArrayList<String>();
        String demo1 = "points\n"+"-2.0,-2.0,0\n-1.0,-1.0,0\n0.0,0.0,0\n1.0,1.0,0\n2.0,2.0,0\n" +
                "0.5,1.0,0\n2.5,2.0,0\n";
        initialDataSets.add(demo1);
        return initialDataSets;
    }

}
