package Controller;

import java.util.Random;

import Common.ModelData;
import Common.ModelInterface;
import Common.ParametricFunction;
import Common.Tuple;
import View.DemoPanel;
import View.Plot;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;

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
    int frameDelay = 2048; // delaye between frames for animation
    
    
    // the constructor should also take a model class instance as a parameter and attach it
    // to the model instance variable.  Then the play method will make calls to the model.
    public PlotController(DemoPanel demoPanel, ModelInterface model){
        this.demoPanel = demoPanel;
        this.model = model;
        this.currentData = model.next();
        this.plot = new Plot(0.1, 400, 300, -8.0, 8.0, -6.0, 6.0);
        demoPanel.setGraph(plot);
        //ParametricFunction cusp = new ParametricFunction(t -> t*t, t -> t*t*t, -2.0, 2.0);
        //plot.addParametricCurve(cusp);
        //plot.addCircle(-1.0,  2.0,  4.0);
        setPlot();
    }
    
    private void setPlot(){
        // for now, we just draw points and curves, we'll save coloring based on classes for later
        for (Tuple point : currentData.getPoints()){
            plot.addCircle(point.getX(), point.getY(), 3);
        }
        for (ParametricFunction f : currentData.getCurves()){
            plot.addParametricCurve(f);
        }
    }
    
    private void clearPlot(){
        // TODO need method to remove all points and curves from plot
    }

    
    
    public void play(){
        stopped = false;
        new AnimationTimer() {

            long nextTime = -1;
            
            @Override
            public void handle(long now) {
                if (!stopped && now > nextTime){
                    incrementAnimation();
                    if (nextTime == -1) {nextTime = now;}
                    nextTime = now + frameDelay*1000000; // convert delays from milliseconds to nanoseconds
                    System.out.println("nextTime: "+nextTime);
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
    
    private void incrementAnimation(){
        //System.out.println("incrementing animation");
        currentData = model.next();
        clearPlot();
        setPlot();
    }
    
    public void speedUp() {
        if (frameDelay > 1) {
            frameDelay /= 2;
            //System.out.println("new frameDelay: "+frameDelay);
        }
        else {
            //System.out.println("Already at minimum frame delay: "+frameDelay);
        }
        
    }
    
    public void speedDown() {
        if (frameDelay <= 1024) {
            frameDelay *= 2;
            //System.out.println("new frameDelay: "+frameDelay);
        }
        else {
            //System.out.println("Already at maximum frame delay: "+frameDelay);
        }
        
    }
    
    public void rewind(){
        //System.out.println("rewinding");
        pause();
        model.reset();
        currentData = model.next();
        this.plot = new Plot(0.1, 400, 300, -8.0, 8.0, -6.0, 6.0);
        demoPanel.setGraph(plot);
    }

    
}
