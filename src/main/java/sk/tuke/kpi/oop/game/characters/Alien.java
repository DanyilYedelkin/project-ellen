package sk.tuke.kpi.oop.game.characters;

//add libraries
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
import sk.tuke.kpi.oop.game.market.Money;

//import java.util.List;
import java.util.Objects;

//create public class Alien (is enemy)
public class Alien extends AbstractActor implements Movable, Alive, Enemy {
    //create private variables
    private Animation alienAnimation;   //for animations
    private Health health;              //for health
    private Behaviour<? super Alien> behaviour; //for behaviour
    private Disposable attackHero;      //for attack tha main character
    private int speed;                  //for speed walking


    //default Alien()
    public Alien(){
        //set default parameters
        speed = 4;
        alienAnimation = new Animation("sprites/alien.png", 32, 32,
            0.1f, Animation.PlayMode.LOOP_PINGPONG);
        health = new Health(100, 100);

        health.onExhaustion(() -> Objects.requireNonNull(getScene()).removeActor(this));
        //set the animation
        setAnimation(alienAnimation);
    }
    //Alien with opportunity to set health points and his type of moving (behaviour)
    public Alien(int healthValue, Behaviour<? super Alien> behaviour){
        //set default parameters
        speed = 4;
        alienAnimation = new Animation("sprites/alien.png", 32, 32,
            0.1f, Animation.PlayMode.LOOP_PINGPONG);
        health = new Health(healthValue, healthValue);
        this.behaviour = behaviour;

        health.onExhaustion(() -> Objects.requireNonNull(getScene()).removeActor(this));
        //set the animation
        setAnimation(alienAnimation);
    }


    //a method returns alien's speed
    @Override
    public int getSpeed() {
        return speed;
    }

    //a method returns alien's health points
    @Override
    public Health getHealth() {
        return health;
    }

    //a method, which adds functions of alien into the scene
    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);

        if(behaviour != null){
            behaviour.setUp(this);
        }

        attackHero = new Loop<>(
            new ActionSequence<>(
                new Wait<>(0.5f),
                new Invoke<>(this::attack)
            )
        ).scheduleFor(this);

        new Loop<>(
            new ActionSequence<>(
                new Invoke<>(this::replace)
            )
        ).scheduleFor(this);
    }

    //a method for dropping out access card from the Alien's Mother body, after her die
    private void replace(){
        if(this instanceof AlienMother && this.getHealth().getValue() == 0){
            Objects.requireNonNull(getScene()).addActor(new Money(), this.getPosX() + 35, this.getPosY() + 35);
        }
    }

    //a method for restarting alien's attack
    private void restartAttack() {
        attackHero = null;
    }

    //a method for alien's attack
    private void attack(){
        //the cycle for finding an ellen (the main character) to destroy him
        for(Actor hero : Objects.requireNonNull(getScene()).getActors()){
            if(!(hero instanceof Enemy) && hero instanceof Alive && intersects(hero)){
                //set the damage
                int damage = 10;

                /*for(int i = 1; i <= damage; i++, damage--){
                    if(((Ripley) hero).getArmor().getValue() <= 0) break;

                    ((Ripley) hero).getArmor().drain(1);
                }*/
                //drain the armor hp, and if it will be 0, then alien will drain the ellen's health points
                if(((Ripley) hero).getArmor().getValue() > 0){
                    ((Ripley) hero).getArmor().drain(damage);
                } else if(((Ripley) hero).getHealth().getValue() > 0){
                    ((Ripley) hero).getHealth().drain(damage);
                }

                //for attacking
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
