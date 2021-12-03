package sk.tuke.kpi.oop.game.items;

//add libraries
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.AbstractActor;

import java.util.Objects;

public abstract class BreakableTool<A extends Actor> extends AbstractActor implements Usable<A>{
    //crating private variables
    private int remainingUses;  //a tools' health points
    private Scene scene;

    public BreakableTool(int remainingUses) {
        this.remainingUses = remainingUses;
    }

    /* make method "useWith()" for all tools, witch will represent the tool usage */
    @Override
    public void useWith(A actor){
        if(this.remainingUses > 0) { //if health points is > 0, we will decrease it by 1 point
            this.remainingUses -= 1;
        }
        if(this.remainingUses == 0) { //if health points = 0, we will remove this tool from the scene
            Objects.requireNonNull(this.getScene()).removeActor(this);
            this.removedFromScene(scene);
        }
    }

    /* a method, which returns a variable "remainingUses" */
    public int getRemainingUses(){
        return remainingUses;
    }

    /* a method, which replaces a variable "remainingUses" */
    public void setRemainingUses(int remainingUses){
        this.remainingUses = remainingUses;
    }
}
