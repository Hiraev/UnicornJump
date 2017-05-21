package gui.screens;
import gui.menu.Button;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import logic.RecordsLoaderAndSaver;

import java.util.Collections;
import java.util.List;


public class RecordsScreen extends Pane{
    private static RecordsScreen instance;
    private List<RecordsLoaderAndSaver.Record> recordsList;
    private GridPane recordsBox;
    private VBox mainBox;
    private Button backButton;

    public static RecordsScreen getInstance(int windowWidth, int windowHeight) {
        if (instance == null) {
            instance = new RecordsScreen(windowWidth, windowHeight);
        }
        return instance;
    }

    private RecordsScreen(int windowWidth, int windowHeight) {
        mainBox = new VBox();
        mainBox.setPrefWidth(windowWidth);
        mainBox.setAlignment(Pos.CENTER);
        mainBox.setSpacing(15);
        mainBox.setTranslateY(20);
        getChildren().add(mainBox);

        setPrefSize(windowWidth, windowHeight);
        Text text = new Text("Рекорды");
        text.setId("textInRecordsScreen");


        recordsBox = new GridPane();
        recordsBox.setAlignment(Pos.CENTER);
        recordsBox.setId("recordsBox");
        recordsBox.setPrefWidth(windowWidth);
        recordsBox.setMinHeight(windowHeight/2);
        recordsBox.setHgap(10);
        recordsBox.setVgap(10);


        backButton = new Button("Назад");
        mainBox.getChildren().addAll(text, recordsBox, backButton);
    }

    public Button getBackButton() {
        return backButton;
    }
    public void setRecordsList(List<RecordsLoaderAndSaver.Record> recordsList) {
        this.recordsList = recordsList;
        updateRecordsBox();
    }

    public void updateRecordsBox() {
        recordsBox.getChildren().clear();
        Collections.reverse(recordsList);
        int i = 0;
        for (RecordsLoaderAndSaver.Record record : recordsList) {
            recordsBox.add(new Text(record.getName()), 0, i);
            recordsBox.add(new Text(String.valueOf(record.getScore())), 1, i);
            i++;
        }
    }
}
