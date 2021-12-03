package sk.tuke.kpi.oop.game.behaviours;

import sk.tuke.kpi.gamelib.Disposable;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.actions.Move;
import sk.tuke.kpi.oop.game.characters.Ripley;

import java.util.Objects;


public class FollowRipley implements Behaviour<Movable>{
    private Disposable disposable;
    private Move<Movable> move;
    private int posXRipley;
    private int posYRipley;
    private int posXEnemy;
    private int posYEnemy;

    public void followRipley(Movable actor){
        Direction newDirection = null;
        posXRipley = Objects.requireNonNull(Objects.requireNonNull(actor.getScene()).getFirstActorByType(Ripley.class)).getPosX();
        posYRipley = Objects.requireNonNull(actor.getScene().getFirstActorByType(Ripley.class)).getPosY();
        posXEnemy = actor.getPosX();
        posYEnemy = actor.getPosY();


        /* checking enemy's posX and ripley's posX */
        if(posXEnemy != posXRipley){
            if(posXEnemy > posXRipley) posXEnemy--;
            else posXEnemy++;
        }
        /* checking enemy's posY and ripley's posY */
        if(posYEnemy != posYRipley){
            if(posYEnemy > posYRipley) posYEnemy--;
            else posYEnemy++;
        }

        posXEnemy -= posXRipley;
        if(posXEnemy > 0) posXEnemy = -1;
        else if(posXEnemy < 0) posXEnemy = 1;
        else posXEnemy = 0;

        posYEnemy -= posYRipley;
        if(posYEnemy > 0) posYEnemy = -1;
        else if(posYEnemy < 0) posYEnemy = 1;
        else posYEnemy = 0;


        for(Direction direction : Direction.values()){
            if(posXEnemy == direction.getDx() && posYEnemy == direction.getDy()) {
                newDirection = direction;
            }
        }

        if(move != null){
            move.stop();
            disposable.dispose();
            move = null;
        }
        if(newDirection != null){
            actor.getAnimation().setRotation(Objects.requireNonNull(newDirection).getAngle());
            move = new Move<>(newDirection, 2);
            disposable = move.scheduleFor(actor);
        }
    }

    @Override
    public void setUp(Movable actor){
        if(actor != null){
            new Loop<>(
                new ActionSequence<Movable>(
                    new Invoke<>(()->{followRipley(actor);}),
                    new Wait<>(1)
                )
            ).scheduleFor(actor);
        } else return;
    }

}
