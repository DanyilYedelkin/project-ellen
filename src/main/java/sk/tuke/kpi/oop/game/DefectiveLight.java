package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.actions.Loop;


public class DefectiveLight extends Light {
    private boolean isRunning;
    private double randomNumber;

    public DefectiveLight(){
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

        new Invoke<>(this::defectiveAct).scheduleFor(this);
        new Loop<>(new Invoke<>(this::defectiveAct)).scheduleFor(this);
    }

}
