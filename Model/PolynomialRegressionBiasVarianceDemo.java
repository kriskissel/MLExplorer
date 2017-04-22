package Model;

import java.util.ArrayList;
import java.util.List;

import Common.ModelInterface;
import Controller.Demo;

public class PolynomialRegressionBiasVarianceDemo implements Demo {
    
    private static String points = "points\n" +
            /* OLD
            "-2.0,-2.05,0\n" + 
            "-1.0,-1.1,0\n" + 
            "0.0,0.0,0\n" + 
            "1.0,1.07,0\n" + 
            "2.0,2.0,0\n" +
            "0.5,1.08,0\n" + 
            "2.5,1.99,0\n";
            */
            "-4.000,-4.093,-1\n" +
            "-3.500,-4.042,-1\n" +
            "-3.000,-3.717,-1\n" +
            "-2.500,-1.903,-1\n" +
            "-2.000,-2.509,-1\n" +
            "-1.500,-1.786,-1\n" +
            "-1.000,-1.108,-1\n" +
            "-0.500,-0.516,-1\n" +
            "0.000,0.485,-1\n" +
            "0.500,0.669,-1\n" +
            "1.000,1.434,-1\n" +
            "1.500,0.526,-1\n" +
            "2.000,1.928,-1\n" +
            "2.500,2.405,-1\n" +
            "3.000,3.274,-1\n" +
            "3.500,2.794,-1\n";
    
    @Override
    public ModelInterface getModel(String initialDataSet) {
        return new PolynomialRegressionBiasVarianceModel(initialDataSet);
    }

    @Override
    public String getTitle() {
        return "Linear Regression: Sample Size and Variance";
    }

    @Override
    public List<String> getDescriptions() {
        ArrayList<String> descriptions = new ArrayList<>();
        descriptions.add("Animation of fitting linear functions to " +
                "random subsamples of size 2 from a dataset");
        descriptions.add("Animation of fitting linear functions to " +
                "random subsamples of size 3 from a dataset");
        descriptions.add("Animation of fitting linear functions to " +
                "random subsamples of size 4 from a dataset");
        descriptions.add("Animation of fitting linear functions to " +
                "random subsamples of size 5 from a dataset");
        return descriptions;
    }
    
    @Override
    public List<String> getInitialDataSets() {
        List<String> initialDataSets = new ArrayList<String>();
        String demo1 = points + "samplesize\n2\npolynomialdegree\n1";
        initialDataSets.add(demo1);
        String demo2 = points + "samplesize\n3\npolynomialdegree\n1";
        initialDataSets.add(demo2);
        String demo3 = points + "samplesize\n4\npolynomialdegree\n1";
        initialDataSets.add(demo3);
        String demo4 = points + "samplesize\n5\npolynomialdegree\n1";
        initialDataSets.add(demo4);
        return initialDataSets;
    }

}
