import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;


public class Unicorn extends ImageView{
    private int width = 40;
    private int height = 20;
    JumpAnimation jumpAnimation;
    private MoveAnimation moveAnimation;
    private Rectangle2D rectanglePort;


    public Unicorn() {
        setImage(new Image("file:src/images/Unicorn.png"));
        rectanglePort = new Rectangle2D(0,0,width,height);
        //setViewport(rectanglePort);
        setFitWidth(50);
        setFitHeight(30);
        jumpAnimation  = new JumpAnimation(this);
        moveAnimation = new MoveAnimation(this);
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
