package platforms;

import javafx.util.Duration;

public class BasicFadePlatform extends Platform {
    private OpacityTransition opacityTransition;

    public BasicFadePlatform() {
        type = Type.BasicFade;
        opacityTransition = new OpacityTransition(Duration.seconds(2), this);
        parallelTransition.getChildren().add(opacityTransition);
    }



    @Override
    public void touch() {

    }
}
