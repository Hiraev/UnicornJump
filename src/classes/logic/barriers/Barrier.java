package logic.barriers;

import javafx.scene.shape.Rectangle;
import logic.Continuable;
import logic.Pausable;

public abstract class Barrier extends Rectangle implements Pausable, Continuable {
    protected int windowWidth;
    protected int width = 30;
    protected int height = 30;
    protected Type type;

    public enum Type {
        Static, Move
    }

    public Barrier(int windowWidth) {
        this.windowWidth = windowWidth;
        setWidth(width);
        setHeight(height);
    }

    public Type getType() {
        return type;
    }

    public abstract void action();

    @Override
    public abstract void pause();

    @Override
    public abstract void continueIt();

    public void kill() {
        setTranslateX(3 * windowWidth);
        pause();
    }
}
