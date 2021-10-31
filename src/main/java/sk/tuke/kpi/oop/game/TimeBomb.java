/*package sk.tuke.kpi.oop.game;

//import sk.tuke.kpi.gamelib.Scene;
//import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
//import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class TimeBomb extends AbstractActor {
    private float time;
    private boolean isActivated;
    //private Scene scene;

    //private Animation bombAnimation;
    private Animation bombActAnimation;
    private Animation bombBoom;

    public TimeBomb(float time){
        this.time = time;
        isActivated = false;

        //bombAnimation = new Animation("sprites/bomb.png", 16, 16);
        bombActAnimation = new Animation("sprites/bomb_activated.png",
            16, 16, 0.125f, Animation.PlayMode.LOOP_PINGPONG);
        bombBoom = new Animation("sprites/small_explosion.png",
            16, 16, 0.125f, Animation.PlayMode.LOOP_PINGPONG);
        //setAnimation(bombAnimation);
        setAnimation(new Animation("sprites/bomb.png", 16, 16));
    }

    public void activate(){
        isActivated = true;
        setAnimation(bombActAnimation);
        //new Loop<>(new Invoke<>(this::bombActivate)).scheduleFor(this);
        bombActivate();
    }

    private void bombActivate(){
        if(isActivated){
            //time--;

            new ActionSequence<>(
                new Wait<>(time),
                new Invoke<>(() -> setAnimation(bombBoom)),

                new Wait<>(1),
                new Invoke<>(() -> this.getScene().removeActor(this))
            ).scheduleFor(this);


            //if(time <= 0) setAnimation(bombBoom);
            //if(time == -90){
                //this.isActivated = false;
                //this.getScene().removeActor(this);
                //Objects.requireNonNull(this.getScene()).removeActor(this);
                //this.getScene().removeActor(this);
            //}
        //}
    //}

    //public boolean isActivated(){
        //return isActivated;
    //}

    /*@Override
    public void addedToScene(Scene scene){
        super.addedToScene(scene);

        new Loop<>(new Invoke<>(this::bombActivate)).scheduleFor(this);
    }*/
//}


/*package sk.tuke.kpi.oop.game;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.actions.When;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

import java.util.Objects;

public class TimeBomb extends AbstractActor {
    private float time;
    private boolean isActivated;
    private boolean isExploded;
    private Animation bombActAnimation;
    private Animation bombBoom;

    public TimeBomb(float time){
        this.time = time;
        isActivated = false;
        isExploded = false;

        bombActAnimation = new Animation("sprites/bomb_activated.png",
            16, 16, 0.125f, Animation.PlayMode.LOOP_PINGPONG);
        bombBoom = new Animation("sprites/small_explosion.png",
            16, 16, 0.125f, Animation.PlayMode.LOOP_PINGPONG);
        setAnimation(new Animation("sprites/bomb.png", 16, 16));
    }

    public void activate(){
        isActivated = true;
        setAnimation(bombActAnimation);
        bombActivate();
    }

    private void bombActivate(){
        if(isActivated){
            new ActionSequence<>(
                new Wait<>(time),
                new Invoke<>(() -> setAnimation(bombBoom)),

                // the reaction of the chain's bomb explode
                new Invoke<>(this::setExploded),

                new Wait<>(1),
                new Invoke<>(() -> this.getScene().removeActor(this))
            ).scheduleFor(this);
        }
    }

    public void setExploded(){
        this.isExploded = true;
    }

    public boolean isActivated(){
        return isActivated;
    }

    public boolean isExploded() {
        return isExploded;
    }
}*/

// (the code is not perfect, later, it will be modified and improved)

//a new version of the code
package sk.tuke.kpi.oop.game;

//add libraries
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.actions.When;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

import java.util.Objects;

public class TimeBomb extends AbstractActor {
    //add private variables
    private float time;             //bomb's time before it will explode
    private boolean isActivated;    //checks if it's activated
    private boolean isExploded;     //checks if it's exploded

    //add private animations
    private Animation bombActAnimation;
    private Animation bombBoom;

    public TimeBomb(float time){
        this.time = time;
        isActivated = false;    //default
        isExploded = false;     //default

        //set bomb's animations
        bombActAnimation = new Animation("sprites/bomb_activated.png",
            16, 16, time/6, Animation.PlayMode.LOOP_PINGPONG);  //if it's activated
        bombBoom = new Animation("sprites/small_explosion.png",
            16, 16, 0.125f, Animation.PlayMode.LOOP_PINGPONG);  //if it's exploded

        setAnimation(new Animation("sprites/bomb.png", 16, 16));    //default animation
    }

    //a method, which activate bombs
    public void activate(){
        isActivated = true;
        setAnimation(bombActAnimation);
        bombActivate();
    }

    //a method, which activate all bombs and check all situations with it
    private void bombActivate(){
        if(isActivated){
            new ActionSequence<>(
                new Wait<>(time),   //wait some seconds, before it explodes
                new Invoke<>(this::explode)     //go to method "explode()"

                //the old code version (some problems in the arena, but code is working
                //new Wait<>(1),
                //new Invoke<>(() -> this.getScene().removeActor(this))
            ).scheduleFor(this);
        }
    }

    //a method, which is about explode (removing an object from the scene, after the last frame)
    public void explode(){
        this.isExploded = true;
        setAnimation(bombBoom);
        new When<>(
            ()->{
                return getAnimation().getCurrentFrameIndex() >= 7;  //when the animation in the last frame
            },
            new Invoke<>(() -> {    //removing the bomb from the scene
                Objects.requireNonNull(getScene()).removeActor(this); //переключается на удаление, иначе анимация взрыва проходит в 0.000001 секунды
            })
        ).scheduleFor(this);
    }

    // a method, which returns if the bomb is activated
    public boolean isActivated(){ return isActivated; }

    // a method, which returns if the bomb is exploded
    public boolean isExploded() {
        return isExploded;
    }
}
