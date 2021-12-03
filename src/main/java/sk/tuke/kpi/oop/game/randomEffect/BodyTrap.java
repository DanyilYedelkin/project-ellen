package sk.tuke.kpi.oop.game.randomEffect;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.oop.game.behaviours.FollowRipley;
import sk.tuke.kpi.oop.game.behaviours.RandomlyMoving;
import sk.tuke.kpi.oop.game.characters.Lurker;
import sk.tuke.kpi.oop.game.weapons.Bullet;

import java.util.List;
import java.util.Objects;

public class BodyTrap extends Body {
    private boolean repeat = true;

    public BodyTrap(){
        super();
    }

    @Override
    public void breaking(){
        List<Actor> itemList;
        itemList = Objects.requireNonNull(getScene()).getActors();

        for(Actor item : itemList){
            if(item instanceof Bullet && item.intersects(this) && repeat){
                getScene().addActor(new Lurker(50, new FollowRipley()), this.getPosX() + 25, this.getPosY() + 15);

                getScene().removeActor(this);
                getScene().removeActor(item);
                repeat = false;
            }
        }
    }
}
