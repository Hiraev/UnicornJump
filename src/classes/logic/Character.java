package logic;

import animations.JumpAnimation;
import animations.MoveAnimation;
import javafx.animation.TranslateTransition;
import javafx.scene.paint.Color;
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
    private boolean canFire = true;

    private Character() {
        setId("character");
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
        canFire = true;
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
        canFire = true;
    }

    public boolean isCanFire() {
        return canFire;
    }

    public boolean isReadyToFire() {
        return readyToFire;
    }

    public void clearFires() {
        fires.clear();
    }

    public Fire fire() {
        Fire fire = new Fire(getTranslateY());
        readyToFire = false;
        fires.add(fire);
        countOfFires--;
        if (countOfFires == 0) {
            canFire = false;
        }
        return fire;
    }


    /**
     * ВНУТРЕННИЙ КЛАСС, ОТВЕЧАЮЩИЙ ЗА ПУЛЬКИ
     * ПРИ СОЗДАНИИ СРАЗУ ЛЕТИТ ВВЕРХ
     * ПРИ ВРЕЗАНИИ В ПРЕГРАДУ ИЛИ ПРИ ЗАВЕРШЕНИИ СВОЕГО ПОЛЕТА,
     * УНИЧНОЖАЕТСЯ, ТО ЕСТЬ ПРОСТО ПЕРЕМЕЩАЕТСЯ ВНИЗ КАРТЫ
     */

    public class Fire extends Circle implements Pausable, Continuable {
        private TranslateTransition translateTransition;
        private boolean killed;

        Fire(double currentPosition) {
            double v = Math.random();
            setFill(v > 0.8 ? Color.RED : v > 0.6 ? Color.GREEN : v > 0.4 ? Color.BLUE : v > 0.2 ? Color.VIOLET : Color.MISTYROSE);
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
            this.translateTransition.setToY(currentPositionY - 650);
            this.translateTransition.play();
        }

        public void kill() {
            this.killed = true;
            this.setTranslateY(Character.this.getTranslateY() + 400);
        }

        public boolean isKilled() {
            return killed;
        }

        @Override
        public void pause() {
            this.translateTransition.pause();
        }

        @Override
        public void continueIt() {
            if (!isKilled()) this.translateTransition.play();
        }
    }
}
