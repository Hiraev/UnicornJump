package logic.platforms;

import animations.OpacityTransition;
import javafx.animation.Animation;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.util.Duration;


public class MoveFadePlatform extends Platform {
    private ParallelTransition parallelTransition;

    private TranslateTransition translateTransition;

    private OpacityTransition opacityTransition;


    public MoveFadePlatform(int windowWidth) {
        type = Type.MoveFade;

        translateTransition = new TranslateTransition(Duration.seconds(2), this);
        opacityTransition = new OpacityTransition(Duration.seconds(2), this);


        opacityTransition.setAutoReverse(true);
        opacityTransition.setCycleCount(Animation.INDEFINITE);

        translateTransition.setAutoReverse(true);
        translateTransition.setCycleCount(Animation.INDEFINITE);
        translateTransition.setByX(windowWidth / 10);
        translateTransition.setToX(windowWidth - windowWidth / 10);

        parallelTransition = new ParallelTransition();
        parallelTransition.getChildren().addAll(opacityTransition, translateTransition);
    }

    @Override
    public void play() {
        parallelTransition.play();
    }

    @Override
    public void pause() {
        parallelTransition.pause();
    }

    @Override
    public void continueAnimation() {
        parallelTransition.play();
    }

    @Override
    public void touch() {

    }

}
