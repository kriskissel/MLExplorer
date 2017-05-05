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
        Button button3 = new Button("Bias-Variance Tradeoff");
        Button button4 = new Button("K-Means Clustering");
        
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
        
        button3.setOnAction(e -> {
            if (this.textListener != null) {
                this.textListener.textEmitted("Bias-Variance Tradeoff");
            }
        });
        
        button4.setOnAction(e -> {
            if (this.textListener != null) {
                this.textListener.textEmitted("K-Means Clustering");
            }
        });
        
        this.getChildren().addAll(button1, button2, button3, button4);
    }

    public void setStringListener(StringListener listener){
        this.textListener = listener;
    }
    
}
