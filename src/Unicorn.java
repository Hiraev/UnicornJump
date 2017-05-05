import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;



public class Unicorn extends Pane{
    int width;
    int height;
    Rectangle rectangle;
    JumpAnimation jumpAnimation;
    MoveAnimation moveAnimation;

    public Unicorn() {
        maxHeight(10);
        maxWidth(10);
        width = 10;
        height = 10;
        rectangle = new Rectangle(width,height, Color.BLUE);
        getChildren().add(rectangle);
        jumpAnimation  = new JumpAnimation(this);
        moveAnimation = new MoveAnimation(rectangle);
    }

    public void jump() {
        jumpAnimation.play();
    }

    public void move(MoveAnimation.DIRECTION direction) {
        moveAnimation.setDirection(direction);
        moveAnimation.play();
    }

    public void stopMove() {
        moveAnimation.stop();
    }
}
