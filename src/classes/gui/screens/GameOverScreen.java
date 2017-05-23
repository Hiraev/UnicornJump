package gui.screens;

import gui.menu.Button;
import gui.menu.MenuBox;
import javafx.animation.TranslateTransition;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;


public class GameOverScreen extends Pane {
    private static GameOverScreen instance;
    private Text scoreText;
    private Text scoreText2;
    private Text scoreTextScore;
    private Text nameText;
    private TextField nameField;
    private Button save;
    private Button restart;
    private Button exit;
    private VBox vBox;
    private VBox textBox;
    private TranslateTransition translateTransition;

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

        save = new Button("Сохранить");
        nameField = new TextField();
        nameField.setMaxWidth(width/4);
        nameText = new Text("Введите имя");
        nameText.setId("nameText");

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

        menuBox = new MenuBox(save, restart, exit);

        textBox.getChildren().addAll(scoreText, scoreTextScore, scoreText2, nameText, nameField);
        textBox.setAlignment(Pos.CENTER);
        textBox.setSpacing(2);

        vBox.getChildren().addAll(textBox, menuBox);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(10);

        getChildren().add(vBox);

        translateTransition = new TranslateTransition(Duration.seconds(0.5), textBox);
        translateTransition.setToY(textBox.getTranslateY() + 100);

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

        textBox.setTranslateY(textBox.getLayoutY());
        nameText.setVisible(true);
        nameField.setVisible(true);
        save.setVisible(true);
    }

    public Button getRestart() {
        return restart;
    }

    public Button getExit() {
        return exit;
    }

    public Button getSave() {
        return save;
    }

    public String getName() {
        translateTransition.play();
        nameField.setVisible(false);
        nameText.setVisible(false);
        save.setVisible(false);
        return nameField.getText().trim();
    }
}
