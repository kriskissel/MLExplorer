import java.util.List;

import Common.ModelInterface;
import Controller.Demo;
import Controller.PlotController;
import Model.PerceptronDemo;
import Model.PolynomialRegressionBiasVarianceDemo;
import View.DemoButtons;
import View.DemoPanel;
import View.StringListener;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class MLExplorer extends Application {
    
    /**
     * This is the main class for ML Explorer.
     * It initiates the main view and attaches a model and a controller.
     */
    
    PlotController plotController;
    int SCENE_WIDTH = 900;
    int SCENE_HEIGHT = 600;
    DemoPanel demoPanel;
    List<String> descriptions;
    List<String> initialDataSets;
    Demo demo;
    
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
                System.out.println("buttonPanel listener received message: "+text);
                switch (text) {
                case "Overfitting":
                    demo = new PolynomialRegressionBiasVarianceDemo();
                    descriptions = demo.getDescriptions();
                    initialDataSets = demo.getInitialDataSets();
                    demoPanel.setTitle(demo.getTitle());
                    demoPanel.setDescription(descriptions.get(0));
                    demoPanel.setNumberOfDemos(descriptions.size());
                    ModelInterface demoModel = demo.getModel(initialDataSets.get(0));
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
                relayMessageToPlotController(text);
            }
        });
        
        // TEMP for Testing:
        // rewrite with subroutine for selecting demo via button panel
        demo = new PerceptronDemo(); // this is the default option on startup
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
    
    private void relayMessageToPlotController(String message){
        // really should refactor this with enumerations and a switch statement
        if (plotController == null) {return;}
        if (message.equals("play")){
            plotController.play();
        }
        if (message.equals("pause")){
            plotController.pause();
        }
        if (message.equals("back")){
            plotController.back();
        }
        if (message.equals("speedup")){
            plotController.speedUp();
        }
        if (message.equals("speeddown")){
            plotController.speedDown();
        }
        if (message.equals("next")){
            plotController.next();
        }
        if (message.equals("reset")) {
            plotController.reset();
        }
        if (message.equals("Demo 1")){
            plotController.pause();
            demoPanel.setDescription(descriptions.get(0));
            ModelInterface demoModel = demo.getModel(initialDataSets.get(0));
            plotController = new PlotController(demoPanel, demoModel);
        }
        if (message.equals("Demo 2")){
            plotController.pause();
            demoPanel.setDescription(descriptions.get(1));
            ModelInterface demoModel = demo.getModel(initialDataSets.get(1));
            plotController = new PlotController(demoPanel, demoModel);
        }
    }

}
