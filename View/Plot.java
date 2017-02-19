package View;
import java.util.function.Function;
import Common.Tuple;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;

public class Plot extends Pane implements PlotView {
        
    private Axes axes;
    private Color plotColor = Color.ORANGE.deriveColor(0, 1, 1, 0.6);
    private double tInc;

    public Plot(double tInc, Axes axes) {
        this.axes = axes;
        this.tInc = tInc;

        setMinSize(Pane.USE_PREF_SIZE, Pane.USE_PREF_SIZE);
        setPrefSize(axes.getPrefWidth(), axes.getPrefHeight());
        setMaxSize(Pane.USE_PREF_SIZE, Pane.USE_PREF_SIZE);

        getChildren().setAll(axes);
    }

    @Override
    public void addParametricCurve(Function<Double, Tuple> f, double tMin, double tMax){
        Path path = new Path();
        path.setStroke(plotColor);
        path.setStrokeWidth(2);
        path.setClip(new Rectangle(0, 0, axes.getPrefWidth(), axes.getPrefHeight()));

        double t = tMin;
        Tuple point = f.apply(t);

        path.getElements().add(new MoveTo(mapX(point.getX(), axes), mapY(point.getY(), axes)));

        t += this.tInc;
        while (t < tMax) {
            point = f.apply(t);
            path.getElements().add(new LineTo(mapX(point.getX(), axes), mapY(point.getY(), axes)));
            t += tInc;
        }

        this.getChildren().add(path);
    }

    @Override
    public void setPlotColor(Color color){
        this.plotColor = color;
    }
    
    @Override
    public void addCircle(double xCenter, double yCenter, double radius){ 
        Circle circle = new Circle(mapX(xCenter, axes), mapY(yCenter, axes), radius);
        circle.setStroke(plotColor);
        circle.setFill(plotColor);
        this.getChildren().add(circle);
    }

    private double mapX(double x, Axes axes) {
        double tx = axes.getPrefWidth() / 2;
        double sx = axes.getPrefWidth() / 
                (axes.getXAxis().getUpperBound() - 
                        axes.getXAxis().getLowerBound());

        return x * sx + tx;
    }

    private double mapY(double y, Axes axes) {
        double ty = axes.getPrefHeight() / 2;
        double sy = axes.getPrefHeight() / 
                (axes.getYAxis().getUpperBound() - 
                        axes.getYAxis().getLowerBound());

        return -y * sy + ty;
    }

    @Override
    public void removeLast(){
        if (this.getChildren().size() > 0){
            this.getChildren().remove(this.getChildren().size() - 1);
        }
    }
}
