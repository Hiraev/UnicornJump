package logic;

import animations.JumpAnimation;
import animations.MoveAnimation;
import javafx.scene.shape.Rectangle;


public class Character extends Rectangle{
    private int width = 40;
    private int height = 40;
    private JumpAnimation jumpAnimation;    //Прыжки
    private MoveAnimation moveAnimation;    //Движение в стороны
    private static Character instance;      //Данный объект, может быть только один

    private Character() {
        setWidth(width);
        setHeight(height);
        jumpAnimation  = new JumpAnimation(this);
        moveAnimation = new MoveAnimation(this);
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

    public void pause() {
        jumpAnimation.pause();
        moveAnimation.pause();
    }

    public void continueAnimation(){
        jumpAnimation.continueAnimation();
    }

    public void stopMove() {
        moveAnimation.stop();
    }
    public boolean isFalling() {
        return jumpAnimation.isFalling();
    }
}
