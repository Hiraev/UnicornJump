package logic.bonuses;

import javafx.scene.shape.Rectangle;
import logic.Continuable;
import logic.Pausable;

public abstract class Bonus extends Rectangle implements Pausable, Continuable {
    protected static int windowWidth;
    private static int size = 20;

    protected Type type;

    public enum Type {
        Rainbow, Star
    }

    public Bonus(int windowWidth) {
        Bonus.windowWidth = windowWidth;
        setWidth(size);
        setHeight(size);
    }

    public abstract int getScore();

    @Override
    public abstract void pause();

    @Override
    public abstract void continueIt();

    public void vanish() {
        //Убираем бонус подальше
        setTranslateX(3 * windowWidth);
    }

    public Type getType() {
        return type;
    }
}