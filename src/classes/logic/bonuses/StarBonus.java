package logic.bonuses;


public class StarBonus extends Bonus {
    private static int score = 10;
    private static int firesCount = 2;


    public StarBonus(int windowWidth) {
        super(windowWidth);
        setId("starBonus");
        type = Type.Star;
    }

    @Override
    public int getScore() {
        return score;
    }

    @Override
    public int getFiresCount() {
        return firesCount;
    }

    @Override
    public void pause() {

    }

    @Override
    public void continueIt() {

    }
}
