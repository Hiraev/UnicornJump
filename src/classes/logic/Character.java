package logic;

import animations.JumpAnimation;
import animations.MoveAnimation;
import javafx.animation.TranslateTransition;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;


public class Character extends Rectangle implements Pausable, Continuable {
    private int width = 40;
    private int height = 40;
    private int countOfFires = 5;
    private List<Fire> fires;
    private JumpAnimation jumpAnimation;    //Прыжки
    private MoveAnimation moveAnimation;    //Движение в стороны
    private static Character instance;      //Данный объект, может быть только один
    private boolean readyToFire = true;

    private Character() {
        setWidth(width);
        setHeight(height);
        jumpAnimation = new JumpAnimation(this);
        moveAnimation = new MoveAnimation(this);
        fires = new ArrayList<>();
    }

    public static Character getInstance() {
        if (instance == null) instance = new Character();
        return instance;
    }

    public void jump() {
        jumpAnimation.play();
    }

    public void move(MoveAnimation.DIRECTION direction) {
        moveAnimation.setDirection(direction);
        moveAnimation.play();
    }

    @Override
    public void pause() {
        jumpAnimation.pause();
        moveAnimation.pause();
        for (Fire fire : fires) {
            fire.pause();
        }
    }

    @Override
    public void continueIt() {
        jumpAnimation.continueAnimation();
        for (Fire fire : fires) {
            fire.continueIt();
        }
    }

    public void stop() {
        jumpAnimation.stop();
        moveAnimation.stop();
        countOfFires = 5;
    }

    public void stopMove() {
        moveAnimation.stop();
    }

    public boolean isFalling() {
        return jumpAnimation.isFalling();
    }

    public List<Fire> getFires() {
        return fires;
    }

    public int getCountOfFires() {
        return countOfFires;
    }

    public void plusCountOfFire(int count) {
        countOfFires += count;
    }

    public boolean isReadyToFire() {
        return readyToFire;
    }

    public void fire() {
        readyToFire = false;
        if (countOfFires != 0) {
            fires.add(new Fire(getTranslateY()));
            countOfFires--;
        }

    }


    public class Fire extends Circle implements Pausable, Continuable {
        private TranslateTransition translateTransition;

        Fire(double currentPosition) {
            this.setRadius(5);
            this.setTranslateX(Character.this.getTranslateX() + width / 2);
            this.translateTransition = new TranslateTransition(Duration.seconds(0.6), this);
            this.translateTransition.setOnFinished(event -> {
                kill();
                readyToFire = true;
            });
            go(currentPosition);
        }

        public void go(double currentPositionY) {
            this.translateTransition.setFromY(currentPositionY);
            this.translateTransition.setToY(currentPositionY - 450);
            this.translateTransition.play();
        }

        public void kill() {
            this.setTranslateY(Character.this.getTranslateY()+400);
            fires.remove(this);
        }

        @Override
        public void pause() {
            this.translateTransition.pause();
        }

        @Override
        public void continueIt() {
            this.translateTransition.play();
        }
    }
}
