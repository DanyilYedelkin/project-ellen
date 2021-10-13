package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.actions.Loop;

public class SmartCooler extends Cooler {
    private Reactor reactor;

    public SmartCooler(Reactor reactor) {
        super(reactor);
        if(reactor != null){
            this.reactor = reactor;
        }
    }


    private void smartCoolReactor(){
        if(reactor != null){
            if(reactor.getTemperature() >= 2500){
                turnOn();
            }
            if(reactor.getTemperature() <= 1500){
                turnOff();
            }
        }
    }

    @Override
    public void addedToScene(Scene scene) {
        super.addedToScene(scene);

        new Loop<>(new Invoke<>(this::smartCoolReactor)).scheduleFor(this);
    }
}
