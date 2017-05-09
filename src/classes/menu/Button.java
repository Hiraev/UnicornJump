package menu;

import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;


public class Button extends StackPane {
    private Text text;
    private Rectangle bg;

    public Button(String string, int windowWidth) {
        setAlignment(Pos.CENTER);
        this.text = new Text(string);
        bg = new Rectangle(100, 40);
        bg.setOpacity(0.2);
        text.setId("button"); //Установим красоту через CSS


        getChildren().addAll(bg,text);
        setCursor(Cursor.HAND);
        setPrefSize(100,40);
    }
}
