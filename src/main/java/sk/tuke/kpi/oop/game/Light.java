package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Light extends AbstractActor {
    private Animation lightOffAnimation;
    private Animation lightOnAnimation;

    /* create variables isRunning and isPowered */
    private boolean isRunning;      // a variable, that checks, if our Light is working
    private boolean isPowered;      // a variable, that checks, if our Light has an electricity

    public Light() {
        // create animation object
        lightOnAnimation = new Animation("sprites/light_on.png", 16, 16, 0.1f);
        lightOffAnimation = new Animation("sprites/light_off.png", 16, 16, 0.1f);
        // set actor's animation to just created Animation object
        setAnimation(lightOffAnimation);
    }

    /* a method will change light's state from on to off and vice-versa */
    public void toggle() {
        if (isRunning == false) {
            isRunning = true;
            updateAnimation();
        } else {
            isRunning = false;
            updateAnimation();
        }
    }

    /* a method, which update light's animation */
    private void updateAnimation() {
        if (isRunning == false && isPowered == false) {
            setAnimation(lightOffAnimation);
        } else {
            setAnimation(lightOnAnimation);
        }
    }

    /* a method will specify whether electricity is provided to light or not */
    public void setElectricityFlow(boolean newPower) {
        isPowered = newPower;
        updateAnimation();
    }

    /* a method, that checks if our light is working */
    public boolean isWorking() {
        return isRunning;
    }

}
