import javafx.scene.shape.Rectangle;


public class Platform extends Rectangle {
    private int width = 30;
    private int height = 10;

    public Platform(int count) {
        setArcHeight(5);
        setArcWidth(5);
        setWidth(30);
        setHeight(10);
        setTranslateX(Math.random() * Main.WINDOW_WIDTH - width);
        setTranslateY(Math.random() * Main.WINDOW_HEIGHT/4 - 150*count);
    }
}