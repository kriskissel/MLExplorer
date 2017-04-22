package View;

import Common.ParametricFunction;
import javafx.scene.paint.Color;

public interface PlotView {

    public void addParametricCurve(ParametricFunction vectorFunction, int lineThickness);
    
    public void removeLast();
    
    public void setPlotColor(Color color);
    
    public void addCircle(double xCenter, double yCenter, double radius);
}
