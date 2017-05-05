import javafx.animation.AnimationTimer;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class Game {
    Pane root;
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

    public Game(Pane root) {
        this.root = root;
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

        //ИСПРАВИТЬ ЭТОТ УЧАСТОК КОДА
        unicorn.translateYProperty().addListener((value, oldVal, newVal) -> {
            int offset = newVal.intValue();
            int delta = (oldVal.intValue() - offset);
            if (offset < root.getTranslateY() - offset & !unicorn.jumpAnimation.falling.get()) { // Поменять 150 на актуальное значение
                root.setLayoutY(root.getLayoutY() + delta);
                System.out.println("Delta : " + delta);
            }
        });
    }

    public ArrayList<Platform> getPlatforms() {
        return platforms;
    }
}
