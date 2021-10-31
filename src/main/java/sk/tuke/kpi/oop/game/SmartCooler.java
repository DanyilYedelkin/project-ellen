package sk.tuke.kpi.oop.game;

//add libraries
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.actions.Loop;

public class SmartCooler extends Cooler {
    //add private variables
    private Reactor reactor;
    private boolean isRunning;  //check if a smart cooler is on (works)

    public SmartCooler(Reactor reactor) {
        super(reactor);
        isRunning = false;  //default
        if(reactor != null){
            this.reactor = reactor;
        }
        this.turnOff();     //default
    }

    // a method, which turns on and turns off our smart cooler
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

    @Override //override a method, which turns off the smart cooler
    public void turnOff(){
        if(reactor != null){
            isRunning = false;
            getAnimation().stop();
        }
    }
    @Override   //override a method, which turns on the smart cooler
    public void turnOn(){
        if(reactor != null){
            isRunning = true;
            getAnimation().play();
        }
    }
    @Override   //override a method, which checks the smart cooler's electricity
    public boolean isOn() {
        return isRunning;
    }
}
