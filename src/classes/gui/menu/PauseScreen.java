package gui.menu;

import javafx.animation.FillTransition;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;


public class PauseScreen extends Pane {
    private static PauseScreen instance;

    private Button resume;
    private Button exit;

    public Button getResume() {
        return resume;
    }

    public Button getExit() {
        return exit;
    }

    private Rectangle rectangle;
    private FillTransition fillTransition;

    private MenuBox menuBox;

    public static PauseScreen getInstance(int windowWidth, int windowHeight) {
        if (instance == null) instance = new PauseScreen(windowWidth, windowHeight);
        return instance;
    }

    private PauseScreen(int windowWidth, int windowHeight) {
        setPrefSize(windowWidth, windowHeight);

        rectangle = new Rectangle(windowWidth, windowHeight, Color.BLACK);
        fillTransition = new FillTransition(Duration.seconds(1), rectangle);
        fillTransition.setFromValue(new Color(0, 0, 0, 0));
        fillTransition.setToValue(new Color(0, 0, 0, 0.6));


        resume = new Button("Продолжить");
        exit = new Button("Выйти");

        menuBox = new MenuBox(resume, exit);
        getChildren().addAll(rectangle, menuBox);
        menuBox.setTranslateX(windowWidth / 2 - menuBox.getPrefWidth() / 2);
        menuBox.setTranslateY(windowHeight / 2 - menuBox.getPrefHeight() / 2);
    }

    public void activateAnimation() {
        fillTransition.play();
    }
}
