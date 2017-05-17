package gui.menu;

import javafx.geometry.Pos;
import javafx.scene.layout.VBox;

public class MenuBox extends VBox {
    public MenuBox(Button... buttons) {
        setAlignment(Pos.CENTER);
        setSpacing(10);
        getChildren().addAll(buttons);
        setPrefSize(100, (buttons[0].getPrefHeight() + 10) * buttons.length);
        //setStyle("-fx-background-color: red;");
        System.out.println(getPrefHeight());
    }
}
