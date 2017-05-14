package gui;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import gui.menu.Menu;
import logic.Game;
import animations.MoveAnimation;
import logic.platforms.Platform;


public class MainGame extends Application {
    static int WINDOW_WIDTH = 400;
    static int WINDOW_HEIGHT = 600;
    private Pane appRoot;
    private Pane gameRoot;
    private Menu menuRoot;
    private Game game;
    private int lastPlatformY;

    public static void main(String[] args) {
        launch(args);
    }

    private void setUpButtons() {
        menuRoot.getStartButton().setOnMouseClicked(event -> {
            game.play();
            switcher();
            System.out.println("Clicked");
        });
    }

    private void switcher() {
        menuRoot.setVisible(false);
        gameRoot.setVisible(true);
    }

    private Parent setUp() {
        appRoot = new Pane();
        appRoot.setPrefSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        gameRoot = new Pane();
        menuRoot = Menu.getInstance(WINDOW_WIDTH, WINDOW_HEIGHT);
        appRoot.getChildren().add(gameRoot);
        appRoot.getChildren().add(menuRoot);

        menuRoot.setVisible(true);
        gameRoot.setVisible(false);

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

        gameRoot.getChildren().clear();
        for (Platform platform : game.getLevelMap().getPlatforms()) {
            gameRoot.getChildren().add(platform);
        }
        gameRoot.getChildren().add(game.getCharacter());
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

        gameRoot.setLayoutX(0);
        gameRoot.setLayoutY(0);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(setUp());
        //Пока уберем стили
        //scene.getStylesheets().add("file:src/styles/main.css");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);

        gameRoot.setLayoutY(300);

        primaryStage.show();


        scene.setOnKeyPressed(event -> {
            if (!game.isGameOver()) {
                if (event.getCode() == KeyCode.LEFT) {
                    game.getCharacter().stopMove();
                    game.getCharacter().move(MoveAnimation.DIRECTION.LEFT);
                } else if (event.getCode() == KeyCode.RIGHT) {
                    game.getCharacter().stopMove();
                    game.getCharacter().move(MoveAnimation.DIRECTION.RIGHT);
                }

                if (event.getCode() == KeyCode.ESCAPE) {
                    setUpMap();
                }
            }


        });
        scene.setOnKeyReleased(event -> {
            if (!game.isGameOver()) {
                if (event.getCode() == KeyCode.LEFT | event.getCode() == KeyCode.RIGHT) {
                    game.getCharacter().stopMove();
                }
            }
        });

        game.getCharacter().translateYProperty().addListener((value, oldVal, newVal) -> {

            if (game.isGameOver()) {

                gameRoot.getChildren().clear();
                game.restart();

                menuRoot.setVisible(true);
                gameRoot.setVisible(false);


                setUpGame();
            } else {
                int offset = newVal.intValue();
                int delta = (oldVal.intValue() - offset);
                if (Math.abs(offset) > gameRoot.getLayoutY() - WINDOW_HEIGHT / 3
                        & !game.getCharacter().isFalling()) {
                    gameRoot.setLayoutY(gameRoot.getLayoutY() + delta);



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
