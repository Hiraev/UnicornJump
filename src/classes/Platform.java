import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public abstract class Platform extends Rectangle {
    protected DropShadow effect;
    protected int width = 30;
    protected int height = 10;

    public Platform() {

        effect = new DropShadow(5, 1, 1, new Color(0, 0, 0, 0.4));
        this.setEffect(effect);
        setArcHeight(5);
        setArcWidth(5);
        setWidth(30);
        setHeight(10);
    }

    public abstract void action();

    public abstract void detection();
}
