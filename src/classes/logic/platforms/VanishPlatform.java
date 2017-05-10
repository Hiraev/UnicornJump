package logic.platforms;

public class VanishPlatform extends Platform {
    private int gameWidth;
    public VanishPlatform(int gameWidth) {
        type = Type.Vanish;
        this.gameWidth = gameWidth;
    }

    @Override
    public void play() {

    }

    @Override
    public void touch() {
        setTranslateX(2 * gameWidth);
    }
}
