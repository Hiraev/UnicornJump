package logic.bonuses;

public class RainbowBonus extends Bonus {
    private static int score = 20;

    public RainbowBonus(int windowWidth) {
        super(windowWidth);
        type = Type.Rainbow;
    }

    @Override
    public int getScore() {
        return score;
    }

    @Override
    public void pause() {

    }

    @Override
    public void continueIt() {

    }
}
