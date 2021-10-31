package sk.tuke.kpi.oop.game;

//add libraries
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Computer extends AbstractActor implements EnergyConsumer {
    //add variable
    private boolean isPowered;  //check if a computer has an electricity
    private Animation computerAnimation; //a computer's animation

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

    @Override   //override method for a computer
    public void setPowered(boolean electricity) {
        isPowered = electricity;
        updateAnimation();
    }

    //a method, which changed computer's animation since his powered
    public void updateAnimation() {
        if (isPowered) {
            computerAnimation.play();
        } else{
            computerAnimation.stop();
        }
    }

}
