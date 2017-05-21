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
        setId("moveFadePlatform");
        type = Type.MoveFade;

        translateTransition = new TranslateTransition(Duration.seconds(2), this);
        opacityTransition = new OpacityTransition(Duration.seconds(2), this);


        opacityTransition.setAutoReverse(true);
        opacityTransition.setCycleCount(Animation.INDEFINITE);

        translateTransition.setAutoReverse(true);
        translateTransition.setCycleCount(Animation.INDEFINITE);
        translateTransition.setFromX(windowWidth / 15);
        translateTransition.setToX(windowWidth - windowWidth / 15 - getWidth());

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
    public void continueIt() {
        parallelTransition.play();
    }

    @Override
    public void touch() {

    }

}
