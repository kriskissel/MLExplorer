package Controller;

import java.util.List;

import Common.ModelInterface;

public interface Demo {

    public ModelInterface getModel(String initialDataSet);
    public String getTitle();
    public List<String> getDescriptions();
    public List<String> getInitialDataSets();
    
}
