package logic.bonuses;


public class StarBonus extends Bonus {
    private static int score = 10;



    public StarBonus(int windowWidth) {
        super(windowWidth);
        type = Type.Star;
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
