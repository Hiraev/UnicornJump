package platforms;

import javafx.animation.Animation;
import javafx.animation.Transition;
import javafx.scene.Node;
import javafx.util.Duration;


public class OpacityTransition extends Transition {
    private Node node;
    public OpacityTransition(Duration duration, Node node) {
        setAutoReverse(true);
        setCycleDuration(duration);
        this.node = node;
        setCycleCount(Animation.INDEFINITE);
    }
    @Override
    protected void interpolate(double frac) {
        node.setOpacity(frac);
    }
}
