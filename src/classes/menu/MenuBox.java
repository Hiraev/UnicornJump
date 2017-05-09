package menu;
import javafx.scene.layout.VBox;

public class MenuBox extends VBox {
    public MenuBox(Button...buttons) {
        setSpacing(10);
        getChildren().addAll(buttons);
        setPrefSize(100,40);
    }
}
