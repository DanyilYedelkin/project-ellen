package sk.tuke.kpi.oop.game.tools;

import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.tools.BreakableTool;

public class Hammer extends BreakableTool {
    private Animation hammerAnimation;
    private int healthUse;       // a variable, that have a health point of the hammer


    public Hammer() {
        super(1);
        this.healthUse = 1;        // health point of a regular hammer
        setRemainingUses(1);
        // create animation object
        hammerAnimation = new Animation("sprites/hammer.png", 16, 16);
        // set actor's animation to just created Animation object
        setAnimation(hammerAnimation);
    }

    /* a method, that returns health points of the hammer */
    public int getHealth() {
        return this.healthUse;
    }
}
