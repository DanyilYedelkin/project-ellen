package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.Reactor;

public class PerpetualReactorHeating extends AbstractAction<Reactor> {
    private int increment;

    public PerpetualReactorHeating(int increment) {
        this.increment = increment;
    }
    @Override
    public void execute(float deltaTime){
        getActor().increaseTemperature(increment);
    }
}
