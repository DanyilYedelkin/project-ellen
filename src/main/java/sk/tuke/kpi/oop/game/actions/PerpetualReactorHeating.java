package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.Reactor;

public class PerpetualReactorHeating extends AbstractAction<Reactor> {
    private Reactor reactor;

    public PerpetualReactorHeating(int heating) {
        super();
    }

    @Override
    public Reactor getActor(){
        return reactor;
    }
    @Override
    public void setActor(Reactor reactor){
        this.reactor = reactor;
    }
    @Override
    public void execute(float deltaTime){
        reactor.increaseTemperature(deltaTime);
    }
}
