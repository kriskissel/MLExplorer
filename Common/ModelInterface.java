package Common;

public interface ModelInterface {

    /**
     * 
     * @return true iff the model has another iteration available
     */
    public boolean hasNext();
    
    /**
     * 
     * @return the next iteration of the model
     */
    public ModelData next();
    
    /**
     * 
     * @return the initial state of the model
     */
    public ModelData first();
    
    /**
     * resets model to initial state, erases model iteration history
     */
    public void reset();
    /**
     * 
     * @return true iff the model has a previous iteration available
     */
    public boolean hasPrevious();
    
    /**
     * 
     * @return the previous iteration of the model
     */
    public ModelData previous();
    
}
