import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.paint.Color;


public class Main extends Application {
    static int WINDOW_WIDTH = 400;
    static int WINDOW_HEIGHT = 600;
    private Pane appRoot;
    private Pane gameRoot;
    private Pane menuRoot;
    private Game game;
    Rectangle rectangle;

    private Parent setUp() {
        appRoot = new Pane();
        appRoot.setPrefSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        gameRoot = new Pane();
        appRoot.setId("appRoot");
        gameRoot.setId("gameRoot");
        menuRoot = new Pane();
        rectangle = new Rectangle(300, 5, Color.GRAY);
        rectangle.setTranslateX(50);
        rectangle.setTranslateY(202);
        rectangle.setArcHeight(3);
        rectangle.setArcWidth(3);
        InnerShadow innerShadow = new InnerShadow(3, 0, 0, Color.BLACK);
        rectangle.setEffect(innerShadow);
        gameRoot.getChildren().add(rectangle);

        game = new Game();
        appRoot.getChildren().add(gameRoot);  // Поменять
        Image image = new Image("file:src/images/pool_table.png");
        for (Platform platform : game.getPlatforms()) {

            gameRoot.getChildren().add(platform);
        }

        gameRoot.getChildren().add(game.getUnicorn());
        return appRoot;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(setUp());
        scene.getStylesheets().add("file:src/style.css");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
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

        game.getUnicorn().translateYProperty().addListener((value, oldVal, newVal) -> {
            int offset = newVal.intValue();
            int delta = (oldVal.intValue() - offset);
            if (offset - 100 < gameRoot.getTranslateY() & !game.getUnicorn().jumpAnimation.falling.get()) { // Поменять 150 на актуальное значение
                gameRoot.setLayoutY(gameRoot.getLayoutY() + 3);
            }
        });

    }
}
