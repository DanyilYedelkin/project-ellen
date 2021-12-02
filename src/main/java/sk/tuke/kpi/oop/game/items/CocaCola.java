package sk.tuke.kpi.oop.game.items;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.characters.Ripley;

import java.util.List;
import java.util.Objects;

public class CocaCola extends AbstractActor implements Usable <Ripley>{
    private boolean repeat = true;

    public CocaCola(){
        setAnimation(new Animation("sprites/coca_cola.png", 6, 23));
    }

    @Override
    public void useWith(Ripley actor) {
        if(actor == null) return;

        actor.getHealth().restore();
        if(actor.getHealth().getValue() == 100){
            Objects.requireNonNull(getScene()).removeActor(this);
        }
        new ActionSequence<Ripley>(
            new Invoke<>(() -> actor.setSpeed(4)),
            new Wait<>(5),
            new Invoke<>(() -> actor.setSpeed(2))
        ).scheduleFor(actor);
    }

    @Override
    public Class<Ripley> getUsingActorClass() {
        return Ripley.class;
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
            if(ellen instanceof Ripley && ellen.intersects(this) && repeat){
                if(((Ripley) ellen).getHealth().getValue() != 100){
                    ((Ripley) ellen).getHealth().restore();

                    new ActionSequence<>(
                        new Invoke<>(() -> ((Ripley) ellen).setSpeed(4)),
                        new Wait<>(5),
                        new Invoke<>(() -> ((Ripley) ellen).setSpeed(2))
                    ).scheduleFor(ellen);

                    Objects.requireNonNull(getScene()).removeActor(this);
                    repeat = false;
                }
            }
        }
    }
}
