package Controller;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import Common.ModelData;
import Common.ModelInterface;
import Common.ParametricFunction;
import Common.Tuple;
import View.DemoPanel;
import View.Plot;
import javafx.animation.AnimationTimer;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/*
 * PlotController is a controller class for Plot views.
 * It sends messages to the model corresponding to actions
 * play, pause, back, next, speedup, speeddown,
 * reset and saveScreenShotAsPNG.  The last of these is implemented
 * entirely within this class.  The others all call appropriate methods
 * in the model class (which must implement ModelInterface).
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
    double PLOT_X_MIN = -8.0;
    double PLOT_X_MAX = 8.0;
    double PLOT_Y_MIN = -6.0;
    double PLOT_Y_MAX = 6.0;
    
    
    /*
     * The constructor attaches a model, then points the instance variable
     * this.currentData to the model's first output, and displays that
     * data in a new Plot.
     */
    public PlotController(DemoPanel demoPanel, ModelInterface model){
        this.demoPanel = demoPanel;
        this.model = model;
        this.currentData = model.first();
        this.plot = new Plot(0.1, PLOT_WIDTH, PLOT_HEIGHT, 
                PLOT_X_MIN, PLOT_X_MAX, PLOT_Y_MIN, PLOT_Y_MAX);
        demoPanel.setGraph(plot);
        setPlot();
    }
    
    
    /*
     * setPlot creates a new plot to reflect the state of this.currentData.
     */
    private void setPlot(){
        this.plot = new Plot(0.1, PLOT_WIDTH, PLOT_HEIGHT, 
                PLOT_X_MIN, PLOT_X_MAX, PLOT_Y_MIN, PLOT_Y_MAX);
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
            else  if (currentData.getPointClass().get(i) == 0){
                plot.setPlotColor(Color.GREEN);
            }
            else  if (currentData.getPointClass().get(i) == 3){
                plot.setPlotColor(Color.YELLOW);
            }
            
            plot.addCircle(point.getX(), point.getY(), this.CIRCLE_RADIUS);
        }
        
        for (int i = 0; i < currentData.getCurves().size(); i++){
            ParametricFunction f = currentData.getCurves().get(i);
            int curveClass = currentData.getCurveClass().get(i);
            double alpha = 1.0;
            if (currentData.getCurveAlpha().size() > i) {
                alpha = currentData.getCurveAlpha().get(i);
            }
            if (curveClass == 2){ // BLACK
                plot.setPlotColor(new Color(0,0,0,alpha));
            }
            else if (curveClass == 1){  // BLUE
                plot.setPlotColor(new Color(0,0,1,alpha));
            }
            else  if (curveClass == -1 || curveClass == 0){  // RED
                plot.setPlotColor(new Color(1,0,0,alpha));
            }
            plot.addParametricCurve(f, this.LINE_THICKNESS);
        }
    }
    
    /*
     * removes all data from the current plot
     */
    private void clearPlot(){
        plot.removeAll();
    }

    
    /*
     * play runs the animation by repeatedly calling the model's next method,
     * as long as hasNext evaluates true.  After the first
     * iteration, play always checks the instance
     * variable stopped to see if another method has interrupted the
     * animation (e.g. the pause method changes stopped to true).  
     */
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
    
    /*
     * pause sets the instance variable stopped to true so that the
     * play method will terminate its loop
     */
    /**
     * stops animation
     */
    public void pause() {
        stopped = true;
    }
    
    /**
     * steps back one frame in the animation
     */
    public void back() {
        pause();
        if (model.hasPrevious()){
            currentData = model.previous();
            setPlot();
        }
    }
    
    /**
     * steps forward one frame in the animation ONLY IF there is a next frame
     */
    public void next() {
        pause();
        if (model.hasNext()){
            plot.removeAll();
            currentData = model.next();
            setPlot();
        }
    }
    
    /*
     * called after an update to the model
     */
    private void incrementAnimation(){
        clearPlot();
        currentData = model.next();
        setPlot();
    }
    
    /**
     * halves wait time between consecutive frames unless already at
     * fastest setting (1 millisecond)
     */
    public void speedUp() {
        if (frameDelay > 1) {
            frameDelay /= 2;
        }
    }
    
    /**
     * doubles wait time between consecutive frames unless already at
     * slowest setting (8.192 seconds)
     */
    public void speedDown() {
        if (frameDelay <= 8192) {
            frameDelay *= 2;
        }
    }
    
    /**
     * restarts model to first frame, removing all animation history
     * note that if the model implements a randomized algorithm, playing
     * after reset may result in a different animation than previously viewed
     */
    public void reset(){
        pause();
        model.reset();
        currentData = model.first();
        this.plot = new Plot(0.1, PLOT_WIDTH, PLOT_HEIGHT, -8.0, 8.0, -6.0, 6.0);
        demoPanel.setGraph(plot);
        setPlot();
    }
    
    /**
     * Opens a file chooser window so that the user can save the current
     * frame of animation and performs that save operation.  If the filename
     * selected by the user does not already end in .png, that extension
     * is automatically added.
     * @param window the parent of the file chooser pop-up window
     */
    public void saveScreenShotAsPNG(Stage window) {
        WritableImage image = plot.snapshot(new SnapshotParameters(), null);
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Screen Shot Filename and Location for Saving");
        File file = fileChooser.showSaveDialog(window);
        // append .png if not already there
        if (!file.getPath().substring(file.getPath().length()-4).equals(".png")) {
            file = new File(file.getAbsolutePath() + ".png");
        }
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
        } catch (IOException e) {
            // TODO: handle exception here
        }
    }

    
}
