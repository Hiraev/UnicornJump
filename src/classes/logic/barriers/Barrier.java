package logic.barriers;

import javafx.scene.shape.Rectangle;
import logic.Continuable;
import logic.Pausable;

public abstract class Barrier extends Rectangle implements Pausable, Continuable{
    protected int windowWidth;
    protected int width = 30;
    protected int height  = 30;
    public Barrier(int windowWidth) {
        this.windowWidth = windowWidth;
        setWidth(width);
        setHeight(height);
    }

    public abstract void action();

    @Override
    public abstract void pause();

    @Override
    public abstract void continueIt();
}
