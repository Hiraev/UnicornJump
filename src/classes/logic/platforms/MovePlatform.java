package logic.platforms;

import javafx.animation.Animation;
import javafx.animation.TranslateTransition;
import javafx.util.Duration;

public class MovePlatform extends Platform {
    private TranslateTransition translateTransition;

    public MovePlatform(int gameWidth) {
        setId("movePlatform");
        type = Type.Move;

        translateTransition = new TranslateTransition(Duration.seconds(1.5), this);
        translateTransition.setAutoReverse(true);
        translateTransition.setCycleCount(Animation.INDEFINITE);
        translateTransition.setFromX(gameWidth / 15);
        translateTransition.setToX(gameWidth - gameWidth / 15 - getWidth());
    }

    @Override
    public void play() {
        translateTransition.play();
    }

    @Override
    public void pause() {
        translateTransition.pause();
    }

    @Override
    public void continueIt() {
        translateTransition.play();
    }

    @Override
    public void touch() {
    }
}
