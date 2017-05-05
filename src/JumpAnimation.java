import javafx.animation.Transition;
import javafx.scene.Node;
import javafx.util.Duration;

import java.util.concurrent.atomic.AtomicBoolean;

public class JumpAnimation extends Transition {
    private Node node;
    private double currentPosition;
    private double maxValue = Math.pow(3, Math.E + 1) * 5;
    private FallAnimation fallAnimation;
    AtomicBoolean falling;

    public JumpAnimation(Node node) {
        falling = new AtomicBoolean(false);
        this.node = node;
        setCycleDuration(Duration.seconds(1));
        currentPosition = node.getTranslateY() - maxValue;
        fallAnimation = new FallAnimation();
        setOnFinished(event -> {
            fallAnimation.play();
            falling.set(true);
        });
    }

    @Override
    protected void interpolate(double frac) {
        node.setTranslateY(currentPosition + Math.pow(2 * (1 - frac) + 1, Math.E + (1 - frac)) * 5);
    }

    @Override
    public void play() {
        currentPosition = node.getTranslateY() - maxValue;
        falling.set(false);
        fallAnimation.stop();
        super.play();
        System.out.println("Jumping");
    }

    @Override
    public void stop() {
        currentPosition = node.getTranslateY() - maxValue;
        super.stop();
    }

    class FallAnimation extends Transition {

        public FallAnimation() {
            setCycleDuration(Duration.seconds(1.5));
        }

        @Override
        public void play() {
            System.out.println("falling");
            currentPosition = node.getTranslateY() - 20;
            super.play();
        }

        @Override
        protected void interpolate(double frac) {
            node.setTranslateY(currentPosition + Math.pow(2 * (frac) + 1, Math.E + (frac)) * 20);
        }
    }
}

