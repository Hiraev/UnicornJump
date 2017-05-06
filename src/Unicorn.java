import javafx.scene.image.Image;
import javafx.scene.image.ImageView;



public class Unicorn extends ImageView{
    int width = 30;
    int height = 20;
    JumpAnimation jumpAnimation;
    MoveAnimation moveAnimation;


    public Unicorn() {
        setImage(new Image("Unicorn.png"));
        setFitWidth(40);
        setFitHeight(25);

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
