package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Light extends AbstractActor implements Switchable, EnergyConsumer{
    private Animation lightOffAnimation;
    private Animation lightOnAnimation;

    /* create variables isRunning and isPowered */
    private boolean isRunning;      // a variable, that checks, if our Light is working
    private boolean isPowered;      // a variable, that checks, if our Light has an electricity

    public Light() {
        isRunning = false;
        isPowered = false;
        // create animation object
        lightOnAnimation = new Animation("sprites/light_on.png", 16, 16, 0.1f);
        lightOffAnimation = new Animation("sprites/light_off.png", 16, 16, 0.1f);
        // set actor's animation to just created Animation object
        setAnimation(lightOffAnimation);
    }

    /* a method will change light's state from on to off and vice-versa */
    public void toggle() {
        if (!isRunning) {
            isRunning = true;
            updateAnimation();
            turnOn();
        } else {
            isRunning = false;
            updateAnimation();
            turnOff();
        }
    }

    /* a method, which update light's animation */
    private void updateAnimation() {
        if (isRunning && this.isPowered) {
            setAnimation(lightOnAnimation);
        } else{
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

    @Override
    public void turnOn(){
        isRunning = true;
        updateAnimation();
    }

    @Override
    public void  turnOff(){
        isRunning = false;
        updateAnimation();
    }


}
