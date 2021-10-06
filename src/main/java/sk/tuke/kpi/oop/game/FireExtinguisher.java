package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class FireExtinguisher extends AbstractActor {
    private Animation extinguisherAnimation;

    public FireExtinguisher() {
        // create animation object
        extinguisherAnimation = new Animation("sprites/extinguisher.png", 16, 16);
        // set actor's animation to just created Animation object
        setAnimation(extinguisherAnimation);
    }

    /* a method for using fire extinguisher, which will be removed from the scene */
    public void use() {
        this.getScene().removeActor(this);
    }
}
