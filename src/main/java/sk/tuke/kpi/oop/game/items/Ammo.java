package sk.tuke.kpi.oop.game.items;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.characters.Ripley;

import java.util.Objects;


public class Ammo extends AbstractActor implements Usable<Ripley>{
    private Animation ammo;

    public Ammo(){
        ammo = new Animation("sprites/ammo.png", 16, 16);
        setAnimation(ammo);
    }

    @Override
    public void useWith(Ripley ripley) {
        if (ripley != null && ripley.getAmmo() < 500 && intersects(ripley)){
            ripley.setAmmo(ripley.getAmmo() + 50);
            Objects.requireNonNull(getScene()).removeActor(this);
        }
    }

    @Override
    public Class<Ripley> getUsingActorClass() {
        return Ripley.class;
    }

}
