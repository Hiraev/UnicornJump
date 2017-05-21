package logic.platforms;

import javafx.scene.shape.Rectangle;
import logic.Continuable;
import logic.Pausable;

public abstract class Platform extends Rectangle implements Pausable, Continuable{
    protected Type type;

    public enum Type {
        Basic, Move, Vanish, BasicFade, MoveFade
    }

    protected Platform() {
        setWidth(30);
        setHeight(10);
    }

    public Type getType() {
        return type;
    }

    public abstract void play();

    public abstract void touch();

    @Override
    public abstract void pause();

    @Override
    public abstract void continueIt();
}
