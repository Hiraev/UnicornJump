package logic.platforms;

import animations.OpacityTransition;
import javafx.animation.Animation;
import javafx.util.Duration;

public class BasicFadePlatform extends Platform {
    private OpacityTransition opacityTransition;

    public BasicFadePlatform() {
        type = Type.BasicFade;
        opacityTransition = new OpacityTransition(Duration.seconds(2), this);
        opacityTransition.setAutoReverse(true);
        opacityTransition.setCycleCount(Animation.INDEFINITE);
    }

    @Override
    public void play() {
        opacityTransition.play();
    }

    @Override
    public void touch() {

    }

    @Override
    public void pause() {
        opacityTransition.pause();
    }

    @Override
    public void continueIt() {
        opacityTransition.play();
    }
}
