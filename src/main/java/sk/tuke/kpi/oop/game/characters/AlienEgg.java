package sk.tuke.kpi.oop.game.characters;

import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.actions.Fire;
import sk.tuke.kpi.oop.game.behaviours.FollowRipley;
import sk.tuke.kpi.oop.game.visual.BigExplode;
import sk.tuke.kpi.oop.game.weapons.Firearm;
import sk.tuke.kpi.oop.game.weapons.LaserGun;

import java.util.Objects;

public class AlienEgg extends AbstractActor implements Armed, Enemy, Alive {
    private Firearm weapon;
    private Health health;

    public AlienEgg(int health){
        weapon = new LaserGun(300, 300);
        this.health = new Health(health, health);

        setAnimation(new Animation("sprites/alien_egg.png",
            32, 32, 0.1f, Animation.PlayMode.LOOP_PINGPONG));
    }

    private void attack(){
        new Loop<>(
            new ActionSequence<>(
                new Invoke<>(() -> new Fire<>().scheduleFor(this)),
                new Wait<>(3)
            )
        ).scheduleOn(Objects.requireNonNull(getScene()));
    }

    @Override
    public Firearm getFirearm(){
        return weapon;
    }

    @Override
    public void setFirearm(Firearm weapon){
        this.weapon = weapon;
    }
    private void checking(){
        if(health.getValue() == 0){
            Objects.requireNonNull(getScene()).removeActor(this);

            getScene().addActor(new Lurker(50, new FollowRipley()), this.getPosX(), this.getPosY());
        }
    }

    public void activate(){
        new Loop<>(
            new ActionSequence<>(
                new Invoke<>(this::attack),
                new Wait<>(2),
                new Invoke<>(this::checking)
            )
        ).scheduleFor(this);
    }

    @Override
    public Health getHealth() {
        return health;
    }
}


