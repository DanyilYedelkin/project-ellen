package sk.tuke.kpi.oop.game.visual;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.characters.Ripley;

import java.util.List;
import java.util.Objects;

public class Engine extends AbstractActor {
    public Engine(){
        setAnimation(new Animation("sprites/engine.png",
            96, 64, 0.1f, Animation.PlayMode.LOOP_PINGPONG));
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);

        new Loop<>(
            new Invoke<>(this::damageFire)
        ).scheduleOn(scene);
    }

    private void damageFire(){
        List<Actor> actorList;
        actorList = Objects.requireNonNull(getScene()).getActors();

        for(Actor ellen : actorList){
            if(ellen instanceof Ripley && ellen.intersects(this) && ((Ripley) ellen).getHealth().getValue() > 0){
                ((Ripley) ellen).getHealth().drain(100);
            }
        }
    }
}
