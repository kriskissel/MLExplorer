package View;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class DemoButtons extends VBox {
    
    private StringListener textListener;
    
    public DemoButtons() {
        super(20);
        this.setAlignment(Pos.CENTER);
        this.setPadding(new Insets(10,10,10,10));
        
        Button button1 = new Button("Perceptron");
        Button button2 = new Button("Linear Regression: Variance");
        
        button1.setOnAction(e -> {
            if (this.textListener != null) {
                this.textListener.textEmitted("Perceptron");
            }
        });
        
        button2.setOnAction(e -> {
            if (this.textListener != null) {
                this.textListener.textEmitted("Linear Regression: Variance");
            }
        });
        
        this.getChildren().addAll(button1, button2);
    }

    public void setStringListener(StringListener listener){
        this.textListener = listener;
    }
    
}
