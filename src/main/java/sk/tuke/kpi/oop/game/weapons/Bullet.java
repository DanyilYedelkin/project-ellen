package sk.tuke.kpi.oop.game.weapons;

//add libraries
import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.characters.Alive;


import java.util.Objects;

public class Bullet extends AbstractActor implements Fireable, WeaponInterface {
    //create private variables
    private int speed;
    private int damage;

    //main function
    public Bullet(){
        //setting default values
        speed = 4;
        damage = 40;

        //create an animation and set it on
        Animation bulletAnimation = new Animation("sprites/bullet.png", 16, 16);
        setAnimation(bulletAnimation);
    }

    //a method, which return the bullet speed
    @Override
    public int getSpeed() {
        return speed;
    }

    //a method, with which the ellen can start his moving
    @Override
    public void startedMoving(Direction direction){
        //start the ellen's moving with checking his current direction
        if(direction != null && direction != Direction.NONE){
            this.getAnimation().setRotation(direction.getAngle());
        }
    }

    //a method, which checks "collided with walls"
    @Override
    public void collidedWithWall(){
        //if the bullet collides with wall, the bullet will be removed from the scene
        Objects.requireNonNull(getScene()).removeActor(this);
    }

    //a method, which add this actor to the scene (world)
    @Override
    public void addedToScene(@NotNull Scene scene){
        super.addedToScene(scene);

        //the cycle for shooting
        new Loop<>(
            new Invoke<>(this::shooting)
        ).scheduleFor(this);

    }

    //a private method, with which ellen can shoot
    private void shooting(){
        for(Actor actor : Objects.requireNonNull(getScene()).getActors()){
            //if the actor from the class Alive and if the bullet is intersects with the actor
            if((actor instanceof Alive) && this.intersects(actor)){
                ((Alive) actor).getHealth().drain(damage);

                //call the another method
                collidedWithWall();
            }
        }
    }

    //a method, with which we can set new damage for the gun
    @Override
    public void setDamage(int damage) {
        this.damage = damage;
    }

    //a method, with which we can set new speed for the gun
    @Override
    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
