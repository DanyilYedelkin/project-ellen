package sk.tuke.kpi.oop.game.items;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.characters.Alive;
import sk.tuke.kpi.oop.game.characters.Ripley;

import java.util.List;
import java.util.Objects;

public class BulletproofVest extends AbstractActor implements Usable<Alive> {
    private boolean repeat = true;

    public BulletproofVest(){
        setAnimation(new Animation("sprites/bulletproof_vest.png", 24, 26));
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
            if(ellen instanceof Ripley && ellen.intersects(this) && repeat && ((Ripley) ellen).getArmor().getValue() != 100){
                ((Ripley) ellen).getArmor().refill(20);

                (Objects.requireNonNull(getScene())).removeActor(this);
                repeat = false;
            }
        }

    }
}
