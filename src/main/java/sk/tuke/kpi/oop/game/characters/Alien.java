package sk.tuke.kpi.oop.game.characters;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Disposable;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.behaviours.Behaviour;

//import java.util.List;
import java.util.Objects;


public class Alien extends AbstractActor implements Movable, Alive, Enemy {
    private Animation alienAnimation;
    private Health health;
    private Behaviour<? super Alien> behaviour;
    private Disposable attackHero;
    private int speed;


    public Alien(){
        speed = 4;
        alienAnimation = new Animation("sprites/alien.png", 32, 32,
            0.1f, Animation.PlayMode.LOOP_PINGPONG);
        health = new Health(100, 100);

        health.onExhaustion(() -> Objects.requireNonNull(getScene()).removeActor(this));
        setAnimation(alienAnimation);
    }
    public Alien(int healthValue, Behaviour<? super Alien> behaviour){
        speed = 4;
        alienAnimation = new Animation("sprites/alien.png", 32, 32,
            0.1f, Animation.PlayMode.LOOP_PINGPONG);
        health = new Health(healthValue, healthValue);
        this.behaviour = behaviour;

        health.onExhaustion(() -> Objects.requireNonNull(getScene()).removeActor(this));
        setAnimation(alienAnimation);
    }



    @Override
    public int getSpeed() {
        return Math.max(speed, 0);
    }

    @Override
    public Health getHealth() {
        return health;
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);

        if(behaviour != null){
            behaviour.setUp(this);
        }

        attackHero = new Loop<>(
            new ActionSequence<>(
                new Invoke<>(this::attack),
                new Wait<>(0.5f)
            )
        ).scheduleFor(this);
    }

    public void restartAttack() {
        attackHero = null;
    }

    public void attack(){
        for(Actor hero : Objects.requireNonNull(getScene()).getActors()){
            if(!(hero instanceof Enemy) && hero instanceof Alive && intersects(hero)){
                ((Alive) hero).getHealth().drain(10);

                if(attackHero == null){
                    new ActionSequence<>(
                        new Invoke<>(this::restartAttack),
                        new Wait<>(1.5f),
                        new Invoke<>(this::attack)
                    ).scheduleFor(this);
                }
            }
        }
    }

}
