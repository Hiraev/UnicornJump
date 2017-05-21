package animations;

import javafx.animation.Transition;
import javafx.scene.Node;
import javafx.util.Duration;


public class MoveAnimation extends Transition {
    private Node node;                  //Объект, который необхдимо сдвинуть
    private int direction = 1;          //Направление по умолчанию
    private Breaker breaker;            //Небольшая анимация торможения

    public enum DIRECTION {
        LEFT, RIGHT
    }

    public MoveAnimation(Node node) {
        breaker = new Breaker();
        this.node = node;
        setCycleDuration(Duration.INDEFINITE);      //Устанавливаем бесконечный цикл
    }

    @Override
    protected void interpolate(double frac) {
        node.setTranslateX(node.getTranslateX() + (Math.pow((1.6 - frac), 4 - frac)) * direction);
    }

    public void setDirection(DIRECTION direction) {
        if (direction == DIRECTION.LEFT) {
            this.direction = -1;
        } else if (direction == DIRECTION.RIGHT) {
            this.direction = 1;
        } else throw new IllegalArgumentException("Необходимо указать направление движения");
    }

    @Override
    public void stop() {
        breaker.play();
        super.stop();
    }

    public class Breaker extends Transition {

        public Breaker() {
            setCycleDuration(Duration.seconds(0.1));
        }

        @Override
        protected void interpolate(double frac) {
            node.setTranslateX(node.getTranslateX() + Math.pow((frac + 1), 1 / 4) * direction);
        }
    }
}
