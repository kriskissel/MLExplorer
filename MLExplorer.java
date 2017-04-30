import java.util.List;

import Common.ModelInterface;
import Controller.Demo;
import Controller.PlotController;
import Model.PerceptronDemo;
import Model.PolynomialRegressionBiasVarianceTradeoffDemo;
import Model.LinearRegressionVarianceDemo;
import View.DemoButtons;
import View.DemoPanel;
import View.StringListener;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class MLExplorer extends Application {
    
    /*
     * This is the main class and entry point for ML Explorer.
     * It initiates the main view and attaches a model and a controller.
     */
    
    PlotController plotController;
    int SCENE_WIDTH = 900;
    int SCENE_HEIGHT = 600;
    DemoPanel demoPanel;
    List<String> descriptions;
    List<String> initialDataSets;
    Demo demo;
    ModelInterface demoModel;
    
    public static void main(String[] args){
        launch(args);
    }
    
    @Override
    public void start(Stage window){
        
        // set up main window
        window.setTitle("ML Explorer");
        HBox mainLayout = new HBox();
        mainLayout.setMinWidth(400);
        
        // Attach button panel on left side of window
        DemoButtons buttonPanel = new View.DemoButtons();
        mainLayout.getChildren().add(buttonPanel);
        
        // define buttonPanel listener
        buttonPanel.setStringListener( new StringListener() {
            @Override
            public void textEmitted(String text) {
                if (text == null) { return; }
                switch (text) {
                case "Perceptron":
                    demo = new PerceptronDemo();
                    descriptions = demo.getDescriptions();
                    initialDataSets = demo.getInitialDataSets();
                    demoPanel.setTitle(demo.getTitle());
                    demoPanel.setDescription(descriptions.get(0));
                    demoPanel.setNumberOfDemos(descriptions.size());
                    demoModel = demo.getModel(initialDataSets.get(0));
                    plotController = new PlotController(demoPanel, demoModel);
                    break;
                
                case "Linear Regression: Variance":
                    demo = new LinearRegressionVarianceDemo();
                    descriptions = demo.getDescriptions();
                    initialDataSets = demo.getInitialDataSets();
                    demoPanel.setTitle(demo.getTitle());
                    demoPanel.setDescription(descriptions.get(0));
                    demoPanel.setNumberOfDemos(descriptions.size());
                    demoModel = demo.getModel(initialDataSets.get(0));
                    plotController = new PlotController(demoPanel, demoModel);
                    break;
                    
                case "Bias-Variance Tradeoff":
                    demo = new PolynomialRegressionBiasVarianceTradeoffDemo();
                    descriptions = demo.getDescriptions();
                    initialDataSets = demo.getInitialDataSets();
                    demoPanel.setTitle(demo.getTitle());
                    demoPanel.setDescription(descriptions.get(0));
                    demoPanel.setNumberOfDemos(descriptions.size());
                    demoModel = demo.getModel(initialDataSets.get(0));
                    plotController = new PlotController(demoPanel, demoModel);
                    break;
                    
                default:
                    System.out.println("No case in buttonPanel listener for " + text);
                    break;
                }
            }
        });
        
        // Attach demo panel on the right side of window
        demoPanel = new DemoPanel("Title", "DESCRIPTION GOES HERE");
        mainLayout.getChildren().add(demoPanel);
        
        // define demoPanel listener
        demoPanel.setStringListener(new StringListener() {
            @Override
            public void textEmitted(String text) {
                relayMessageToPlotController(text, window);
            }
        });
        
        // the Perceptron Demo is the default on startup
        demo = new PerceptronDemo(); 
        String demoTitle = demo.getTitle();
        descriptions = demo.getDescriptions();
        initialDataSets = demo.getInitialDataSets();
        demoPanel.setTitle(demoTitle);
        demoPanel.setDescription(descriptions.get(0));
        demoPanel.setNumberOfDemos(descriptions.size());
        ModelInterface demoModel = demo.getModel(initialDataSets.get(0));
        plotController = new PlotController(demoPanel, demoModel);
        
        Scene scene = new Scene(mainLayout, SCENE_WIDTH, SCENE_HEIGHT);
        window.setScene(scene);
        window.show();

        
    }
    
    /*
     * This method is part of the communication link from the
     * view (a DemoPanel) to the controller (a PlotController).
     * We use strings instead of enumerations because some messages
     * may include additional data, such as the value of a view
     * control (e.g. a slider).
     */
    private void relayMessageToPlotController(String message, Stage window){
        
        if (plotController == null || message == null) {return;}
        
        switch (message) {
        
        case "play":
            plotController.play();
            break;
        case "pause":
            plotController.pause();
            break;
        case "back":
            plotController.back();
            break;
        case "speedup":
            plotController.speedUp();
            break;
        case "speeddown":
            plotController.speedDown();
            break;
        case "next":
            plotController.next();
            break;
        case "reset":
            plotController.reset();
            break;
        case "screenshot":
            plotController.pause();
            plotController.saveScreenShotAsPNG(window);
        default:
            if (message.length() > 4 && message.substring(0, 4).equals("Demo")){
                int demoNumber = Integer.parseInt(message.substring(5)) - 1;
                plotController.pause();
                demoPanel.setDescription(descriptions.get(demoNumber));
                ModelInterface demoModel = demo.getModel(initialDataSets.get(demoNumber));
                plotController = new PlotController(demoPanel, demoModel);
            }
            break;
        }
    }

}
