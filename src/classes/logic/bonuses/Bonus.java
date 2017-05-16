package logic.bonuses;

import javafx.scene.shape.Rectangle;

public abstract class Bonus extends Rectangle {
    protected static int windowWidth;
    private static int size = 20;

    public Bonus(int windowWidth) {
        Bonus.windowWidth = windowWidth;
        setWidth(size);
        setHeight(size);
    }

    public abstract int getScore();
    public abstract void pause();
    public abstract void continueAnimation();

    public void vanish(){
        //Убираем бонус подальше
        setTranslateX(3 * windowWidth);
    }


}