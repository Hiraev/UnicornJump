package animations;

import javafx.animation.Transition;

public class ParallelTransitionThread implements Runnable{
    private Transition transition;
    public ParallelTransitionThread(Transition transition) {
        this.transition = transition;
    }

    @Override
    public void run() {
        transition.play();
    }

    public void pause() {
        transition.pause();
    }

    public void continueTransition(){
        transition.play();
    }
}
