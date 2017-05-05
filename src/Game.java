import javafx.animation.AnimationTimer;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class Game {

    private Unicorn unicorn;
    private int score;
    private AnimationTimer timer;
    private int level = 1;
    private ArrayList<Platform> platforms = new ArrayList<>();

    private void setPlatforms() {
        for (int i = -1; i < 70; i++) {
            platforms.add(new Platform(i));
        }
    }

    public Unicorn getUnicorn() {
        return unicorn;
    }

    private void update() {
        if (unicorn.jumpAnimation.falling.get()) {
            for (Platform platform : platforms) {
                if (platform.getBoundsInParent().intersects(unicorn.getBoundsInParent())) {
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
    }

    public ArrayList<Platform> getPlatforms() {
        return platforms;
    }
}
