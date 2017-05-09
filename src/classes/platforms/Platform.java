package platforms;

import javafx.animation.ParallelTransition;
import javafx.scene.shape.Rectangle;

public abstract class Platform extends Rectangle {
    protected Type type;
    static ParallelTransition parallelTransition;

    public enum Type {
        Basic, Move, Vanish, BasicFade, MoveFade
    }

    protected Platform() {
        parallelTransition = new ParallelTransition();
        setWidth(30);
        setHeight(10);
    }

    public Type getType() {
        return type;
    }

    public static void play(){
        parallelTransition.play();
    }

    public abstract void touch();
}
