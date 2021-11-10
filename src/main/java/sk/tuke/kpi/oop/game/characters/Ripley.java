package sk.tuke.kpi.oop.game.characters;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.items.Backpack;


public class Ripley extends AbstractActor implements Movable, Keeper {
    private int speed;
    private Animation ripleyAnimation;
    private int energy;
    private int ammo;
    private Backpack backpack;

    public Ripley(){
        super("Ellen");
        energy = 60;
        speed = 2;
        ammo = 100;
        backpack = new Backpack("Ripley's backpack", 10);

        ripleyAnimation = new Animation("sprites/player.png",
            32, 32, 0.1f, Animation.PlayMode.LOOP_PINGPONG);

        setAnimation(ripleyAnimation);
        stoppedMoving();
    }

    @Override
    public int getSpeed() {
        return speed;
    }
    @Override
    public void startedMoving(Direction direction){
        ripleyAnimation.setRotation(direction.getAngle());
        ripleyAnimation.play();
    }
    @Override
    public void stoppedMoving(){
        ripleyAnimation.stop();
    }

    public int getEnergy(){
        return energy;
    }

    public void setEnergy(int energy){
        if(energy >=0 && energy <= 100){
            this.energy = energy;
        }
    }

    public int getAmmo() { return ammo; }

    public void setAmmo(int ammo) { this.ammo = ammo; }


    @Override
    public Backpack getBackpack() {
        return backpack;
    }
}
