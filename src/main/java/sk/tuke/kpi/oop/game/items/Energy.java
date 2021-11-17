package sk.tuke.kpi.oop.game.items;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.characters.Ripley;

import java.util.Objects;


public class Energy extends AbstractActor implements Usable<Ripley>{
    private Animation energy;

    public Energy(){
        energy = new Animation("sprites/energy.png", 16, 16);
        setAnimation(energy);
    }
    @Override
    public void useWith(Ripley ripley){
        if(ripley != null && ripley.getEnergy() != 100 && intersects(ripley)){
            ripley.setEnergy(100);

            (Objects.requireNonNull(getScene())).removeActor(this);
        }
    }

    @Override
    public Class<Ripley> getUsingActorClass() {
        return Ripley.class;
    }
}
