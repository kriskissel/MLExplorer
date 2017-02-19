package View;
import java.util.function.Function;
import Common.Tuple;
import javafx.scene.paint.Color;

public interface PlotView {

    public void addParametricCurve(Function<Double, Tuple> vectorFunction, double tMin, double tMax);
    
    public void removeLast();
    
    public void setPlotColor(Color color);
    
    public void addCircle(double xCenter, double yCenter, double radius);
}
