package sk.tuke.kpi.oop.game.items;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.openables.LockedDoor;

public class AccessCard extends AbstractActor implements Collectible, Usable<LockedDoor>{

    public AccessCard(){
        Animation accessCardAnimation = new Animation("sprites/key.png", 16, 16);

        setAnimation(accessCardAnimation);
    }

    @Override
    public void useWith(LockedDoor actor){
        actor.unlock();
    }

    @Override
    public Class<LockedDoor> getUsingActorClass(){
        return LockedDoor.class;
    }

}
