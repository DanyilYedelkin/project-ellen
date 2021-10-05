package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Reactor extends AbstractActor {
    private int temperature;
    private int damage;
    private boolean running;
    private Light light;
    private Animation normalAnimation;
    private Animation overheatedAnimation;
    private Animation destroyAnimation;
    private Animation offAnimation;
    private Animation estinguishedAnimation;

    public Reactor(){
        this.temperature = 0;
        this.damage = 0;
        this.running = false;
        this.normalAnimation = new Animation("sprites/reactor_on.png",
            80, 80, 0.2f, Animation.PlayMode.LOOP_PINGPONG);

        this.overheatedAnimation = new Animation("sprites/reactor_hot.png",
            80, 80, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
        this.destroyAnimation = new Animation("sprites/reactor_broken.png",
            80, 80, 0.08f, Animation.PlayMode.LOOP_PINGPONG);
        this.offAnimation = new Animation("sprites/reactor.png", 80, 80, 0.1f);

        this.estinguishedAnimation = new Animation("sprites/reactor_extinguished.png",
            80, 80, 0.12f, Animation.PlayMode.LOOP_PINGPONG);

        setAnimation(offAnimation);
        //updateAnimation();
    }

    public void increaseTemperature(int increment){
        if(running == true) {
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
    }

    public void decreaseTemperature(int decrement){
        if(decrement > 0 && this.running == true){
            if(this.damage < 50){
                this.temperature -= decrement;
            } else if(this.damage >= 50 && this.damage < 100){
                this.temperature -= decrement / 2;
            } else{
                this.temperature += 0;
            }
            updateAnimation();
        }
    }
    private void updateAnimation(){
        if(running == true) {
            if (this.temperature < 4000) setAnimation(normalAnimation);
            else if (this.temperature > 4000 && this.temperature < 6000) setAnimation(overheatedAnimation);
            else if (this.damage == 100){
                running = false;
                setAnimation(destroyAnimation);
            }
        } else if(this.damage != 100){
            setAnimation(offAnimation);
        }
        updateLights();
    }

    public int getTemperature() { return this.temperature; }
    public int getDamage() { return this.damage; }


    //the lesson of the 3 week   =======================================================================

    public void repairWith(Hammer hammer){
        if((hammer != null) && (this.damage > 0 && this.damage < 100)){
            int decrement = this.damage - 50;
            if(decrement > 0){
                this.temperature = (int)Math.floor((decrement / 0.025) + 2000);
                this.damage -= 50;
            } else if(decrement < 0){
                this.temperature = (int)Math.floor((decrement / 0.025) + 2000);
                this.damage = 0;
            }
            hammer.use();
            updateAnimation();
        }
    }
    public void turnOn(){
        if(damage != 100) running = true;
        else running = false;
        updateAnimation();
    }
    public void turnOff(){
        if(damage != 100) running = false;
        else running = false;
        updateAnimation();
    }
    public boolean isRunning(){
        if(running == true) return true;
        else return false;
    }

    public void addLight(Light light){
        this.light = light;
        updateLights();
    }
    public void removeLights(@NotNull Light light){
        light.setElectricityFlow(false);
        //updateLights();
        this.light = null;
    }
    public void updateLights(){
        if(running == true && damage != 100) this.light.setElectricityFlow(true);
        else this.light.setElectricityFlow(false);
    }

    public void extinguishWith(FireExtinguisher fireExtinguisher){
        if((fireExtinguisher != null) && (damage == 100)){
            fireExtinguisher.use();
            temperature = 4000;
            setAnimation(estinguishedAnimation);
        }
    }
}
