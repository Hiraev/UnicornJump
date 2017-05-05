import javafx.animation.Transition;
import javafx.scene.Node;
import javafx.util.Duration;


public class MoveAnimation extends Transition {
    private Node node;
    private int direction = 1;
    private double firstPositionX;

    public enum DIRECTION {
        LEFT, RIGHT
    }

    public MoveAnimation(Unicorn node) {
        this.node = node;
        setCycleDuration(Duration.INDEFINITE);
        firstPositionX = node.getLayoutX();
    }

    @Override
    protected void interpolate(double frac) {
        node.setLayoutX(node.getLayoutX() + (Math.pow((1 - frac) + 0.8, Math.E + (1 - frac))) * direction);
    }

    public void setDirection(DIRECTION direction) {
        if (direction == DIRECTION.LEFT) {
            this.direction = -1;
        } else {
            this.direction = 1;
        }
    }

    @Override
    public void stop() {
        Breaker breaker = new Breaker();
        breaker.play();
        super.stop();
    }

    public class Breaker extends Transition {

        public Breaker() {
            setCycleDuration(Duration.seconds(0.1));
        }

        @Override
        protected void interpolate(double frac) {
            node.setTranslateX(node.getTranslateX() + Math.pow((frac + 1), 1 / Math.E) * direction);
        }
    }
}
