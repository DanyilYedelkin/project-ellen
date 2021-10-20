package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;

import static sk.tuke.kpi.gamelib.graphics.Animation.PlayMode.LOOP_PINGPONG;

public class Cooler extends AbstractActor  implements Switchable{
    private Animation coolerAnimation;
    private boolean isRunning;
    private Reactor reactor;

    public Cooler(Reactor reactor){
        if(reactor != null){
            this.reactor = reactor;
        }
        isRunning = false;

        coolerAnimation = new Animation("sprites/fan.png", 32, 32, 0.2f, LOOP_PINGPONG);
        setAnimation(coolerAnimation);
        turnOff();
    }

    @Override
    public void turnOn(){
        if(reactor != null){
            isRunning = true;
            getAnimation().play();
        }
    }
    @Override
    public void turnOff(){
        if(reactor != null){
            isRunning = false;
            getAnimation().stop();
        }
    }
    @Override
    public boolean isOn() {
        return isRunning;
    }
    private void coolReactor(){
        if(reactor != null && isRunning == true){
            reactor.decreaseTemperature(1);
        }
    }

    @Override
    public void addedToScene(Scene scene) {
        super.addedToScene(scene);

        new Loop<>(new Invoke<>(this::coolReactor)).scheduleFor(this);
    }
}
