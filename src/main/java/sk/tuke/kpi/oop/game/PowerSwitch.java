package sk.tuke.kpi.oop.game;

//add libraries
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.graphics.Color;


public class PowerSwitch extends AbstractActor {
    //add private variable
    private Switchable device;

    public PowerSwitch(Switchable device) {
        this.device = device;

        setAnimation(new Animation("sprites/switch.png", 16, 16));
    }

    //a method, which changed color of a switchable
    public void setTint(){
        if(!this.device.isOn() && this.device != null){
            getAnimation().setTint(Color.GRAY);
        } else{
            getAnimation().setTint(Color.WHITE);
        }
    }

    // a method, which return the device, which is connected with a switchable
    public Switchable getDevice(){
        return device;
    }

    // a method, which turns on our switchable, and changes his color
    public void switchOn(){
        if(device != null && !device.isOn()) {
            device.turnOn();
            setTint();
        }
    }

    // a method, which turns off our switchable, and changes his color
    public void switchOff(){
        if(device != null) {    //was some problems in arena
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
