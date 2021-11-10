package sk.tuke.kpi.oop.game.actions;

import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.actions.Action;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Movable;

public class Move<A extends Movable> implements Action<A> {
    private Direction direction;
    private A actor;
    private float duration;
    private boolean isDone;
    private boolean isFirst;

    public Move(Direction direction, float duration) {
        // implementation of actor's constructor
        isDone = false;
        isFirst = false;
        this.direction = direction;
        this.duration = duration;
    }
    public Move(Direction direction) {
        // implementation of actor's constructor
        isDone = false;
        isFirst = false;
        this.direction = direction;
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

        // '-' because, if we have '+', we will be walking into another side
        int x = actor.getPosX() - direction.getX() * actor.getSpeed();
        int y = actor.getPosY() + direction.getY() * actor.getSpeed();

        actor.setPosition(x, y);
        duration -= deltaTime;

        if(Math.abs(deltaTime - duration) <= 1e-5){
            isDone = true;
            isFirst = true;
            actor.stoppedMoving();
        }
    }

    @Override
    public void reset() {
        actor.stoppedMoving();
        isDone = false;
        isFirst = false;
        duration = 0;
    }

    public void stop(){
        isDone = false;
        actor.stoppedMoving();
    }
}
