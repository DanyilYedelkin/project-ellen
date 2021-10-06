package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Computer extends AbstractActor{
    private Animation computerAnimation;

    public Computer() {
        // create animation object
        computerAnimation = new Animation("sprites/computer.png",
            80, 48, 0.2f, Animation.PlayMode.LOOP);
        // set actor's animation to just created Animation object
        setAnimation(computerAnimation);
    }

    /* a method for basic arithmetic operations for numeric data type float */
    public float add(float one, float two) {
        return one + two;
    }

    /* a method for basic arithmetic operations for numeric data type int */
    public int add(int one, int two) {
        return  one + two;
    }

    /* a method for basic arithmetic operations for numeric data type int */
    public int sub(int one, int two) {
        return one - two;
    }

    /* a method for basic arithmetic operations for numeric data type float */
    public float sub(float one, float two) {
        return one - two;
    }

}
