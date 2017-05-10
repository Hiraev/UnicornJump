package logic.platforms;

import javafx.animation.ParallelTransition;
import javafx.scene.shape.Rectangle;

public abstract class Platform extends Rectangle {
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
}
