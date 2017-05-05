import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class Platform extends Pane {
    private int width = 30;
    private int height = 10;
    private Rectangle rectangle;

    public Platform(int count) {
        setMaxHeight(height);
        setMaxWidth(width);
        rectangle = new Rectangle(width, height, Color.GREEN);
        getChildren().add(rectangle);
        setTranslateX(Math.random() * Main.WINDOW_WIDTH);
        setTranslateY(Math.random() * Main.WINDOW_HEIGHT/2 + 50*count);
    }
}