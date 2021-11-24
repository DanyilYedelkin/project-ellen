package sk.tuke.kpi.oop.game.behaviours;

import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.actions.Move;

import java.util.Objects;


public class RandomlyMoving implements Behaviour<Movable>{

    @Override
    public void setUp(Movable actor){
        if(actor != null){
            new Loop<>(
                new ActionSequence<>(
                    new Invoke<>(this::randomMoving),
                    new Wait<>(2)
                )
            ).scheduleFor(actor);
        }
    }

    private void randomMoving(Movable actor){
        Direction newDirection = null;
        int checking = (int)(Math.random() * (3)) - 1;
        int checking2 = (int)(Math.random() * (3)) - 1;

        for(Direction direction : Direction.values()){
            if(checking == direction.getDx() && checking2 == direction.getDy()){
                newDirection = direction;
            }
        }

        actor.getAnimation().setRotation(Objects.requireNonNull(newDirection).getAngle());
        new Move<>(newDirection, 2).scheduleFor(actor);
    }
}
