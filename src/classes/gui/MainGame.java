package gui;

import gui.menu.*;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
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
    private RecordsScreen recordsScreen;
    private StatusBar statusBar;
    private Game game;
    private int lastPlatformY;
    private boolean pause;

    public static void main(String[] args) {
        launch(args);
    }


    /**
     * ЗАДАЕМ ДЕЙСТВИЯ ДЛЯ ВСЕХ КНОПОК
     */

    private void setUpButtons() {
        generalScreen.getStartButton().setOnMouseClicked(event -> {
            game.play();
            generalScreen.setVisible(false);
            gameScreen.setVisible(true);
            statusBar.setVisible(true);
        });

        generalScreen.getExitButton().setOnMouseClicked(event -> {
            System.exit(0);
        });

        generalScreen.getRecordsButton().setOnMouseClicked(event -> {
            recordsScreen.setRecordsList(game.getRecords());
            generalScreen.setVisible(false);
            recordsScreen.setVisible(true);
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
            statusBar.setVisible(false);
        });

        gameOverScreen.getRestart().setOnMouseClicked(event -> {
            game.play();
            gameOverScreen.setVisible(false);
            gameScreen.setVisible(true);
            statusBar.setVisible(true);
        });

        gameOverScreen.getExit().setOnMouseClicked(event -> {
            gameOverScreen.setVisible(false);
            generalScreen.setVisible(true);
        });

        recordsScreen.getBackButton().setOnMouseClicked(event -> {
            recordsScreen.setVisible(false);
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
        recordsScreen = RecordsScreen.getInstance(WINDOW_WIDTH, WINDOW_HEIGHT);
        statusBar = StatusBar.getInstance(WINDOW_WIDTH, WINDOW_HEIGHT);

        appRoot.getChildren().addAll(gameScreen, statusBar, generalScreen, pauseScreen, gameOverScreen, recordsScreen);

        generalScreen.setVisible(true);
        statusBar.setVisible(false);
        pauseScreen.setVisible(false);
        gameScreen.setVisible(false);
        gameOverScreen.setVisible(false);
        recordsScreen.setVisible(false);

        game = Game.getInstance(WINDOW_WIDTH, WINDOW_HEIGHT);
        setUpGame();
        setUpButtons();
        return appRoot;
    }


    /**
     * Отрисовка карты
     */
    private void setUpMap() {
        statusBar.setLevel(game.getLevel());
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
        gameScreen.setLayoutY(WINDOW_HEIGHT / 3);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(setUp());
        scene.getStylesheets().add("main.css");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);

        primaryStage.show();


        scene.setOnKeyPressed(event -> {
            if (!game.isGameOver()) {

                /**
                 * ДВИЖЕНИЯ ВЛЕВО И ВПРАВО
                 */
                if (!pause) {
                    if (event.getCode() == KeyCode.LEFT) {
                        game.getCharacter().stopMove();
                        game.getCharacter().move(MoveAnimation.DIRECTION.LEFT);
                    } else if (event.getCode() == KeyCode.RIGHT) {
                        game.getCharacter().stopMove();
                        game.getCharacter().move(MoveAnimation.DIRECTION.RIGHT);
                    }
                }


                /**
                 * ПАУЗА ПРИ НАЖАТИИ КНОПКИ ESC
                 */
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

                /**
                 * ВЫСТРЕЛЫ ПРИ НАЖАТИИ КНОПКИ SPACE
                 */
                if (game.getCharacter().isReadyToFire() & game.getCharacter().isCanFire() & !pause &event.getCode() == KeyCode.SPACE) {
                    gameScreen.getChildren().add(game.getCharacter().fire());
                }
            }
        });

        /**
         * ПРЕКРАЩАЕМ ДВИЖЕНИЕ ВЛЕВО ИЛИ ВПРАВО, КОГДА КНОПКА ОТПУЩЕНА
         */
        scene.setOnKeyReleased(event -> {
            if (!game.isGameOver() & !pause) {
                if (event.getCode() == KeyCode.LEFT | event.getCode() == KeyCode.RIGHT) {
                    game.getCharacter().stopMove();
                }
            }
        });

        /**
         * СЛЕДИМ ЗА ПОЛОЖЕНИЕМ ПЕРСОНАЖА ПО Y И ВЫПОЛНЯЕМ СЛЕДУЮЩИЕ ДЕЙСТВИЯ:
         * - проверяем закончина ли игра, если да, то вызываем экран завершения игры, если нет:
         *  - обновляем статус очков и пулек
         *  - перемещаем экран, если персонаж слищком близко к верхушке экрана
         */
        game.getCharacter().translateYProperty().addListener((value, oldVal, newVal) -> {

            if (game.isGameOver()) {
                gameOverScreen.setScore(game.getScore());
                gameOverScreen.setVisible(true);
                gameScreen.setVisible(false);
                statusBar.setVisible(false);

                setUpGame();
            } else if (!pause) {
                int offset = newVal.intValue();
                int delta = (oldVal.intValue() - offset);
                if (Math.abs(offset) > gameScreen.getLayoutY() - WINDOW_HEIGHT / 2
                        & !game.getCharacter().isFalling()) {
                    gameScreen.setLayoutY(gameScreen.getLayoutY() + delta);
                }
            }

            statusBar.setScores(game.getScore());
            statusBar.setFires(game.getCharacter().getCountOfFires());

            /**
             * ЕСЛИ КАРТА В ИГРЕ ОБНОВЛЕНА, ТО МЫ УЗНАЕМ ОБ ЭТОМ
             * СРАВНИВАЯ ПОЗИЦИЮ ПОСЛЕДНЕЙ ПЛАТФОМЫ ВЗЯТУЮ ИЗ ИГРЫ И
             * СОХРАНЕННУЮ ЗДЕСЬ
             */
            if (lastPlatformY != game.getLastPlatformY()) {
                setUpMap();
            }
        });
    }
}
