package gui.menu;

import javafx.scene.layout.Pane;


public class GeneralScreen extends Pane {
    private static GeneralScreen instance;

    private Button start;
    private Button settings;
    private Button records;
    private Button exit;

    private MenuBox menuBox;

    public static GeneralScreen getInstance(int windowWidth, int windowHeight) {
        if (instance == null) instance = new GeneralScreen(windowWidth, windowHeight);
        return instance;
    }

    public Button getStartButton() {
        return start;
    }

    public Button getSettingsButton() {
        return settings;
    }

    public Button getRecordsButton() {
        return records;
    }

    public Button getExitButton() {
        return exit;
    }

    private GeneralScreen(int windowWidth, int windowHeight) {

        setPrefSize(windowWidth, windowHeight);


        start = new Button("Начать игру");
        settings = new Button("Настройки");
        records = new Button("Рекорды");
        exit = new Button("Выйти");

        menuBox = new MenuBox(start, settings, records, exit);
        getChildren().add(menuBox);
        menuBox.setTranslateX(windowWidth / 2 - menuBox.getPrefWidth() / 2);
        menuBox.setTranslateY(windowHeight / 2 - menuBox.getPrefHeight() / 2);
    }
}