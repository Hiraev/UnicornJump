package gui.menu;

import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.Pane;


public class Menu extends Pane {
    private static Menu instance;
    private double windowWidth;
    private double windowHeight;
    private Button start;
    private Button settings;
    private Button records;
    private Button exit;

    private MenuBox menuBox;

    public static Menu getInstance(int windowWidth, int windowHeight) {
        if (instance == null) instance = new Menu(windowWidth, windowHeight);
        return instance;
    }

    public Button getStart() {
        return start;
    }

    public Button getSettings() {
        return settings;
    }

    public Button getRecords() {
        return records;
    }

    public Button getExit() {
        return exit;
    }

    private Menu(int windowWidth, int windowHeight) {

        setPrefSize(windowWidth, windowHeight);
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;

        start = new Button("Начать игру", windowWidth);
        settings = new Button("Настройки", windowWidth);
        records = new Button("Рекорды", windowWidth);
        exit = new Button("Выйти", windowWidth);

        menuBox = new MenuBox(start, settings, records, exit);
        getChildren().add(menuBox);
        menuBox.setTranslateX(windowWidth / 2 - menuBox.getPrefWidth()/2);
        menuBox.setTranslateY(windowHeight / 2 - menuBox.getPrefHeight()/2);
        System.out.println(menuBox.getPrefHeight());
        System.out.println(menuBox.getPrefWidth());
        System.out.println();
    }
}