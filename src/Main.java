import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {
    static int WINDOW_WIDTH = 300;
    static int WINDOW_HEIGHT = 600;
    private Pane appRoot;
    private Pane gameRoot;
    private Pane menuRoot;
    private Game game;

    private Parent setUp() {
        appRoot = new Pane();
        appRoot.setPrefSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        gameRoot = new Pane();
        menuRoot = new Pane();
        game = new Game(gameRoot);
        appRoot.getChildren().add(gameRoot);  // Поменять
        gameRoot.getChildren().addAll(game.getPlatforms());
        gameRoot.getChildren().add(game.getUnicorn());
        return appRoot;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(setUp());
        primaryStage.setScene(scene);
        primaryStage.show();

        game.getUnicorn().jump();
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.LEFT) {
                game.getUnicorn().stopMove();
                game.getUnicorn().move(MoveAnimation.DIRECTION.LEFT);
            } else if (event.getCode() == KeyCode.RIGHT) {
                game.getUnicorn().stopMove();
                game.getUnicorn().move(MoveAnimation.DIRECTION.RIGHT);
            }
        });
        scene.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.LEFT | event.getCode() == KeyCode.RIGHT) {
                game.getUnicorn().stopMove();
            }
        });
    }
}
