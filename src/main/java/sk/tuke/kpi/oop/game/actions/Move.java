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
        if(actor == null || getActor() == null) return;

        duration -= deltaTime;

        if(Math.abs(deltaTime - duration) <= 1e-5){
            isDone = true;
            isFirst = true;
            stop();
        }


        if(!isDone()){
            if(!isFirst){
                isFirst = true;
                actor.startedMoving(direction);
            }
            if(duration > 0){
                // '-' because, if we have '+', we will be walking into another side
                int x = actor.getPosX() + direction.getDx() * actor.getSpeed();
                int y = actor.getPosY() + direction.getDy() * actor.getSpeed();

                actor.setPosition(x, y);
                assert getActor() != null;
                if(getActor().getScene().getMap().intersectsWithWall(actor)) {
                    int notX = actor.getPosX() - direction.getDx() * actor.getSpeed();
                    int notY = actor.getPosY() - direction.getDy() * actor.getSpeed();

                    actor.setPosition(notX, notY);
                    actor.collidedWithWall();
                }
            } else{
                stop();
            }
        }
    }

    @Override
    public void reset() {
        actor.stoppedMoving();
        isDone = false;
        isFirst = true;
        duration = 0;
    }

    public void stop(){
        if(actor != null){
            isDone = true;
            isFirst = true;

            actor.stoppedMoving();
        }
    }

}
