package sk.tuke.kpi.oop.game.items;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.openables.LockedDoor;

import java.util.List;
import java.util.Objects;

public class AccessCard extends AbstractActor implements Collectible, Usable<LockedDoor>{

    public AccessCard(){
        Animation accessCardAnimation = new Animation("sprites/key.png", 16, 16);

        setAnimation(accessCardAnimation);
    }

    @Override
    public void useWith(LockedDoor actor){
        actor.unlock();

        List<Actor> actorList;
        actorList = Objects.requireNonNull(getScene()).getActors();

        for(Actor ellen : actorList){
            if(ellen instanceof Ripley){
                ((Ripley) ellen).getBackpack().remove(this);
            }
        }
    }

    @Override
    public Class<LockedDoor> getUsingActorClass(){
        return LockedDoor.class;
    }

}
