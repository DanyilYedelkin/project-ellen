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

package sk.tuke.kpi.oop.game;
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
            16, 16, time/6, Animation.PlayMode.LOOP_PINGPONG);
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
                new Invoke<>(() -> explode())
                //new Wait<>(1),
                //new Invoke<>(() -> this.getScene().removeActor(this))
            ).scheduleFor(this);

        }
    }

    public void explode(){
        this.isExploded = true;
        setAnimation(bombBoom);
        new When<>(
            ()->{
                return getAnimation().getCurrentFrameIndex() >= 7;  //когда анимация взрыва бомбы на последнем кадре
            },
            new Invoke<>(() -> {
                Objects.requireNonNull(getScene()).removeActor(this); //переключается на удаление, иначе анимация взрыва проходит в 0.000001 секунды
            })
        ).scheduleFor(this);
    }

    public boolean isActivated(){
        return isActivated;
    }

    public boolean isExploded() {
        return isExploded;
    }
}
