package gui.menu;

import javafx.scene.layout.Pane;


public class Menu extends Pane {
    private static Menu instance;

    private Button start;
    private Button settings;
    private Button records;
    private Button exit;

    private MenuBox menuBox;

    public static Menu getInstance(int windowWidth, int windowHeight) {
        if (instance == null) instance = new Menu(windowWidth, windowHeight);
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

    private Menu(int windowWidth, int windowHeight) {

        setPrefSize(windowWidth, windowHeight);


        start = new Button("Начать игру", windowWidth);
        settings = new Button("Настройки", windowWidth);
        records = new Button("Рекорды", windowWidth);
        exit = new Button("Выйти", windowWidth);

        menuBox = new MenuBox(start, settings, records, exit);
        getChildren().add(menuBox);
        menuBox.setTranslateX(windowWidth / 2 - menuBox.getPrefWidth()/2);
        menuBox.setTranslateY(windowHeight / 2 - menuBox.getPrefHeight()/2);
    }
}