package logic.platforms;

import animations.OpacityTransition;
import javafx.util.Duration;

public class VanishPlatform extends Platform {
    private OpacityTransition opacityTransition;

    public VanishPlatform(int gameWidth) {
        type = Type.Vanish;
        opacityTransition = new OpacityTransition(Duration.seconds(1), this);
        opacityTransition.setOnFinished(event -> setTranslateX(2 * gameWidth));
    }

    @Override
    public void play() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void continueIt() {

    }

    @Override
    public void touch() {
        opacityTransition.play();
    }
}
