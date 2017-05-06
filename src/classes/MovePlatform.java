import javafx.animation.Animation;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;



public class MovePlatform extends Platform {
    private TranslateTransition translateTransition;

    public MovePlatform(int count) {
        Image image = new Image("file:src/images/bg_metal.png");
        setFill(new ImagePattern(image));
        setId("movePlatform");
        setStrokeWidth(0.5);
        setStroke(Color.BLACK);
        translateTransition = new TranslateTransition(Duration.seconds(1.3), this);
        translateTransition.setAutoReverse(true);
        translateTransition.setCycleCount(Animation.INDEFINITE);
        translateTransition.setByX(40);
        translateTransition.setToX(300 - super.width + 20);

        //setFill(Color.BLUE);
        setX(40);
        setTranslateY(count);
    }

    @Override
    public void action() {
        translateTransition.play();
    }

    @Override
    public void detection() {
    }
}
