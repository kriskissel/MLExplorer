package Controller;

import java.util.List;

import Common.ModelInterface;

/**
 * represents a collection of demos
 * @author kriskissel
 *
 */
public interface Demo {

    /**
     * 
     * @param initialDataSet data set for parsing
     * @return a model
     */
    public ModelInterface getModel(String initialDataSet);
    
    /**
     * 
     * @return the title of the demo collection
     */
    public String getTitle();
    
    /**
     * 
     * @return list of descriptions of the various demos in this collection
     */
    public List<String> getDescriptions();
    
    /**
     * 
     * @return list of initial data sets corresponding to the various
     * demos in this collection
     */
    public List<String> getInitialDataSets();
    
}
