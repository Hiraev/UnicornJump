package animations;

import javafx.animation.Transition;
import javafx.scene.Node;
import javafx.util.Duration;


public class OpacityTransition extends Transition {
    private Node node;

    public void setNode(Node node) {
        this.node = node;
    }

    public OpacityTransition() {

    }
    public OpacityTransition(Duration duration, Node node) {
        this.node = node;
        setCycleDuration(duration);
    }
    @Override
    protected void interpolate(double frac) {
        node.setOpacity(frac);
    }
}