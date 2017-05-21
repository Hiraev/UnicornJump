package gui.screens;

import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.layout.Pane;


public class StatusBar extends Pane {
    private static StatusBar instance;

    private HBox box;
    private VBox scores;
    private VBox level;
    private VBox fires;

    private Text infoScoreText;
    private Text scoreText;
    private Text infoLevelText;
    private Text levelText;
    private Text infoFiresText;
    private Text fireText;

    public static StatusBar getInstance(int windowWidth, int windowHeight) {
        if (instance == null) {
            instance = new StatusBar(windowWidth, windowHeight);
        }
        return instance;
    }

    private StatusBar(int windowWidth, int windowHeight) {
        box = new HBox();
        box.setId("statusBarBox");

        scores = new VBox();

        infoScoreText = new Text("Очков");
        scoreText = new Text();
        scores.getChildren().addAll(scoreText, infoScoreText);
        scores.setAlignment(Pos.CENTER);

        level = new VBox();

        infoLevelText = new Text("Уровень");
        levelText = new Text();
        level.getChildren().addAll(levelText, infoLevelText);
        level.setAlignment(Pos.CENTER);

        fires = new VBox();

        infoFiresText = new Text("Пулек");
        fireText = new Text();
        fires.getChildren().addAll(fireText, infoFiresText);
        fires.setAlignment(Pos.CENTER);

        infoScoreText.setId("statusInfo");
        infoLevelText.setId("statusInfo");
        infoFiresText.setId("statusInfo");
        scoreText.setId("statusVariable");
        levelText.setId("statusVariable");
        fireText.setId("statusVariable");

        box.getChildren().addAll(scores, level, fires);
        box.setAlignment(Pos.CENTER);
        box.setPrefWidth(windowWidth);
        box.setPrefHeight(50);
        box.setTranslateY(windowHeight - box.getPrefHeight());
        box.setSpacing(70);

        getChildren().addAll(box);
    }

    public void setScores(int score) {
        scoreText.setText(String.valueOf(score));
    }

    public void setLevel(int level) {
        levelText.setText(String.valueOf(level));
    }

    public void setFires(int fires) {
        fireText.setText(String.valueOf(fires));
    }
}
