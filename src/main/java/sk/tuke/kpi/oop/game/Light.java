package sk.tuke.kpi.oop.game;

//add libraries
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Light extends AbstractActor implements Switchable, EnergyConsumer{
    //add private variables of animations
    private Animation lightOffAnimation;
    private Animation lightOnAnimation;

    /* create variables isRunning and isPowered */
    private boolean isRunning;      // a variable, that checks, if our Light is working
    private boolean isPowered;      // a variable, that checks, if our Light has an electricity

    public Light() {
        isRunning = false;  //default
        isPowered = false;  //default

        // create animation object
        lightOnAnimation = new Animation("sprites/light_on.png", 16, 16, 0.1f);
        lightOffAnimation = new Animation("sprites/light_off.png", 16, 16, 0.1f);
        // set actor's animation to just created Animation object
        setAnimation(lightOffAnimation);
    }

    /* a method will change light's state from on to off and vice-versa */
    public void toggle() {
        if(!isRunning) {    //if it doesn't work
            isRunning = true;
            updateAnimation();
            turnOn();
        } else{     //if it works
            isRunning = false;
            updateAnimation();
            turnOff();
        }
    }

    /* a method, which update light's animation */
    private void updateAnimation() {
        if(isRunning && this.isPowered){    //if we want to turn it on
            setAnimation(lightOnAnimation);
        } else{                             //or to turn it off
            setAnimation(lightOffAnimation);
        }
    }

    /* a method will specify whether electricity is provided to light or not */
    public void setPowered(boolean electricity) {
        this.isPowered = electricity;
        updateAnimation();
    }

    /* a method, that checks if our light is working */
    @Override
    public boolean isOn() {
        return isRunning;
    }

    @Override   //override a method, which turns the light on
    public void turnOn(){
        isRunning = true;
        updateAnimation();
    }

    @Override   //override a method, which turns the light off
    public void  turnOff(){
        isRunning = false;
        updateAnimation();
    }
}
