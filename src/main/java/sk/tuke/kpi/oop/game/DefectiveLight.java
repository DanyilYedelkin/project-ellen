package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.Disposable;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
//import sk.tuke.kpi.oop.game.tools.BreakableTool;
//import sk.tuke.kpi.oop.game.tools.Wrench;

//import javax.swing.*;


public class DefectiveLight extends Light implements Repairable{
    private boolean isRunning;
    private double randomNumber;
    private boolean isPowered;
    private Animation lightOffAnimation;
    private Animation lightOnAnimation;

    /*public DefectiveLight(){
        isRunning = false;
    }

    public void defectiveAct(){
        if(isRunning == true){
            randomNumber = Math.random() * 20;
            if(randomNumber < 3){
                this.toggle();
            }
        } else if(this.isPowered == true){
            isRunning = true;
        }
    }

    @Override
    public void addedToScene(Scene scene) {
        super.addedToScene(scene);

        new Loop<>(new Invoke<>(this::defectiveAct)).scheduleFor(this);
    }

    @Override
    public boolean repair() {

        new When<>(
            ()->{
                new ActionSequence<>(
                    new Wait<>(5),
                    new Invoke<>(this::turnOn)
                ).scheduleFor(this);

                return true;
            },

        ).scheduleFor(this);

    }*/

    private Disposable blinking;
    private boolean isBroken;

    public DefectiveLight(){
        super();
        this.isRunning = false;
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

    private void randomOutage(){
        updateAnimation();
        if(isPowered){
            int randomNumber = (int)(Math.random() * 20);
            if(randomNumber < 3) toggle();
            this.isRunning = true;
        }
    }

    private void updateLoop(){
        this.blinking = new Loop<>(new Invoke<>(this::randomOutage)).scheduleFor(this);
        this.isBroken = true;
    }

    /*public void repairLight(Wrench wrench){
        //if(tool instanceof Wrench){
            wrench.useWith(this);
            repair();
        //}
    }*/

    @Override
    public boolean repair(){
        if (this.blinking == null || !this.isBroken) return false;
        this.isBroken = false;
        this.blinking.dispose();
        //setAnimation(lightOnAnimation);

        new ActionSequence<>(
            new Wait<>(10),
            new Invoke<>(this::updateLoop)
        ).scheduleFor(this);

        turnOn();
        return !this.isBroken;
    }

    public boolean isBroken(){
        return this.isBroken;
    }

    @Override
    public void addedToScene(Scene scene){
        super.addedToScene(scene);

        this.blinking = new Loop<>(new Invoke<>(this::randomOutage)).scheduleFor(this);
    }
}
