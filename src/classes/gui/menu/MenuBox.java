package gui.menu;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;

public class MenuBox extends VBox {
    public MenuBox(Button...buttons) {
        setAlignment(Pos.CENTER);
        setSpacing(10);
        getChildren().addAll(buttons);
        setPrefSize(100,190);
        //setStyle("-fx-background-color: red;");
    }
}
