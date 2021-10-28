package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.actions.Loop;

public class SmartCooler extends Cooler {
    private Reactor reactor;
    private boolean isRunning;

    public SmartCooler(Reactor reactor) {
        super(reactor);
        isRunning = false;
        if(reactor != null){
            this.reactor = reactor;
        }
        this.turnOff();
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

    @Override
    public void turnOff(){
        if(reactor != null){
            isRunning = false;
            getAnimation().stop();
        }
    }
    @Override
    public void turnOn(){
        if(reactor != null){
            isRunning = true;
            getAnimation().play();
        }
    }
    @Override
    public boolean isOn() {
        return isRunning;
    }
}
