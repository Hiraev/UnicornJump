package platforms;

import javafx.animation.Animation;
import javafx.animation.TranslateTransition;
import javafx.util.Duration;

public class MoveFadePlatform extends Platform {
    private TranslateTransition translateTransition;
    private OpacityTransition opacityTransition;


    public MoveFadePlatform(int windowWidth) {
        type = Type.MoveFade;

        translateTransition = new TranslateTransition(Duration.seconds(1), this);
        opacityTransition = new OpacityTransition(Duration.seconds(1), this);
        translateTransition.setByX(windowWidth / 10);
        translateTransition.setToX(windowWidth - windowWidth / 10);
        translateTransition.setAutoReverse(true);
        translateTransition.setCycleCount(Animation.INDEFINITE);
        parallelTransition.getChildren().addAll(opacityTransition, translateTransition);
    }




    @Override
    public void touch() {

    }
}
