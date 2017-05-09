package platforms;

import javafx.animation.Animation;
import javafx.animation.TranslateTransition;
import javafx.util.Duration;

public class MovePlatform extends Platform {
    private TranslateTransition translateTransition;

    public MovePlatform(int gameWidth) {
        type = Type.Move;

        translateTransition = new TranslateTransition(Duration.seconds(1.5), this);
        translateTransition.setAutoReverse(true);
        translateTransition.setCycleCount(Animation.INDEFINITE);
        translateTransition.setByX(gameWidth / 10);
        translateTransition.setToX(gameWidth - gameWidth / 10);
        parallelTransition.getChildren().add(translateTransition);
    }


    @Override
    public void touch() {
    }
}
