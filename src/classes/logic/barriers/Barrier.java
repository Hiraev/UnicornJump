package logic.barriers;

import javafx.scene.shape.Rectangle;

public abstract class Barrier extends Rectangle {
    protected int windowWidth;
    protected int width = 30;
    protected int height  = 30;
    public Barrier(int windowWidth) {
        this.windowWidth = windowWidth;
        setWidth(width);
        setHeight(height);
    }

    public abstract void action();
    public abstract void pause();
    public abstract void continueAnimation();
}
