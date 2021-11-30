package sk.tuke.kpi.oop.game.weapons;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.characters.Alive;
//import sk.tuke.kpi.oop.game.characters.Ripley;

import java.util.Objects;

public class Bullet extends AbstractActor implements Fireable {
    private int speed;

    public Bullet(){
        speed = 4;
        Animation bulletAnimation = new Animation("sprites/bullet.png", 16, 16);

        setAnimation(bulletAnimation);
    }

    @Override
    public int getSpeed() {
        return speed;
    }
    @Override
    public void startedMoving(Direction direction){
        if(direction != null && direction != Direction.NONE){
            this.getAnimation().setRotation(direction.getAngle());
        }
    }
    @Override
    public void collidedWithWall() {
        Objects.requireNonNull(getScene()).removeActor(this);
    }
    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        new Loop<>(
            new Invoke<>(this::shooting)
        ).scheduleFor(this);

    }
    private void shooting(){
        for(Actor actor : Objects.requireNonNull(getScene()).getActors()){
            if((actor instanceof Alive) && this.intersects(actor)){
                ((Alive) actor).getHealth().drain(40);

                collidedWithWall();
            }
        }
    }
}
