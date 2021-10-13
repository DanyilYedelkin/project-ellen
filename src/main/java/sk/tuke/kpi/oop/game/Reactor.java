package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.actions.PerpetualReactorHeating;
import sk.tuke.kpi.oop.game.tools.FireExtinguisher;
import sk.tuke.kpi.oop.game.tools.Hammer;

public class Reactor extends AbstractActor {
    private int temperature;            // a variable, which checks a temperature of the reactor
    private int damage;                 // a variable, which checks damage to the reactor
    private boolean running;            // a variable, which checks a temperature of the reactor
    private Light light;                // a light for the reactor

    private Animation normalAnimation;
    private Animation overheatedAnimation;
    private Animation destroyAnimation;
    private Animation offAnimation;
    private Animation estinguishedAnimation;

    public Reactor() {
        temperature = 0;            // initial temperature
        damage = 0;                 // initial damage
        running = false;            // initial working of the reactor

        /* create animations for the object */
        normalAnimation = new Animation("sprites/reactor_on.png",
            80, 80, 0.2f, Animation.PlayMode.LOOP_PINGPONG);

        overheatedAnimation = new Animation("sprites/reactor_hot.png",
            80, 80, 0.1f, Animation.PlayMode.LOOP_PINGPONG);

        destroyAnimation = new Animation("sprites/reactor_broken.png",
            80, 80, 0.08f, Animation.PlayMode.LOOP_PINGPONG);

        offAnimation = new Animation("sprites/reactor.png", 80, 80, 0.1f);

        estinguishedAnimation = new Animation("sprites/reactor_extinguished.png",
            80, 80, 0.12f, Animation.PlayMode.LOOP_PINGPONG);

        // set actor's animation to just created Animation object
        setAnimation(offAnimation);         // initial animation of the reactor (turn off)
    }

    /* method increaseTemperature() by using which increasing current temperature of reactor's core will be possible */
    public void increaseTemperature(float increment) {
        if (running == true) {
            if (increment > 0 && this.damage != 100) {
                if (damage < 33 && temperature <= 6000) {
                    temperature += increment;
                } else if (damage >= 33 && damage <= 66) {
                    temperature += (increment * 1.5f);
                } else if (damage > 66 && damage <= 100) {
                    temperature += (increment * 2);
                }

                //for current damage to the reactor
                if (temperature > 2000 && temperature <= 6000) {
                    damage = (temperature - 2000) / 40;
                } else if (temperature >= 6000) {
                    damage = 100;
                }

                updateAnimation();
            }
        }
    }

    /* method decreaseTemperature() which will allow to decrease actual temperature of reactor's core */
    public void decreaseTemperature(int decrement) {
        if (decrement > 0 && running == true && temperature >= 0) {
            if (damage < 50) {
                temperature -= decrement;
            }
            else if (damage >= 50 && damage < 100) {
                temperature -= decrement / 2;
            }

            updateAnimation();
        }
    }

    /* method updateAnimation() which will set proper animation of reactor according to its current temperature. */
    private void updateAnimation() {
        if (running == true) {
            if (temperature < 4000) {
                setAnimation(normalAnimation);
            } else if (temperature > 4000 && temperature < 6000) {
                setAnimation(overheatedAnimation);
            } else if (damage == 100) {
                running = false;
                setAnimation(destroyAnimation);
                updateLights();
            }
        } else if (damage != 100) {
            setAnimation(offAnimation);
        }

        updateLights();
    }

    /* method getTemperature() which will allow you to obtain current value of reactor's core temperature */
    public int getTemperature() {
        return temperature;
    }

    /* method getDamage() which will allow you to obtain current value of reactor's core damage. */
    public int getDamage() {
        return damage;
    }

    /*  method repairWith() repairs reactor (health and temperature) */
    public void repairWith(Hammer hammer) {
        if ((hammer != null) && (this.damage > 0 && this.damage < 100)) {
            int decrement = this.damage - 50;
            if (decrement > 0) {
                temperature = (int)Math.floor((decrement / 0.025) + 2000);
                damage -= 50;
            } else if (decrement < 0) {
                temperature = (int)Math.floor((decrement / 0.025) + 2000);
                damage = 0;
            }

            hammer.use();
            updateAnimation();
        }
    }

    /* method turnOn(), which will allow turn on the reactor. */
    public void turnOn() {
        if(damage != 100) {
            running = true;
            light.toggle();
        } else {
            running = false;
            light.toggle();
        }
        updateAnimation();
    }

    /* methods turnOff() which will allow turn off the reactor. */
    public void turnOff() {
        if (damage != 100) {
            running = false;
            light.toggle();
        } else {
            running = false;
            light.toggle();
        }
        updateAnimation();
    }

    /* method isRunning() which will find out whether reactor is on or off */
    public boolean isRunning() {
        if(running == true) {
            return true;
        } else {
            return false;
        }
    }

    /* method addLight() with which you will be able to connect light to reactor. */
    public void addLight(Light light) {
        this.light = light;
        light.setElectricityFlow(true);

        this.light.setElectricityFlow(true);
        updateLights();
    }

    /* method removeLight() with which you will be able to disconnect light from reactor. */
    public void removeLights(Light light) {
        light.setElectricityFlow(false);

        this.light.setElectricityFlow(false);
        this.light = null;
    }

    /* method updateLights() will update the status (animation) of reactor */
    public void updateLights() {
        if(light == null){
            return;
        }
        if (running == true && damage != 100) {
            light.setElectricityFlow(true);
        } else {
            light.setElectricityFlow(false);
        }
    }

    /* method extinguishWith() which will extinguish burning broken reactor */
    public void extinguishWith(FireExtinguisher fireExtinguisher) {
        if ((fireExtinguisher != null) && (damage == 100)) {
            fireExtinguisher.use();
            temperature = 4000;
            setAnimation(estinguishedAnimation);
        }
    }

    @Override
    public void addedToScene(Scene scene) {
        super.addedToScene(scene);

        new PerpetualReactorHeating(1).scheduleFor(this);
    }
}
