import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

public class BasicPlatform extends Platform {
    private static Image image;
    private static Type type;
    static {
        image = new Image("file:src/images/pool_table.png");
        type = Type.BasicPlatform;
    }

    @Override
    public Type getType() {
        return type;
    }

    public BasicPlatform(int count) {

        setFill(new ImagePattern(image));
        setTranslateX(Math.random() * (Main.WINDOW_WIDTH - width - 70) + 30);
        setTranslateY(Main.WINDOW_HEIGHT / 4 - 150 * count);
    }

    @Override
    public void action() {

    }

    @Override
    public void detection() {

    }
}