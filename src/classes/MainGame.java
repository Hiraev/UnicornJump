import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import menu.Menu;
import platforms.Platform;


public class MainGame extends Application {
    static int WINDOW_WIDTH = 400;
    static int WINDOW_HEIGHT = 600;
    private Pane appRoot;
    private Pane gameRoot;
    private Menu menuRoot;
    private Game game;

    private void setUpButtons() {
        menuRoot.getStart().setOnMouseClicked(event -> {
            appRoot.getChildren().remove(menuRoot);
            appRoot.getChildren().add(gameRoot);
            game.play();
        });
    }


    private Parent setUp() {
        appRoot = new Pane();
        appRoot.setPrefSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        gameRoot = new Pane();
        menuRoot = Menu.getInstance(WINDOW_WIDTH, WINDOW_HEIGHT);
        appRoot.getChildren().add(menuRoot);
        game = Game.getInstance(WINDOW_WIDTH, WINDOW_HEIGHT);
        for (Platform platform : game.getLevelMap().getPlatforms()) {
            gameRoot.getChildren().add(platform);
        }
        gameRoot.getChildren().add(game.getCharacter());
        setUpButtons();
        return appRoot;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(setUp());
        scene.getStylesheets().add("file:src2/styles/main.css");
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
                appRoot.getChildren().remove(gameRoot);
                game = null;
                appRoot.getChildren().add(menuRoot);
            }

            int offset = newVal.intValue();
            int delta = (oldVal.intValue() - offset);
            if (Math.abs(offset) > gameRoot.getLayoutY() - 180
                    & !game.getCharacter().isFalling()) {
                gameRoot.setLayoutY(gameRoot.getLayoutY() + delta);
            }
        });

    }


}
