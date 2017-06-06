package View;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.layout.HBox;
import javafx.scene.layout.BorderPane;

public class DemoPanel extends BorderPane {
    
    Label titleLabel;
    Label descriptionLabel;
    private StringListener textListener;
    ChoiceBox<String> choiceBox;
    boolean option1Show = false;
    Label option1Label = new Label();
    Label option1Value = new Label();
    Label option1Sublabel = new Label();
    VBox optionsPanel;
    Button option1up;
    Button option1down;

    
    public DemoPanel(String title, String description){
        super();
        this.setPadding(new Insets(10, 10, 10, 10));
        
        
        // Create title and description for top of pane
        VBox titleBox = new VBox(10);
        titleLabel = new Label(title);
        titleLabel.setFont(new Font("Arial", 20));
        descriptionLabel = new Label(description);
        titleBox.getChildren().addAll(titleLabel, descriptionLabel);
        
        // Create control buttons for bottom of pane
        // Later, embed this in another VBox and add drop menu for preset demos and custom demo button
        // Will also need to add listeners to these buttons which activate message emitters
        HBox controls = new HBox(10);
        Button rewind = new Button("Reset");
        rewind.setOnAction(e -> emitMessageText("reset"));
        Button pause = new Button("Pause");
        pause.setOnAction(e -> emitMessageText("pause"));
        Button back = new Button("Back");
        back.setOnAction(e -> emitMessageText("back"));
        Button speedDown = new Button("Speed-");
        speedDown.setOnAction(e -> emitMessageText("speeddown"));
        Button play = new Button("Play");
        play.setOnAction(e -> emitMessageText("play"));
        Button speedUp = new Button("Speed+");
        speedUp.setOnAction(e -> emitMessageText("speedup"));
        Button next = new Button("Next");
        next.setOnAction(e -> emitMessageText("next"));
        Button screenShot = new Button("Save Screen Shot");
        screenShot.setOnAction(e -> emitMessageText("screenshot"));
        controls.getChildren().addAll(rewind, pause, 
                back, speedDown, play, speedUp, next, screenShot);
        
        // Create demo selection panel
        HBox demoSelections = new HBox(10);
        choiceBox = new ChoiceBox<>();
        
        demoSelections.getChildren().add(choiceBox);
        choiceBox.setOnAction(e -> emitMessageText(choiceBox.getValue()));
        
        // Combine controls and demo selection panel into a single node
        VBox controlPanel = new VBox(10);
        controlPanel.getChildren().addAll(controls, demoSelections);
        
        // Options Panel will be to the right of the plot
        optionsPanel = new VBox(10);
        option1Label = new Label("option1Label");
        option1Value = new Label("option1value");
        option1Sublabel = new Label("option1sublabel");
        option1up = new Button("UP");
        option1up.setOnAction((e) -> emitMessageText("option1UP"));
        option1down = new Button("DOWN");
        option1down.setOnAction((e) -> emitMessageText("option1DOWN"));
        optionsPanel.setPadding(new Insets(10, 10, 10, 10));
        optionsPanel.setMinWidth(200);
        if (option1Show){
            optionsPanel.getChildren().addAll(option1Label, option1Value, 
                option1up, option1down, option1Sublabel);
            this.setRight(optionsPanel);
        }
        
        this.setTop(titleBox);
        this.setCenter(new Label("Visualization"));
        this.setBottom(controlPanel);

    }
    
    public void setNumberOfDemos(int N){
        ArrayList<String> demoNames = new ArrayList<String>();
        for (int i = 0; i < N; i++){
            int K = i+1;
            String demoName = "Demo " + K;
            demoNames.add(demoName);
        }
        choiceBox.setItems(FXCollections.observableArrayList(demoNames));
        choiceBox.setOnAction(e -> emitMessageText(choiceBox.getValue()));
        choiceBox.setValue("Demo 1");
    }
    
    private void emitMessageText(String text){
        if (this.textListener != null) {
            this.textListener.textEmitted(text);
        }
    }
    
    public void setGraph(Plot plot){
        this.setCenter(plot);
    }

    public void setTitle(String title){
        this.titleLabel.setText(title);
    }
    
    public void setDescription(String description){
        this.descriptionLabel.setText(description);
    }
    
    public void setStringListener(StringListener listener){
        this.textListener = listener;
    }
    
    public void showOption1(boolean show, String label, String value, String sublabel) {
        option1Label.setText(label); 
        option1Value.setText(value);
        option1Sublabel.setText(sublabel);
        option1Show = show;
        if (!show) {
            optionsPanel.getChildren().removeAll(option1Label, option1Value, 
                    option1up, option1down, option1Sublabel);
                this.setRight(optionsPanel);
        } else {
            optionsPanel.getChildren().addAll(option1Label, option1Value, 
                    option1up, option1down, option1Sublabel);
                this.setRight(optionsPanel);
        }
    }
    
    public void setOption1Value(String text1, String text2) {
        option1Value.setText(text1);
        option1Sublabel.setText(text2);
    }
    
}
