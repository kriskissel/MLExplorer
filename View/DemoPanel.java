package View;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.layout.HBox;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class DemoPanel extends BorderPane {
    
    public String title = "Title";
    public String description = "Description";
    Label titleLabel;
    Label descriptionLabel;
    private StringListener textListener;
    
    // NOT SURE HOW TO DO THIS
    //FXCollections.observableArrayList presetDemoList;
    
    public DemoPanel(String title, String description){
        super();
        this.title = title;
        this.description = description;
        //presetDemoList = FXCollections.observableArrayList("Demo 1", "Demo 2", "Demo 3");
        
        
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
        Button rewind = new Button("Rewind");
        rewind.setOnAction(e -> emitMessageText("rewind"));
        Button pause = new Button("Pause");
        pause.setOnAction(e -> emitMessageText("pause"));
        Button speedDown = new Button("Speed-");
        speedDown.setOnAction(e -> emitMessageText("speeddown"));
        Button play = new Button("Play");
        play.setOnAction(e -> emitMessageText("play"));
        Button speedUp = new Button("Speed+");
        speedUp.setOnAction(e -> emitMessageText("speedup"));
        controls.getChildren().addAll(rewind, pause, speedDown, play, speedUp);
        
        // Create demo selection panel
        HBox demoSelections = new HBox(10);
        ChoiceBox<String> choiceBox = new ChoiceBox<>();
        choiceBox.setItems(FXCollections.observableArrayList("Demo 1", "Demo 2", "Demo 3"));
        choiceBox.setValue("Demo 1");
        Button customDemoButton = new Button("Customize Demo Settings");
        demoSelections.getChildren().addAll(choiceBox, customDemoButton);
        
        // Combine controls and demo selection panel into a single node
        VBox controlPanel = new VBox(10);
        controlPanel.getChildren().addAll(controls, demoSelections);
        
        this.setTop(titleBox);
        this.setCenter(new Label("Visualization"));
        this.setBottom(controlPanel);
    }
    
    private void emitMessageText(String text){
        if (this.textListener != null) {
            this.textListener.textEmitted(text);
        }
    }
    
    public void setGraph(Plot plot){
        this.setCenter(plot);
        //System.out.println(this.getChildren());
    }


    public void setTitle(String title){
        this.title = title;
        this.titleLabel.setText(title);
    }
    
    public void setDescription(String description){
        this.description = description;
        this.descriptionLabel.setText(description);
    }
    
    public void setStringListener(StringListener listener){
        this.textListener = listener;
    }
    
}
