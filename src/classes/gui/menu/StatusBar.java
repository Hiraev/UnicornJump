package gui.menu;

import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.layout.Pane;


public class StatusBar extends Pane {
    private int windowWidth;

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
    private Rectangle bg;

    public StatusBar(int windowWidth, int windowHeight) {
        bg = new Rectangle(windowWidth + 10, 55, Color.WHITESMOKE);
        bg.setStroke(Color.DARKGRAY);
        bg.setStrokeWidth(2);
        bg.setOpacity(0.85);

        box = new HBox();

        scores = new VBox();
        scores.setPrefWidth(windowWidth / 3);
        infoScoreText = new Text("Очков");
        scoreText = new Text();
        scores.getChildren().addAll(scoreText, infoScoreText);
        scores.setAlignment(Pos.CENTER);

        level = new VBox();
        level.setPrefWidth(windowWidth / 3);
        infoLevelText = new Text("Уровень");
        levelText = new Text();
        level.getChildren().addAll(levelText, infoLevelText);
        level.setAlignment(Pos.CENTER);

        fires = new VBox();
        fires.setPrefWidth(windowWidth / 3);
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

        this.windowWidth = windowWidth;
        box.getChildren().addAll(scores, level, fires);
        box.setAlignment(Pos.CENTER);

        box.setPrefHeight(50);
        box.setTranslateY(windowHeight - box.getPrefHeight());
        bg.setTranslateY(box.getTranslateY() );
        bg.setTranslateX(-5);

        getChildren().addAll(bg, box);
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
