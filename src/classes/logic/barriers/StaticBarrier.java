package logic.barriers;

public class StaticBarrier extends Barrier {

    public StaticBarrier(int windowWidth) {
        super(windowWidth);
        setId("staticBarrier");
        type = Type.Static;
    }

    @Override
    public void action() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void continueIt() {

    }
}
