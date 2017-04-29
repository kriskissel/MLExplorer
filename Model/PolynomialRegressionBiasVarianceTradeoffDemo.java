package Model;

import java.util.ArrayList;
import java.util.List;

import Common.ModelInterface;
import Controller.Demo;

public class PolynomialRegressionBiasVarianceTradeoffDemo implements Demo {
    
    private static String points = "points\n" +
            "-6.50000000,-4.37748820,-1\n" +
            "-6.16666667,-3.57234124,-1\n" +
            "-5.83333333,-3.40729842,-1\n" +
            "-5.50000000,-3.35855067,-1\n" +
            "-5.16666667,-3.16451345,-1\n" +
            "-4.83333333,-2.99615730,-1\n" +
            "-4.50000000,-2.69215114,-1\n" +
            "-4.16666667,-2.48164062,-1\n" +
            "-3.83333333,-2.07805619,-1\n" +
            "-3.50000000,-3.32079463,-1\n" +
            "-3.16666667,-3.27886565,-1\n" +
            "-2.83333333,-2.92185373,-1\n" +
            "-2.50000000,-3.69225369,-1\n" +
            "-2.16666667,-2.90846547,-1\n" +
            "-1.83333333,-2.95847103,-1\n" +
            "-1.50000000,-2.87480280,-1\n" +
            "-1.16666667,-3.14570296,-1\n" +
            "-0.83333333,-2.52113289,-1\n" +
            "-0.50000000,-3.13941446,-1\n" +
            "-0.16666667,-1.85800898,-1\n" +
            "0.16666667,-2.28393229,-1\n" +
            "0.50000000,-2.62476452,-1\n" +
            "0.83333333,-0.99984881,-1\n" +
            "1.16666667,-1.19928372,-1\n" +
            "1.50000000,-1.00368139,-1\n" +
            "1.83333333,-0.67502503,-1\n" +
            "2.16666667,0.78523096,-1\n" +
            "2.50000000,1.40428487,-1\n" +
            "2.83333333,2.72431792,-1\n" +
            "3.16666667,3.50842891,-1\n";
    
    @Override
    public ModelInterface getModel(String initialDataSet) {
        return new LinearRegressionVarianceModel(initialDataSet);
    }

    @Override
    public String getTitle() {
        return "Bias-Variance Tradeoff";
    }

    @Override
    public List<String> getDescriptions() {
        ArrayList<String> descriptions = new ArrayList<>();
        descriptions.add("Fitting linear functions to " +
                "random subsamples of size 10 from a dataset of cubic plus noise");
        descriptions.add("Fitting quadratic functions to " +
                "random subsamples of size 10 from a dataset of cubic plus noise");
        descriptions.add("Fitting cubic functions to " +
                "random subsamples of size 10 from a dataset of cubic plus noise");
        return descriptions;
    }
    
    @Override
    public List<String> getInitialDataSets() {
        List<String> initialDataSets = new ArrayList<String>();
        String demo1 = points + "samplesize\n10\npolynomialdegree\n1";
        initialDataSets.add(demo1);
        String demo2 = points + "samplesize\n10\npolynomialdegree\n2";
        initialDataSets.add(demo2);
        String demo3 = points + "samplesize\n10\npolynomialdegree\n3";
        initialDataSets.add(demo3);
        return initialDataSets;
    }

}
