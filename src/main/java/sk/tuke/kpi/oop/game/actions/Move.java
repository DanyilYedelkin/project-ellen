package sk.tuke.kpi.oop.game.actions;

import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.actions.Action;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Movable;

import java.util.Objects;

public class Move<A extends Movable> implements Action<A> {
    private Direction direction;
    private A actor;
    private float duration;
    private boolean isDone;
    private boolean isFirst;
    private int time;

    public Move(Direction direction, float duration) {
        // implementation of actor's constructor
        isDone = false;
        isFirst = false;
        this.direction = direction;
        this.duration = duration;
        time = 0;
    }
    public Move(Direction direction) {
        // implementation of actor's constructor
        isDone = false;
        isFirst = false;
        this.direction = direction;
        time = 0;
    }

    @Override
    public @Nullable A getActor() {
        return actor;
    }

    @Override
    public void setActor(@Nullable A actor) {
        this.actor = actor;
    }

    @Override
    public boolean isDone() {
        return isDone;
    }

    @Override
    public void execute(float deltaTime) {
        if(actor == null) return;

        if(!isFirst){
            isFirst = true;
            actor.startedMoving(direction);
        }

        if(deltaTime >= duration){
            reset();
        }

        duration -= deltaTime;

        if(!isDone()){
            if(time == 0 ){
                actor.startedMoving(direction);
                time += 1;
            }

            if(duration > 0){
                // '-' because, if we have '+', we will be walking into another side
                int x = actor.getPosX() + direction.getDx() * actor.getSpeed();
                int y = actor.getPosY() + direction.getDy() * actor.getSpeed();

                //removed these variables to get rid of the jitter(shake) effect
                //int notX = actor.getPosX() + direction.getX() * actor.getSpeed();
                //int notY = actor.getPosY() - direction.getY() * actor.getSpeed();

                actor.setPosition(x, y);
                assert getActor() != null;
                if ((Objects.requireNonNull(getActor().getScene())).getMap().intersectsWithWall(actor)) {
                    int notX = actor.getPosX() - direction.getDx() * actor.getSpeed();
                    int notY = actor.getPosY() - direction.getDy() * actor.getSpeed();

                    actor.setPosition(notX, notY);
                    actor.collidedWithWall();
                }
            } else{
                stop();
            }
        }

        if(Math.abs(deltaTime - duration) <= 1e-5){
            isDone = true;
            isFirst = true;
            //actor.stoppedMoving();
            stop();
        }
    }

    @Override
    public void reset() {
        actor.stoppedMoving();
        isDone = false;
        isFirst = false;
        time = 0;
        duration = 0;
    }

    public void stop(){
        isDone = false;
        actor.stoppedMoving();
    }

}
