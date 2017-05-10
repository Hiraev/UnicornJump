package gui.menu;

import animations.OpacityTransition;
import javafx.animation.Animation;
import javafx.animation.FillTransition;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;


public class Button extends StackPane {
    private Text text;
    private Rectangle bg;
    private FillTransition fillTransition;

    public Button(String string, int windowWidth) {
        bg = new Rectangle(100, 40);
        bg.setOpacity(0.5);
        fillTransition = new FillTransition(Duration.seconds(1), bg);
        fillTransition.setFromValue(Color.ANTIQUEWHITE);
        fillTransition.setToValue(Color.AQUA);
        fillTransition.setAutoReverse(true);
        fillTransition.setCycleCount(Animation.INDEFINITE);
        setAlignment(Pos.CENTER);

        this.text = new Text(string);

        text.setId("button"); //Установим красоту через CSS
        getChildren().addAll(bg,text);
        setCursor(Cursor.HAND);
        setPrefSize(100,40);

        setOnMouseEntered(event -> {
            fillTransition.play();
        });

        setOnMouseExited(event -> {
            bg.setFill(Color.AQUA);
            fillTransition.stop();
        });
    }
}
