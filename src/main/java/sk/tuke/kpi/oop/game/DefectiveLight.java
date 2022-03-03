package sk.tuke.kpi.oop.game;

//add libraries
import sk.tuke.kpi.gamelib.Disposable;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;



public class DefectiveLight extends Light implements Repairable{
    private boolean isPowered;
    private Animation lightOffAnimation;
    private Animation lightOnAnimation;
    private Disposable blinking;
    private boolean isBroken;

    public DefectiveLight(){
        super();
        this.isBroken = true;

        lightOnAnimation = new Animation("sprites/light_on.png", 16, 16, 0.1f);
        lightOffAnimation = new Animation("sprites/light_off.png", 16, 16, 0.1f);
        // set actor's animation to just created Animation object
        setAnimation(lightOffAnimation);
    }

    /* a method will specify whether electricity is provided to light or not */
    public void setPowered(boolean electricity) {
        this.isPowered = electricity;
        updateAnimation();
    }
    /* a method, which update light's animation */
    private void updateAnimation() {
        if (this.isPowered) {
            setAnimation(lightOnAnimation);
        } else{
            setAnimation(lightOffAnimation);
        }
    }

    // a method with which we will have a random number, for blinking
    private void randomOutage(){
        updateAnimation();
        if(isPowered){
            int randomNumber = (int)(Math.random() * 20);
            if(randomNumber < 3) toggle();
            //this.isRunning = true;
        }
    }

    private void updateLoop(){
        this.blinking = new Loop<>(new Invoke<>(this::randomOutage)).scheduleFor(this);
        this.isBroken = true;
    }

    @Override
    public boolean repair(){
        if (this.blinking == null || !this.isBroken) return false;
        this.isBroken = false;
        this.blinking.dispose();

        new ActionSequence<>(
            new Wait<>(10),
            new Invoke<>(this::updateLoop)
        ).scheduleFor(this);

        turnOn();
        return !this.isBroken;
    }

    //a method, which will returns if defective light is broken
    public boolean isBroken(){
        return this.isBroken;
    }

    @Override
    public void addedToScene(Scene scene){
        super.addedToScene(scene);

        this.blinking = new Loop<>(new Invoke<>(this::randomOutage)).scheduleFor(this);
    }
}
