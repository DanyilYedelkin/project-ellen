package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.items.Hammer;
import sk.tuke.kpi.oop.game.items.Usable;

import java.util.Objects;

public class Locker extends AbstractActor implements Usable<Ripley> {
    private Animation lockerAnimation;
    private boolean isUsed;

    public Locker(){
        lockerAnimation = new Animation("sprites/locker.png", 16, 16);

        setAnimation(lockerAnimation);
    }

    @Override
    public void useWith(Ripley actor) {
        if(!isUsed()){
            isUsed = true;
            Objects.requireNonNull(getScene()).addActor(new Hammer(),getPosX(),getPosY());
        }
    }

    @Override
    public Class<Ripley> getUsingActorClass() {
        return Ripley.class;
    }

    private boolean isUsed(){
        return isUsed;
    }
}
