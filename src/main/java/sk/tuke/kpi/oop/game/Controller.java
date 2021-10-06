package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Controller extends AbstractActor {
    private Reactor reactor;
    private Animation controllerAnimation;

    public Controller(Reactor reactor) {
        this.reactor = reactor;

        // create animation object
        controllerAnimation = new Animation("sprites/switch.png", 16, 16);
        // set actor's animation to just created Animation object
        setAnimation(controllerAnimation);
    }

    /* method toggle() which will turn on or off the controlled reactor */
    public void toggle() {
        if (reactor != null) {
            if (reactor.isRunning() == true) {
                reactor.turnOff();
            } else {
                reactor.turnOn();
            }
        }
    }

}
