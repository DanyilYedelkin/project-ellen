package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Reactor extends AbstractActor {
    private int temperature;
    private int damage;
    private Animation normalAnimation;
    private Animation overheatedAnimation;
    private Animation destroyAnimation;

    public Reactor(){
        this.temperature = 0;
        this.damage = 0;
        this.normalAnimation = new Animation("sprites/reactor_on.png",
            80, 80, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
        setAnimation(normalAnimation);

        this.overheatedAnimation = new Animation("sprites/reactor_hot.png",
            80, 80, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
        this.destroyAnimation = new Animation("sprites/reactor_broken.png",
            80, 80, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
    }

    public void increaseTemperature(int increment){
        if(increment > 0 && this.damage != 100){
            if (this.damage < 33 && this.temperature <= 6000){
                this.temperature += increment;
            } else if (this.damage >= 33 && this.damage <= 66) {
                this.temperature += (increment * 1.5f);
            } else if(this.damage > 66 && this.damage <= 100){
                this.temperature = this.temperature + (increment * 2);
            }

            //for the damage of the reactor
            if (this.temperature > 2000 && this.temperature <= 6000){
                this.damage = (this.temperature - 2000) / 40;
            } else if (this.temperature >= 6000){
                this.damage = 100;
            }
            updateAnimation();
        }
    }

    public void decreaseTemperature(int decrement){
        if(this.damage < 50){
            this.temperature -= decrement;
        } else if(this.damage >= 50 && this.damage < 100){
            this.temperature -= decrement / 2;
        } else{
            this.temperature += 0;
        }
        updateAnimation();
    }
    private void updateAnimation(){
        if (this.temperature < 4000) setAnimation(normalAnimation);
        else if (this.temperature > 4000 && this.temperature < 6000) setAnimation(overheatedAnimation);
        else if (this.damage == 100) setAnimation(destroyAnimation);
    }

    public int getTemperature() { return this.temperature; }
    public int getDamage() { return this.damage; }
}
