package sk.tuke.kpi.oop.game.items;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
//import sk.tuke.kpi.oop.game.characters.Alive;
import sk.tuke.kpi.oop.game.characters.Armed;
import sk.tuke.kpi.oop.game.characters.Ripley;
//import sk.tuke.kpi.oop.game.characters.Ripley;

import java.util.List;
import java.util.Objects;


public class Ammo extends AbstractActor implements Usable<Armed>{
    private boolean repeat = true;

    public Ammo(){
        Animation ammo = new Animation("sprites/ammo.png", 16, 16);
        setAnimation(ammo);
    }

    @Override
    public void useWith(Armed armed) {
        /*if(ripley != null && ripley.getAmmo() < 500 && intersects(ripley)){
            ripley.setAmmo(ripley.getAmmo() + 50);

            Objects.requireNonNull(getScene()).removeActor(this);
        }*/

        if(armed != null && armed.getFirearm().getAmmo() < 500){
            armed.getFirearm().reload(50);

            Objects.requireNonNull(getScene()).removeActor(this);
        }

    }

    @Override
    public Class<Armed> getUsingActorClass() {
        return Armed.class;
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        new Loop<>(
            new Invoke<>(this::useAndRemove)
        ).scheduleOn(scene);
    }

    private void useAndRemove(){
        List<Actor> actorList;
        actorList = Objects.requireNonNull(getScene()).getActors();

        for(Actor ellen : actorList){
            if(ellen instanceof Ripley && ellen.intersects(this) && ((Ripley) ellen).getFirearm().getAmmo() < 500 && repeat){
                ((Ripley) ellen).getFirearm().reload(50);

                Objects.requireNonNull(getScene()).removeActor(this);
                repeat = false;
                break;
            }
        }
    }

}
