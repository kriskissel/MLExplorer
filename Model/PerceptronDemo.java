package Model;

import java.util.ArrayList;
import java.util.List;

import Common.ModelInterface;
import Controller.Demo;

public class PerceptronDemo implements Demo {

    @Override
    public ModelInterface getModel(String initialDataSet) {
        return new PerceptronModel(initialDataSet);
    }

    @Override
    public String getTitle() {
        return "Perceptron";
    }

    @Override
    public List<String> getDescriptions() {
        ArrayList<String> descriptions = new ArrayList<>();
        descriptions.add("Animation of perceptron learning algorithm applied " +
                "to linearly separable data");
        descriptions.add("Animation of perceptron learning algorithm with data that " +
                "is not linearly separable");
        return descriptions;
    }
    
    @Override
    public List<String> getInitialDataSets() {
        List<String> initialDataSets = new ArrayList<String>();
        String demo1 = "points\n"+"1.0,-2.0,1\n5.0,-1.0,1\n3.0,1.0,1\n3.0,5.0,1\n-2.0,-3.0,1\n" +
                "0.0,1.55,-1\n-2.0,4.0,-1\n-4.0,2.0,-1\n-2.0,4.0,-1\n-3.0,-3.0,-1\n" +
                "decision vector\n" + "0.5,0.0,1.0";
        String demo2 = "points\n" +
                "0.0,0.0,1\n0.0,4.0,-1\n4.0,0.0,-1\n4.0,4.0,1\n" +
                "decision vector\n" + "0.5,0.0,1.0";
        initialDataSets.add(demo1);
        initialDataSets.add(demo2);
        return initialDataSets;
    }

    
    
}
