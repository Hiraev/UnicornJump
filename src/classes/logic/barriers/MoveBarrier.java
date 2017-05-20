package logic.barriers;

import javafx.animation.Animation;
import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class MoveBarrier extends Barrier {
    private RotateTransition rotateTransition;
    private TranslateTransition translateTransition;
    private ParallelTransition parallelTransition;

    public MoveBarrier(int windowWidth) {
        super(windowWidth);
        type = Type.Move;
        rotateTransition = new RotateTransition(Duration.seconds(0.2), this);
        rotateTransition.setFromAngle(-30);
        rotateTransition.setToAngle(30);
        rotateTransition.setAutoReverse(true);
        rotateTransition.setCycleCount(Animation.INDEFINITE);

        translateTransition = new TranslateTransition(Duration.seconds(2), this);
        translateTransition.setByX(windowWidth / 10);
        translateTransition.setToX(windowWidth - windowWidth / 10);
        translateTransition.setAutoReverse(true);
        translateTransition.setCycleCount(Animation.INDEFINITE);

        parallelTransition = new ParallelTransition();
        parallelTransition.getChildren().addAll(rotateTransition, translateTransition);
    }

    @Override
    public void action() {
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
}
