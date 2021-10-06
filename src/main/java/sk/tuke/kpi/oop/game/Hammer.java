package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Hammer extends AbstractActor {
    private Animation hammerAnimation;
    protected int healthUse;       // a variable, that have a health point of the hammer


    public Hammer() {
        this.healthUse = 1;        // health point of a regular hammer

        // create animation object
        hammerAnimation = new Animation("sprites/hammer.png", 16, 16);
        // set actor's animation to just created Animation object
        setAnimation(hammerAnimation);
    }

    /* a method, that returns health points of the hammer */
    public int getHealth() {
        return this.healthUse;
    }

    /* method use() which will represent hammer's usage (health points) */
    public void use() {
        if (this.healthUse > 0) {
            this.healthUse -= 1;
        }
        if (this.healthUse == 0) {
            this.getScene().removeActor(this);      // will remove the item from the scene
        }
    }
}
