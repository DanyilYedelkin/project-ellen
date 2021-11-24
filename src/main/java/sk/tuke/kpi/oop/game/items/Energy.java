package sk.tuke.kpi.oop.game.items;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.characters.Alive;
//import sk.tuke.kpi.oop.game.characters.Ripley;

import java.util.Objects;


public class Energy extends AbstractActor implements Usable<Alive>{

    public Energy(){
        Animation energy = new Animation("sprites/energy.png", 16, 16);
        setAnimation(energy);
    }
    @Override
    public void useWith(Alive alive){
        if(alive != null && alive.getHealth().getValue() != 100){
            alive.getHealth().refill(100);
            if(intersects(alive)){
                alive.getHealth().restore();
            }

            (Objects.requireNonNull(getScene())).removeActor(this);
        }
    }

    @Override
    public Class<Alive> getUsingActorClass() {
        return Alive.class;
    }
}
