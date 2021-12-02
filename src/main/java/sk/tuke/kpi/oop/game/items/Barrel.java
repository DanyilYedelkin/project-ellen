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
import sk.tuke.kpi.oop.game.weapons.Bullet;

import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Barrel extends AbstractActor {

    public Barrel(){
        setAnimation(new Animation("sprites/barrel.png", 16, 16));
    }

    public void rozbiSa() {
        List<Actor> nastrojeList;
        nastrojeList = Objects.requireNonNull(getScene()).getActors();

        for (Actor nastroj : nastrojeList) {
            if(nastroj instanceof Hammer && nastroj.intersects(this)){
                Random random = new Random();
                int randomNumber = random.nextInt(3);
                if(randomNumber == 0){
                    getScene().addActor(new AccessCard(), this.getPosX(), this.getPosY());
                } else if(randomNumber == 1){
                    getScene().addActor(new Ammo(), this.getPosX(), this.getPosY());
                } else{
                    getScene().addActor(new Energy(), this.getPosX(), this.getPosY());
                }

                getScene().removeActor(this);
                getScene().removeActor(nastroj);
            }
            if(nastroj instanceof Bullet && nastroj.intersects(this)){
                setAnimation(new Animation("sprites/large_explosion.png",
                    32, 32, 0.1f, Animation.PlayMode.LOOP_PINGPONG));

                float time = 1.6f;
                new ActionSequence<>(
                    new Wait<>(time),   //wait some seconds, before it explodes
                    new Invoke<>(() -> {    //removing the bomb from the scene
                        Objects.requireNonNull(getScene()).removeActor(this); //переключается на удаление, иначе анимация взрыва проходит в 0.000001 секунды
                        getScene().removeActor(nastroj);
                    })
                ).scheduleFor(this);

            }
        }
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        new Loop<>(
            new Invoke<>(this::rozbiSa)
        ).scheduleOn(scene);
    }

}
