package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Computer extends AbstractActor implements EnergyConsumer {
    private boolean isPowered;
    private Animation computerAnimation;

    public Computer() {
        isPowered = false;
        // create animation object
        computerAnimation = new Animation("sprites/computer.png", 80, 48, 0.2f, Animation.PlayMode.LOOP);
        // set actor's animation to just created Animation object
        setAnimation(computerAnimation);

        updateAnimation();
    }

    /* a method for basic arithmetic operations for numeric data type float */
    public float add(float one, float two) {
        if(isPowered){
            return one + two;
        } else{
            return 0;
        }
    }

    /* a method for basic arithmetic operations for numeric data type int */
    public int add(int one, int two) {
        if(isPowered){
            return one + two;
        } else{
            return 0;
        }
    }

    /* a method for basic arithmetic operations for numeric data type int */
    public int sub(int one, int two) {
        if(isPowered){
            return one - two;
        } else{
            return 0;
        }
    }

    /* a method for basic arithmetic operations for numeric data type float */
    public float sub(float one, float two) {
        if(isPowered){
            return one - two;
        } else{
            return 0;
        }
    }

    @Override
    public void setPowered(boolean electricity) {
        isPowered = electricity;
        updateAnimation();
    }

    public void updateAnimation() {
        if (isPowered == true) {
            computerAnimation.play();
        } else{
            computerAnimation.stop();
        }
    }

}
