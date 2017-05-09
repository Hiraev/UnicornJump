import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;


public class Main extends Application {
    static int WINDOW_WIDTH = 400;
    static int WINDOW_HEIGHT = 600;
    private Pane appRoot;
    private Pane gameRoot;
    private Pane menuRoot;
    private Game game;
    Rectangle rectangle;
    private GameOverPane gameOverPane;


    /**
     * FOR DEVELOPERS
     */
    private GridPane gridPane;
    Label label4;
    Label label5;
    Label label6;
    Label label7;
    Label label8;
    Label label9;

    /**
     * END
     */

    private Parent setUp() {
        appRoot = new Pane();
        gameOverPane = new GameOverPane();
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


        /**
         * FOR DEVELOPERS
         */
        gridPane = new GridPane();

        gridPane.setPadding(new Insets(3, 3, 3, 3));
        gridPane.setHgap(5);
        gridPane.setVgap(5);
        Label label1 = new Label("gameRoot laX - laY : ");
        Label label2 = new Label("unicorn trX - trY : ");
        Label label3 = new Label("unicornYPr old - new : ");
        label4 = new Label();
        label5 = new Label();
        label6 = new Label();
        label7 = new Label();
        label8 = new Label();
        label9 = new Label();
        gridPane.setConstraints(label1, 0, 0);
        gridPane.setConstraints(label2, 0, 1);
        gridPane.setConstraints(label3, 0, 2);

        gridPane.setConstraints(label4, 1, 0);
        gridPane.setConstraints(label5, 2, 0);

        gridPane.setConstraints(label6, 1, 1);
        gridPane.setConstraints(label7, 2, 1);

        gridPane.setConstraints(label8, 1, 2);
        gridPane.setConstraints(label9, 2, 2);

        gridPane.getChildren().addAll(label1, label2, label3, label4, label5, label6, label7, label8, label9);
        appRoot.getChildren().add(gridPane);
        /**
         * END
         */


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
            if (!game.isGameOver()) {
                /**
                 * FOR DEVELOPERS
                 */

                label6.setText(String.valueOf((int) game.getUnicorn().getTranslateX()));

                /**
                 * END
                 */
                if (event.getCode() == KeyCode.LEFT) {
                    game.getUnicorn().stopMove();
                    game.getUnicorn().move(MoveAnimation.DIRECTION.LEFT);
                } else if (event.getCode() == KeyCode.RIGHT) {
                    game.getUnicorn().stopMove();
                    game.getUnicorn().move(MoveAnimation.DIRECTION.RIGHT);
                }
            }


        });
        scene.setOnKeyReleased(event -> {
            if (!game.isGameOver()) {
                if (event.getCode() == KeyCode.LEFT | event.getCode() == KeyCode.RIGHT) {
                    game.getUnicorn().stopMove();
                }
            }
        });

        game.getUnicorn().translateYProperty().addListener((value, oldVal, newVal) -> {

            if (game.isGameOver()) {
                appRoot.getChildren().remove(gameRoot);
                game = null;
                appRoot.getChildren().add(gameOverPane);
            }

            /**
             * FOR DEVELOPERS
             */
            label4.setText(String.valueOf((int) game.minPosition));
            //label5.setText("");
            label6.setText(String.valueOf((int) game.getUnicorn().getTranslateX()));
            label7.setText(String.valueOf((int) game.getUnicorn().getTranslateY()));
            label8.setText(String.valueOf(oldVal.intValue()));
            label9.setText(String.valueOf(newVal.intValue()));

            /**
             * END
             */

            int offset = newVal.intValue();
            int delta = (oldVal.intValue() - offset);
            if (Math.abs(offset) > gameRoot.getLayoutY() - 150
                    & !game.getUnicorn().jumpAnimation.falling.get()) {
                gameRoot.setLayoutY(gameRoot.getLayoutY() + delta);
            }
        });

    }
}
