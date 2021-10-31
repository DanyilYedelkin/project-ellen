package sk.tuke.kpi.oop.game.tools;

/* (the code is not perfect, later, it will be modified and improved) */

//add libraries
import sk.tuke.kpi.gamelib.graphics.Animation;
//import sk.tuke.kpi.oop.game.tools.Hammer;

public class Mjolnir extends Hammer {
    //private Animation mjolnirAnimation;

    public Mjolnir() {
        super();
        setRemainingUses(4);

        /* create an animation for mjolnir (a hammer, that have 4 health points) */
        //mjolnirAnimation = new Animation("sprites/hammer.png", 16, 16);
        // set actor's animation to just created Animation object
        //setAnimation(mjolnirAnimation);
        setAnimation(new Animation("sprites/hammer.png", 16, 16));
    }
}
