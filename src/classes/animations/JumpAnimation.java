package animations;

import javafx.animation.Transition;
import javafx.scene.Node;
import javafx.util.Duration;

import java.util.concurrent.atomic.AtomicBoolean;

public class JumpAnimation extends Transition {
    private Node node;                                      //Объект, который должен прыгать
    private double currentPosition;                         //Последняя позиция объекта, после прыжка. Позиция, откуда надо падать
    private double maxValue = Math.pow(3, Math.E + 1) * 4;  //Максимальная высота, на которую прыгает объект
    private FallAnimation fallAnimation;                    //Анимация падения

    public boolean isFalling() {
        return isFalling.get();
    }

    private AtomicBoolean isFalling;                          //Объект падает или нет

    public JumpAnimation(Node node) {
        fallAnimation = new FallAnimation();
        this.node = node;
        isFalling = new AtomicBoolean(false);
        currentPosition = node.getTranslateY() - maxValue;
        setCycleDuration(Duration.seconds(1.2));
        setOnFinished(event -> {
            fallAnimation.play();                           //После завершения прыжка начинаем падать
            isFalling.set(true);                              //и ставим значение "падает" на true
        });
    }


    @Override
    protected void interpolate(double frac) {
        node.setTranslateY(currentPosition + Math.pow(2 * (1 - frac) + 1, Math.E + (1 - frac)) * 4);
    }

    @Override
    public void play() {
        fallAnimation.stop();                               //Останавливаем анимацию падения
        currentPosition = node.getTranslateY() - maxValue;  //Обновляем currentPosition
        isFalling.set(false);                                 //Ставим значение "падает" на false
        super.play();
    }

    @Override
    public void stop() {                                    //Действия при насильной остановке прыжка
        currentPosition = node.getTranslateY() - maxValue;
        super.stop();
    }

    class FallAnimation extends Transition {

        public FallAnimation() {
            setCycleDuration(Duration.seconds(1.5));
        }

        @Override
        public void play() {
            currentPosition = node.getTranslateY() - 20;
            super.play();
        }

        @Override
        protected void interpolate(double frac) {
            node.setTranslateY(currentPosition + Math.pow(2 * (frac) + 1, Math.E + (frac)) * 20);
        }
    }
}

