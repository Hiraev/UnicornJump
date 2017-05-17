package gui;

import gui.menu.GameOverScreen;
import gui.menu.PauseScreen;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import gui.menu.GeneralScreen;
import logic.Game;
import animations.MoveAnimation;
import logic.barriers.Barrier;
import logic.bonuses.Bonus;
import logic.platforms.Platform;


public class MainGame extends Application {
    static int WINDOW_WIDTH = 400;
    static int WINDOW_HEIGHT = 600;
    private Pane appRoot;
    private Pane gameScreen;
    private GeneralScreen generalScreen;
    private PauseScreen pauseScreen;
    private GameOverScreen gameOverScreen;
    private Game game;
    private int lastPlatformY;
    private boolean pause;

    public static void main(String[] args) {
        launch(args);
    }

    private void setUpButtons() {
        generalScreen.getStartButton().setOnMouseClicked(event -> {
            game.play();
            generalScreen.setVisible(false);
            gameScreen.setVisible(true);
        });

        generalScreen.getExitButton().setOnMouseClicked(event -> {
            System.exit(0);
        });

        pauseScreen.getResume().setOnMouseClicked(event -> {
            game.continueGame();
            pauseScreen.setVisible(false);
            pause = !pause;
        });

        pauseScreen.getExit().setOnMouseClicked(event -> {
            game.over();
            pause = !pause;
            setUpGame();
            generalScreen.setVisible(true);
            pauseScreen.setVisible(false);
            gameScreen.setVisible(false);
        });

        gameOverScreen.getRestart().setOnMouseClicked(event -> {
            game.play();
            gameOverScreen.setVisible(false);
            gameScreen.setVisible(true);
        });

        gameOverScreen.getExit().setOnMouseClicked(event -> {
            gameOverScreen.setVisible(false);
            generalScreen.setVisible(true);
        });
    }



    private Parent setUp() {
        appRoot = new Pane();
        appRoot.setPrefSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        gameScreen = new Pane();
        generalScreen = GeneralScreen.getInstance(WINDOW_WIDTH, WINDOW_HEIGHT);
        pauseScreen = PauseScreen.getInstance(WINDOW_WIDTH, WINDOW_HEIGHT);
        gameOverScreen = GameOverScreen.getInstance(WINDOW_WIDTH, WINDOW_HEIGHT);

        appRoot.getChildren().add(gameScreen);
        appRoot.getChildren().add(generalScreen);
        appRoot.getChildren().add(pauseScreen);
        appRoot.getChildren().add(gameOverScreen);

        generalScreen.setVisible(true);
        pauseScreen.setVisible(false);
        gameScreen.setVisible(false);
        gameOverScreen.setVisible(false);

        game = Game.getInstance(WINDOW_WIDTH, WINDOW_HEIGHT);

        setUpGame();
        setUpButtons();
        return appRoot;
    }


    /**
     * Отрисовка карты
     * НЕТ ОТРИСОВКИ ПРЕГРАД И БОНУСОВ (ПРЕГРАД И БОНУСОВ САМИХ ПОКА ЕЩЕ НЕТ)
     */
    private void setUpMap() {

        gameScreen.getChildren().clear();
        for (Platform platform : game.getLevelMap().getPlatforms()) {
            gameScreen.getChildren().add(platform);
        }
        for (Bonus bonus : game.getLevelMap().getBonuses()) {
            gameScreen.getChildren().add(bonus);
        }

        for (Barrier barrier : game.getLevelMap().getBarriers()) {
            gameScreen.getChildren().add(barrier);
        }
        gameScreen.getChildren().add(game.getCharacter());
        //Берем координаты последней платформы
        //Далее будем проверять не меняется ли она
        //Если изменится, то обновим карту
        lastPlatformY = game.getLastPlatformY();
    }

    /**
     * КОНЕЦ ОТРИСОВКИ КАРТЫ
     */


    private void setUpGame() {
        setUpMap();
        gameScreen.setLayoutY(WINDOW_HEIGHT / 2);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(setUp());
        //Пока уберем стили
        scene.getStylesheets().add("file:src/styles/main.css");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);


        primaryStage.show();


        scene.setOnKeyPressed(event -> {
            if (!game.isGameOver()) {
                if (!pause) {
                    if (event.getCode() == KeyCode.LEFT) {
                        game.getCharacter().stopMove();
                        game.getCharacter().move(MoveAnimation.DIRECTION.LEFT);
                    } else if (event.getCode() == KeyCode.RIGHT) {
                        game.getCharacter().stopMove();
                        game.getCharacter().move(MoveAnimation.DIRECTION.RIGHT);
                    }
                }

                if (event.getCode() == KeyCode.ESCAPE) {
                    if (!pause) {
                        game.pause();

                        pauseScreen.setVisible(true);
                        pauseScreen.activateAnimation();
                    } else {
                        game.continueGame();

                        pauseScreen.setVisible(false);
                    }
                    pause = !pause;
                }
            }
        });
        scene.setOnKeyReleased(event -> {
            if (!game.isGameOver() & !pause) {
                if (event.getCode() == KeyCode.LEFT | event.getCode() == KeyCode.RIGHT) {
                    game.getCharacter().stopMove();
                }
            }
        });

        game.getCharacter().translateYProperty().addListener((value, oldVal, newVal) -> {

            if (game.isGameOver()) {
                gameOverScreen.setScore(game.getScore());
                gameOverScreen.setVisible(true);
                gameScreen.setVisible(false);

                setUpGame();
            } else if (!pause) {
                int offset = newVal.intValue();
                int delta = (oldVal.intValue() - offset);
                if (Math.abs(offset) > gameScreen.getLayoutY() - WINDOW_HEIGHT / 3
                        & !game.getCharacter().isFalling()) {
                    gameScreen.setLayoutY(gameScreen.getLayoutY() + delta);
                }
            }

            /**
             *  ПЕРЕРИСОВКА КАРТЫ
             *
             * ПОПРОБОВАТЬ НАЙТИ ИНОЙ СПОСОБ ОБНОВЛЕНИЯ КАРТЫ
             *
             */
            if (lastPlatformY != game.getLastPlatformY()) {
                setUpMap();
            }
        });
    }
}
