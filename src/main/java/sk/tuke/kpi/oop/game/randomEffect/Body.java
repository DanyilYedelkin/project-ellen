package sk.tuke.kpi.oop.game.randomEffect;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.items.AccessCard;
import sk.tuke.kpi.oop.game.items.Ammo;
import sk.tuke.kpi.oop.game.items.Energy;
import sk.tuke.kpi.oop.game.weapons.Bullet;

import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Body extends AbstractActor {
    private boolean repeat = true;

    public Body(){
        setAnimation(new Animation("sprites/body.png", 64, 48));
    }

    public void breaking() {
        List<Actor> itemList;
        itemList = Objects.requireNonNull(getScene()).getActors();

        for(Actor item : itemList){
            if(item instanceof Bullet && item.intersects(this) && repeat){
                Random random = new Random();
                int randomNumber = random.nextInt(3);
                if(randomNumber == 0){
                    getScene().addActor(new AccessCard(), this.getPosX() + 25, this.getPosY() + 15);
                } else if(randomNumber == 1){
                    getScene().addActor(new Ammo(), this.getPosX() + 25, this.getPosY() + 15);
                } else{
                    getScene().addActor(new Energy(), this.getPosX() + 25, this.getPosY() + 15);
                }

                getScene().removeActor(this);
                getScene().removeActor(item);

                repeat = false;
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
