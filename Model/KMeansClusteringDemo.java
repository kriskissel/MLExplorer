package Model;

import java.util.ArrayList;
import java.util.List;

import Common.ModelInterface;
import Controller.Demo;

public class KMeansClusteringDemo implements Demo {

    // note that the last k points are used as initial
    // centers for the clustering algorithm
    
    // also, the final k points must be assigned values 0,1,2,...,k for
    // point classes, without repetition
    
    private static String points1 = "points\n" +
            "-2.381,-3.369,-1\n" +
            "-5.089,-2.880,-1\n" +
            "-3.839,-3.179,-1\n" +
            "-2.673,-2.749,-1\n" +
            "-3.446,-2.496,-1\n" +
            "-2.386,-3.507,-1\n" +
            "-2.550,-3.427,-1\n" +
            "-3.002,-3.001,-1\n" +
            "-2.998,-1.414,-1\n" +
            "-2.866,-3.381,-1\n" +
            "2.991,2.954,-1\n" +
            "2.170,3.856,-1\n" +
            "3.196,3.655,-1\n" +
            "3.306,4.084,-1\n" +
            "2.575,4.485,-1\n" +
            "2.953,4.067,-1\n" +
            "3.141,2.475,-1\n" +
            "2.489,3.581,-1\n" +
            "-1.973,3.193,-1\n" +
            "-1.315,2.657,-1\n" +
            "-2.172,2.696,-1\n" +
            "-1.893,2.942,-1\n" +
            "-2.000,3.001,-1\n" +
            "-2.339,3.426,-1\n" +
            "-4.020,4.074,0\n" +
            "-4.618,3.851,1\n";
    
    private static String points1_modified = "points\n" +
            "-2.381,-3.369,-1\n" +
            "-5.089,-2.880,-1\n" +
            "-3.839,-3.179,-1\n" +
            "-2.673,-2.749,-1\n" +
            "-3.446,-2.496,-1\n" +
            "-2.386,-3.507,-1\n" +
            "-2.550,-3.427,-1\n" +
            "-3.002,-3.001,-1\n" +
            "-2.998,-1.414,-1\n" +
            "-2.866,-3.381,-1\n" +
            "2.991,2.954,-1\n" +
            "2.170,3.856,-1\n" +
            "3.196,3.655,-1\n" +
            "3.306,4.084,-1\n" +
            "2.575,4.485,-1\n" +
            "2.953,4.067,-1\n" +
            "3.141,2.475,-1\n" +
            "2.489,3.581,-1\n" +
            "-1.973,3.193,-1\n" +
            "-1.315,2.657,-1\n" +
            "-2.172,2.696,-1\n" +
            "-1.893,2.942,-1\n" +
            "-2.000,3.001,-1\n" +
            "-2.339,3.426,-1\n" +
            "5.020,4.074,0\n" +
            "-4.618,3.851,1\n";
    
    private static String points2 = "points\n" +
            "-2.381,-3.369,-1\n" +
            "-5.089,-2.880,-1\n" +
            "-3.839,-3.179,-1\n" +
            "-2.673,-2.749,-1\n" +
            "-3.446,-2.496,-1\n" +
            "-2.386,-3.507,-1\n" +
            "-2.550,-3.427,-1\n" +
            "-3.002,-3.001,-1\n" +
            "-2.998,-1.414,-1\n" +
            "-2.866,-3.381,-1\n" +
            "2.991,2.954,-1\n" +
            "2.170,3.856,-1\n" +
            "3.196,3.655,-1\n" +
            "3.306,4.084,-1\n" +
            "2.575,4.485,-1\n" +
            "2.953,4.067,-1\n" +
            "3.141,2.475,-1\n" +
            "2.489,3.581,-1\n" +
            "-1.973,3.193,-1\n" +
            "-1.315,2.657,-1\n" +
            "-2.172,2.696,-1\n" +
            "-1.893,2.942,-1\n" +
            "-2.000,3.001,-1\n" +
            "-2.339,3.426,-1\n" +
            "-4.020,4.074,0\n" +
            "-4.618,3.851,1\n" +
            "-4.096,3.703,2\n";
    
