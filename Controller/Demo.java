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
    
    /**
     * 
     * @return true if demo uses option1 selector
     */
    public boolean option1used();
    
    /**
     * 
     * @return the display value for option1 label
     * override with "" (or anything) if option1 is not used
     */
    public String option1Label();
    
    /**
     * 
     * @return the display value for option1 value
     * override with "" (or anything) if option1 is not used
     */
    public String option1Value();
    
    /**
     * 
     * @return the display value for option1 sublabel
     * override with "" (or anything) if option1 is not used
     */
    public String option1Sublabel();
    
}
