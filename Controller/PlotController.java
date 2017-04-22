package Controller;

import Common.ModelData;
import Common.ModelInterface;
import Common.ParametricFunction;
import Common.Tuple;
import View.DemoPanel;
import View.Plot;
import javafx.animation.AnimationTimer;
import javafx.scene.paint.Color;

/*
 * 
 * 
 * PLOTCONTROLLER WILL ALSO NEED TO HAVE A MODEL ATTACHED SO THAT IT CAN QUERY THE MODEL
 * FOR UPDATES VIA NEXT AND HASNEXT METHODS
 */


public class PlotController {

    Plot plot;
    DemoPanel demoPanel;
    ModelInterface model;
    ModelData currentData;
    boolean stopped = false;
    long frameDelay = 512; // delay between frames for animation
    int PLOT_WIDTH = 600;
    int PLOT_HEIGHT = 450;
    int CIRCLE_RADIUS = 10;
    int LINE_THICKNESS = 4;
    
    
    // the constructor should also take a model class instance as a parameter and attach it
    // to the model instance variable.  Then the play method will make calls to the model.
    public PlotController(DemoPanel demoPanel, ModelInterface model){
        this.demoPanel = demoPanel;
        this.model = model;
        this.currentData = model.first();
        this.plot = new Plot(0.1, PLOT_WIDTH, PLOT_HEIGHT, -8.0, 8.0, -6.0, 6.0);
        demoPanel.setGraph(plot);
        setPlot();
    }
    
    private void setPlot(){
        // for now, we just draw points and curves, we'll save coloring based on classes for later
        this.plot = new Plot(0.1, PLOT_WIDTH, PLOT_HEIGHT, -8.0, 8.0, -6.0, 6.0);
        demoPanel.setGraph(plot);
        for (int i = 0; i < currentData.getPoints().size(); i++){
            Tuple point = currentData.getPoints().get(i);
            if (currentData.getPointClass().get(i) == 2){
                plot.setPlotColor(Color.BLACK);
            }
            else if (currentData.getPointClass().get(i) == 1){
                plot.setPlotColor(Color.BLUE);
            }
            else  if (currentData.getPointClass().get(i) == -1){
                plot.setPlotColor(Color.RED);
            }
            
            plot.addCircle(point.getX(), point.getY(), this.CIRCLE_RADIUS);
        }
        
        for (int i = 0; i < currentData.getCurves().size(); i++){
            ParametricFunction f = currentData.getCurves().get(i);
            if (currentData.getCurveClass().get(i) == 2){
                plot.setPlotColor(Color.BLACK);
            }
            else if (currentData.getCurveClass().get(i) == 1){
                plot.setPlotColor(Color.BLUE);
            }
            else  if (currentData.getCurveClass().get(i) == -1){
                plot.setPlotColor(Color.RED);
            }
            plot.addParametricCurve(f, this.LINE_THICKNESS);
        }
    }
    
    private void clearPlot(){
        plot.removeAll();
    }

    
    
    public void play(){
        stopped = false;
        new AnimationTimer() {

            long lastUpdateTime = -1;
            
            @Override
            public void handle(long now) {
                if (!model.hasNext()) {stopped = true;}
                if (!stopped && now > (lastUpdateTime + frameDelay * 1000000)){
                    incrementAnimation();
                    lastUpdateTime = now;
                }
                if (stopped) {
                    stop();
                }
                }
                
        }.start();
        }
    
    public void pause() {
        stopped = true;
    }
    
    public void back() {
        pause();
        if (model.hasPrevious()){
            currentData = model.previous();
            setPlot();
        }
    }
    
    public void next() {
        pause();
        if (model.hasNext()){
            currentData = model.next();
            setPlot();
        }
    }
    
    private void incrementAnimation(){
        clearPlot();
        currentData = model.next();
        setPlot();
    }
    
    public void speedUp() {
        if (frameDelay > 1) {
            frameDelay /= 2;
        }
    }
    
    public void speedDown() {
        if (frameDelay <= 8192) {
            frameDelay *= 2;
        }
    }
    
    public void reset(){
        pause();
        model.reset();
        currentData = model.first();
        this.plot = new Plot(0.1, PLOT_WIDTH, PLOT_HEIGHT, -8.0, 8.0, -6.0, 6.0);
        demoPanel.setGraph(plot);
        setPlot();
    }

    
}
