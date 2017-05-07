import javafx.animation.AnimationTimer;
import javafx.geometry.Bounds;

import java.util.ArrayList;

public class Game {

    private Unicorn unicorn;
    private int score;
    private AnimationTimer timer;
    private int level = 1;
    private ArrayList<Platform> platforms = new ArrayList<>();

    private void setPlatforms() {
        for (int i = -1; i < 70; i++) {
            platforms.add(new BasicPlatform(i));
        }
    }

    public Unicorn getUnicorn() {
        return unicorn;
    }

    private void update() {
        if (unicorn.jumpAnimation.falling.get()) {
            for (Platform platform : platforms) {
                Bounds ub = unicorn.getBoundsInParent();
                Bounds pb = platform.getBoundsInParent();
                //Страшные условия. Короче, прыгать, когда низ персонажа оказывается
                // внутри платформы, и он сам находится хотя бы на 3/4 внутри платформы
                if ((pb.getMinX() < (ub.getMaxX() - unicorn.getFitWidth() / 4) & pb.getMaxX() > (ub.getMinX()) + unicorn.getFitWidth() / 4) &
                        pb.getMinY() <= ub.getMaxY() &
                        pb.getMinY() >= ub.getMinY()) {
                    unicorn.jump();
                }
            }
        }
    }

    public Game() {

        unicorn = new Unicorn();
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
            }
        };
        setPlatforms();
        timer.start();
        unicorn.setTranslateX(Main.WINDOW_WIDTH / 2);
        unicorn.setTranslateY(Main.WINDOW_HEIGHT / 3 * 2);

        Platform movePlatform = new MovePlatform(200);
        movePlatform.action();
        platforms.add(movePlatform);
    }

    public ArrayList<Platform> getPlatforms() {
        return platforms;
    }
}
