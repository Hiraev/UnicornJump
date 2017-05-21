package gui.screens;

import gui.menu.Button;
import gui.menu.MenuBox;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;


public class GameOverScreen extends Pane {
    private static GameOverScreen instance;
    private Text scoreText;
    private Text scoreText2;
    private Text scoreTextScore;
    private Button restart;
    private Button exit;
    private VBox vBox;
    private VBox textBox;

    private MenuBox menuBox;

    public static GameOverScreen getInstance(int width, int height) {
        if (instance == null) {
            instance = new GameOverScreen(width, height);
        }
        return instance;
    }

    private GameOverScreen(int width, int height) {
        setPrefSize(width, height);

        vBox = new VBox();
        textBox = new VBox();
        scoreText = new Text("Набрано");
        scoreText2 = new Text("очков");
        scoreText.setId("scoreText");
        scoreText2.setId("scoreText");
        scoreText.setTextAlignment(TextAlignment.CENTER);
        scoreText2.setTextAlignment(TextAlignment.CENTER);


        scoreTextScore = new Text();
        scoreTextScore.setId("scoreTextScore");
        scoreTextScore.setTextAlignment(TextAlignment.CENTER);

        restart = new Button("Переиграть");
        exit = new Button("Выход");

        menuBox = new MenuBox(restart, exit);

        textBox.getChildren().addAll(scoreText, scoreTextScore, scoreText2);
        textBox.setAlignment(Pos.CENTER);
        textBox.setSpacing(2);

        vBox.getChildren().addAll(textBox, menuBox);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(10);

        getChildren().add(vBox);

        vBox.setTranslateX(width / 2 - menuBox.getPrefWidth() / 2);
        vBox.setTranslateY(height / 2 - menuBox.getPrefHeight());
    }

    public void setScore(int score) {
        int remainder = score % 10;
        if (remainder == 1) {
            scoreText2.setText("очко");
        } else if (remainder < 5 & remainder != 0) {
            scoreText2.setText("очка");
        } else {
            scoreText2.setText("очков");
        }
        scoreTextScore.setText(String.valueOf(score));
    }

    public Button getRestart() {
        return restart;
    }

    public Button getExit() {
        return exit;
    }
}
