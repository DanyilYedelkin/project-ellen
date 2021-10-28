package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.graphics.Color;


public class PowerSwitch extends AbstractActor {
    //private Animation switchAnimation;
    private Switchable device;

    public PowerSwitch(Switchable device) {
        this.device = device;

        // create animation object
        //switchAnimation = new Animation("sprites/switch.png", 16, 16);
        // set actor's animation to just created Animation object
        //setAnimation(switchAnimation);
        setAnimation(new Animation("sprites/switch.png", 16, 16));
    }
    public void setTint(){
        if(!this.device.isOn() && this.device != null){
            getAnimation().setTint(Color.GRAY);
        } else{
            getAnimation().setTint(Color.WHITE);
        }
    }

    public Switchable getDevice(){
        //this.device = device;
        //setTint();
        return device;
    }

    public void switchOn(){
        if(device != null && !device.isOn()) {
            device.turnOn();
            setTint();
        }
    }

    public void switchOff(){
        if(device != null /*&& device.isOn()*/) {
            toggle();
            device.turnOff();
            this.device.turnOff();
            setTint();
        }
    }


    /* method toggle() which will turn on or off the controlled reactor */
    public void toggle() {
        if (device != null) {
            if (device.isOn()) {
                device.turnOff();
            } else {
                device.turnOn();
            }
        }
    }

    @Override
    public void addedToScene(Scene scene) {
        super.addedToScene(scene);

        if(device != null) setTint();
    }

}
