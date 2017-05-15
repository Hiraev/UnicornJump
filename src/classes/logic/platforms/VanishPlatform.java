package logic.platforms;

import animations.OpacityTransition;
import javafx.util.Duration;

public class VanishPlatform extends Platform {
    private int gameWidth;
    private OpacityTransition opacityTransition;

    public VanishPlatform(int gameWidth) {
        type = Type.Vanish;
        opacityTransition = new OpacityTransition(Duration.seconds(1), this);
        opacityTransition.setOnFinished(event -> setTranslateX(2 * gameWidth));
        this.gameWidth = gameWidth;
    }

    @Override
    public void play() {

    }


    @Override
    public void pause() {

    }

    @Override
    public void continueAnimation() {

    }

    @Override
    public void touch() {
        opacityTransition.play();
    }
}
