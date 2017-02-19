import Common.ParametricFunction;
import View.DemoButtons;
import View.DemoPanel;
import View.Plot;
import View.StringListener;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MLExplorer extends Application {
    
    /**
     * This is the main class for ML Explorer.
     * It initiates the main view and attaches a model and a controller.
     */
    
    public static void main(String[] args){
        launch(args);
    }
    
    @Override
    public void start(Stage window){
        
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
            }
        });
        
        // Attach demo panel on the right side of window
        DemoPanel demoPanel = new DemoPanel("Title", "DESCRIPTION GOES HERE");
        mainLayout.getChildren().add(demoPanel);
        
        // define demoPanel listener
        demoPanel.setStringListener(new StringListener() {
            @Override
            public void textEmitted(String text) {
                System.out.println("demoPanel listener received message: "+text);
            }
        });
        
        // TEMP for Testing:
        // Add a graphic in the DemoPanel center
        Plot plot = new Plot(0.1, 400, 300, -8.0, 8.0, -6.0, 6.0);
        demoPanel.setGraph(plot);
        plot.addCircle(-1.0, 2.0, 3);
        ParametricFunction cusp = new ParametricFunction(t -> t*t, t -> t*t*t, -2.0, 2.0);
        plot.addParametricCurve(cusp);
        
        Scene scene = new Scene(mainLayout, 600, 500);
        window.setScene(scene);
        window.show();
        
        demoPanel.setTitle("Changed Title");
        
    }

}
