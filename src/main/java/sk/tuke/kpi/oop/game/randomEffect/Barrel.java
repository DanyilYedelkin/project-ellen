package sk.tuke.kpi.oop.game.randomEffect;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.items.*;
import sk.tuke.kpi.oop.game.weapons.Bullet;

import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Barrel extends AbstractActor {

    public Barrel(){
        setAnimation(new Animation("sprites/barrel.png", 16, 16));
    }

    public void breaking(){
        int repeat = 1;
        List<Actor> itemList;
        itemList = Objects.requireNonNull(getScene()).getActors();

        for(Actor item : itemList){
            if(item instanceof Hammer && item.intersects(this) && repeat == 1){
                Random random = new Random();
                int randomNumber = random.nextInt(4);
                if(randomNumber == 0){
                    getScene().addActor(new AccessCard(), this.getPosX(), this.getPosY());
                } else if(randomNumber == 1){
                    getScene().addActor(new Ammo(), this.getPosX(), this.getPosY());
                } else if(randomNumber == 2){
                    getScene().addActor(new Energy(), this.getPosX(), this.getPosY());
                } else{
                    getScene().addActor(new BulletproofVest(), this.getPosX(), this.getPosY());
                }

                getScene().removeActor(this);
                getScene().removeActor(item);
                repeat--;
            }
            if(item instanceof Bullet && item.intersects(this) && repeat == 1){
                setAnimation(new Animation("sprites/large_explosion.png",
                    32, 32, 0.1f, Animation.PlayMode.LOOP_PINGPONG));

                float time = 1.6f;
                new ActionSequence<>(
                    new Wait<>(time),   //wait some seconds, before it explodes
                    new Invoke<>(() -> {    //removing the bomb from the scene
                        Objects.requireNonNull(getScene()).removeActor(this); //переключается на удаление, иначе анимация взрыва проходит в 0.000001 секунды
                        getScene().removeActor(item);
                    })
                ).scheduleFor(this);
                repeat--;
            }
        }
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        new Loop<>(
            new Invoke<>(this::breaking)
        ).scheduleOn(scene);
    }

}