    private static String points3 = "points\n" +
            "-2.381,-3.369,-1\n" +
            "-5.089,-2.880,-1\n" +
            "-3.839,-3.179,-1\n" +
            "-2.673,-2.749,-1\n" +
            "-3.446,-2.496,-1\n" +
            "-2.386,-3.507,-1\n" +
            "-2.550,-3.427,-1\n" +
            "-3.002,-3.001,-1\n" +
            "-2.998,-1.414,-1\n" +
            "-2.866,-3.381,-1\n" +
            "2.991,2.954,-1\n" +
            "2.170,3.856,-1\n" +
            "3.196,3.655,-1\n" +
            "3.306,4.084,-1\n" +
            "2.575,4.485,-1\n" +
            "2.953,4.067,-1\n" +
            "3.141,2.475,-1\n" +
            "2.489,3.581,-1\n" +
            "-1.973,3.193,-1\n" +
            "-1.315,2.657,-1\n" +
            "-2.172,2.696,-1\n" +
            "-1.893,2.942,-1\n" +
            "-2.000,3.001,-1\n" +
            "-2.339,3.426,-1\n" +
            "-4.020,4.074,0\n" +
            "-4.618,3.851,1\n" +
            "-4.096,3.703,2\n" +
            "-4.000,3.555,3\n";
    
    
    private static String points4 = "points\n" +
            "-2.381,-3.369,-1\n" +
            "-5.089,-2.880,-1\n" +
            "-3.839,-3.179,-1\n" +
            "-2.673,-2.749,-1\n" +
            "-3.446,-2.496,-1\n" +
            "-2.386,-3.507,-1\n" +
            "-2.550,-3.427,-1\n" +
            "-3.002,-3.001,-1\n" +
            "-2.998,-1.414,-1\n" +
            "-2.866,-3.381,-1\n" +
            "2.991,2.954,-1\n" +
            "2.170,3.856,-1\n" +
            "3.196,3.655,-1\n" +
            "3.306,4.084,-1\n" +
            "2.575,4.485,-1\n" +
            "2.953,4.067,-1\n" +
            "3.141,2.475,-1\n" +
            "2.489,3.581,-1\n" +
            "-1.973,3.193,-1\n" +
            "-1.315,2.657,-1\n" +
            "-2.172,2.696,-1\n" +
            "-1.893,2.942,-1\n" +
            "-2.000,3.001,-1\n" +
            "-2.339,3.426,-1\n" +
            "-4.020,4.074,0\n" +
            "-4.618,3.851,1\n" +
            "-4.096,3.703,2\n" +
            "4.000,-3.555,3\n";
    
    private static String points5 = "points\n" +
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
            "3.000,3.274,0\n" +
            "3.500,2.794,1\n";
    
    
    
    @Override
    public ModelInterface getModel(String initialDataSet) {
        return new KMeansClusteringModel(initialDataSet);
    }

    @Override
    public String getTitle() {
        return "K-Means Clustering";
    }

    @Override
    public List<String> getDescriptions() {
        ArrayList<String> descriptions = new ArrayList<>();
        descriptions.add("Animation of K-Means Clustering with 2 Clusters");
        descriptions.add("Animation of K-Means Clustering with 2 Clusters");
        descriptions.add("Animation of K-Means Clustering with 3 Clusters");
        descriptions.add("Animation of K-Means Clustering with 4 Clusters");
        descriptions.add("Animation of K-Means Clustering with 4 Clusters");
        descriptions.add("Animation of K-Means Clustering with 2 Clusters");
        return descriptions;
    }

    @Override
    public List<String> getInitialDataSets() {
        List<String> initialDataSets = new ArrayList<String>();
        String demo1 = points1 + "k\n2\n"; // 2 clusters
        initialDataSets.add(demo1);
        String demo2 = points1_modified + "k\n2\n"; // 2 clusters
        initialDataSets.add(demo2);
        String demo3 = points2 + "k\n3\n"; // 2 clusters
        initialDataSets.add(demo3);
        String demo4 = points3 + "k\n4\n"; // 2 clusters
        initialDataSets.add(demo4);
        String demo5 = points4 + "k\n4\n"; // 2 clusters
        initialDataSets.add(demo5);
        String demo6 = points5 + "k\n2\n"; // 2 clusters
        initialDataSets.add(demo6);
        return initialDataSets;
    }

    @Override
    public boolean option1used() {
        return false;
    }

    @Override
    public String option1Label() {
        return "";
    }

    @Override
    public String option1Value() {
        return "";
    }
    
    @Override
    public String option1Sublabel() {
        return "";
    }
    
    

}
