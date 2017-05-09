import javafx.animation.Animation;
import javafx.animation.FillTransition;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class GameOverPane extends Pane {
    private Rectangle background;

    private Text gameOverText;
    private Label regame;
    private FillTransition fillTransition;

    GameOverPane() {
        regame = new Label("Начать сначала");
        gameOverText = new Text("Игра окончена");
        fillTransition = new FillTransition(Duration.seconds(2),gameOverText);
        fillTransition.setFromValue(Color.ANTIQUEWHITE);
        fillTransition.setToValue(Color.RED);
        fillTransition.setAutoReverse(true);
        fillTransition.setCycleCount(Animation.INDEFINITE);
        fillTransition.play();
        background = new Rectangle(Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT, Color.BLACK);
        background.setOpacity(0.3);
        getChildren().add(background);
        getChildren().add(gameOverText);
        gameOverText.setId("gameOverLabel");
        gameOverText.setTranslateX(Main.WINDOW_WIDTH/4);
        gameOverText.setTranslateY(Main.WINDOW_HEIGHT/2);
    }
}
