package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.messages.Topic;
import sk.tuke.kpi.oop.game.characters.Ripley;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TunnelFinish extends AbstractActor {
    public static final Topic<Ripley> RIPLEY_WIN = Topic.create("ripley win", Ripley.class);
    private List<TunnelFinish.ExhaustionEffect> effect;

    public TunnelFinish(){
        effect = new ArrayList<>();

        setAnimation(new Animation("sprites/tunnel_black.png",
            32, 32, 0.1f, Animation.PlayMode.LOOP_PINGPONG));
    }

    public void isIntersects() {
        Ripley ellen = Objects.requireNonNull(getScene()).getFirstActorByType(Ripley.class);
        if(ellen != null && ellen.intersects(this)){
            ellen.getAnimation().stop();
            endGame();
        }
    }

    private void endGame(){
        Ripley ellen = Objects.requireNonNull(getScene()).getFirstActorByType(Ripley.class);
        Objects.requireNonNull(getScene()).getMessageBus().publish(RIPLEY_WIN, ellen);
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);

        new Loop<>(
            new Invoke<>(this::isIntersects)
        ).scheduleOn(scene);
    }

    public boolean isIn(){
        Ripley ellen = Objects.requireNonNull(getScene()).getFirstActorByType(Ripley.class);
        return ellen != null && ellen.intersects(this);
    }

    @FunctionalInterface
    public interface ExhaustionEffect {
        void apply();
    }

    public void isOn(TunnelFinish.ExhaustionEffect effect){
        if(this.effect != null && effect != null){
            this.effect.add(effect);
        }
    }
}
