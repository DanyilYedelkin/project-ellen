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
import sk.tuke.kpi.oop.game.items.AccessCard;
import sk.tuke.kpi.oop.game.market.Money;

import java.util.Objects;


public class Lurker extends AbstractActor implements Movable, Alive, Enemy {
    private Animation lurkerAnimation;
    private Animation bornAnimation;
    private int speed;
    private Health health;
    private Behaviour<? super Lurker> behaviour;
    private Disposable attackHero;
    private Disposable stealAmmo;
    private boolean born;


    public Lurker(int healthValue, Behaviour<? super Lurker> behaviour){
        speed = 1;
        born = true;
        bornAnimation = new Animation("sprites/lurker_born.png",
            32, 32, 0.5f, Animation.PlayMode.LOOP_PINGPONG);
        lurkerAnimation = new Animation("sprites/lurker_alien.png",
            32, 32, 0.1f, Animation.PlayMode.LOOP_PINGPONG);

        health = new Health(healthValue, healthValue);
        this.behaviour = behaviour;

        health.onExhaustion(() -> Objects.requireNonNull(getScene()).removeActor(this));
        setAnimation(bornAnimation);
    }

    @Override
    public int getSpeed() {
        return speed;
    }

    @Override
    public Health getHealth() {
        return health;
    }

    private boolean isBorn(){
        return born;
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);

        if(isBorn()){
            born = false;

            new ActionSequence<>(
                new Wait<>(2),
                new Invoke<>(this::born),
                new Wait<>(4)
            ).scheduleFor(this);
        }

        if(behaviour != null && !isBorn()){
            behaviour.setUp(this);
        }

        attackHero = new Loop<>(
            new ActionSequence<>(
                new Wait<>(0.5f),
                new Invoke<>(this::attack)
            )
        ).scheduleFor(this);

        stealAmmo = new Loop<>(
            new ActionSequence<>(
                new Wait<>(0.3f),
                new Invoke<>(this::steal)
            )
        ).scheduleFor(this);

        new Loop<>(
            new ActionSequence<>(
                new Invoke<>(this::replace)
            )
        ).scheduleFor(this);
    }

    private void replace(){
        if(this.getHealth().getValue() == 0){
            Objects.requireNonNull(getScene()).addActor(new AccessCard(), this.getPosX(), this.getPosY());
        }
    }

    private void born(){
        setAnimation(lurkerAnimation);
    }

    private void restartSteal(){
        stealAmmo = null;
    }

    private void steal(){
        for(Actor hero : Objects.requireNonNull(getScene()).getActors()){
            if(!(hero instanceof Enemy) && hero instanceof Ripley && intersects(hero)){
                ((Ripley) hero).getFirearm().stealAmmo(5);

                if(attackHero == null){
                    new ActionSequence<>(
                        new Invoke<>(this::restartSteal),
                        new Wait<>(2),
                        new Invoke<>(this::steal)
                    ).scheduleFor(this);
                }

            }
        }
    }

    private void attack(){
        for(Actor hero : Objects.requireNonNull(getScene()).getActors()){
            if(!(hero instanceof Enemy) && hero instanceof Alive && intersects(hero)){
                int damage = 5;

                /*for(int i = 0; i < damage; i++, damage--){
                    if(((Ripley) hero).getArmor().getValue() <= 0) break;

                    ((Ripley) hero).getArmor().drain(1);
                }*/
                if(((Ripley) hero).getArmor().getValue() > 0){
                    ((Ripley) hero).getArmor().drain(damage);
                } else{
                    ((Ripley) hero).getHealth().drain(damage);
                }

                if(attackHero == null){
                    new ActionSequence<>(
                        new Invoke<>(this::restartAttack),
                        new Wait<>(2),
                        new Invoke<>(this::attack)
                    ).scheduleFor(this);
                }

            }
        }
    }

    private void restartAttack() {
        attackHero = null;
    }

}
