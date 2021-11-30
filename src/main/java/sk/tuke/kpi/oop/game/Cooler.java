package sk.tuke.kpi.oop.game;

//add libraries
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;

import static sk.tuke.kpi.gamelib.graphics.Animation.PlayMode.LOOP_PINGPONG;

public class Cooler extends AbstractActor  implements Switchable{
    //add private variables
    private boolean isRunning;  //check if cooler is running
    private Reactor reactor;    //add a reactor for a cooler

    public Cooler(Reactor reactor){
        if(reactor != null){
            this.reactor = reactor;
        }
        isRunning = false;

        setAnimation(new Animation("sprites/fan.png", 32, 32, 0.2f, LOOP_PINGPONG));
        //turnOff();
        turnOn();
    }

    public void setReactor(Reactor reactor){
        this.reactor = reactor;
    }

    @Override    //override method for cooler (turn it on)
    public void turnOn(){
        if(reactor != null){
            isRunning = true;
            getAnimation().play();
        }
    }
    @Override   //override method for cooler (turn it off)
    public void turnOff(){
        if(reactor != null){
            isRunning = false;
            getAnimation().stop();
        }
    }
    @Override   //override method for cooler (return, if a cooler is running)
    public boolean isOn() {
        return isRunning;
    }

    // a method, which decreases the reactor's temperature by 1 points
    private void coolReactor(){
        if(reactor != null && isRunning){
            reactor.decreaseTemperature(1);
        }
    }

    @Override
    public void addedToScene(Scene scene) {
        super.addedToScene(scene);

        new Loop<>(new Invoke<>(this::coolReactor)).scheduleFor(this);
    }
}
