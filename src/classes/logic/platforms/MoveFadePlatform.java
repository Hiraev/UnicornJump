package logic.platforms;

import animations.OpacityTransition;
import animations.ParallelTransitionThread;
import javafx.animation.Animation;
import javafx.animation.TranslateTransition;
import javafx.util.Duration;


public class MoveFadePlatform extends Platform {
    private ParallelTransitionThread parallelTransitionThread;

    private TranslateTransition translateTransition;

    private Thread thread;

    private OpacityTransition opacityTransition;


    public MoveFadePlatform(int windowWidth) {
        type = Type.MoveFade;

        translateTransition = new TranslateTransition(Duration.seconds(2), this);
        opacityTransition = new OpacityTransition(Duration.seconds(2), this);
        parallelTransitionThread = new ParallelTransitionThread(translateTransition);
        thread = new Thread(parallelTransitionThread);


        opacityTransition.setAutoReverse(true);
        opacityTransition.setCycleCount(Animation.INDEFINITE);

        translateTransition.setAutoReverse(true);
        translateTransition.setCycleCount(Animation.INDEFINITE);
        translateTransition.setByX(windowWidth / 10);
        translateTransition.setToX(windowWidth - windowWidth / 10);
    }

    @Override
    public void play() {
        thread.run();
        opacityTransition.play();
    }

    @Override
    public void pause() {
        opacityTransition.pause();
        parallelTransitionThread.pause();
    }

    @Override
    public void continueAnimation() {
        opacityTransition.play();
        parallelTransitionThread.continueTransition();
    }

    @Override
    public void touch() {

    }

}
