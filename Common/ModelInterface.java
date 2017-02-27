package Common;

public interface ModelInterface {

    public boolean hasNext();
    public ModelData next();
    public ModelData first();
    public void reset();
    public boolean hasPrevious();
    public ModelData previous();
    
}
