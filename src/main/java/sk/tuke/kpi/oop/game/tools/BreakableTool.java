package sk.tuke.kpi.oop.game.tools;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.AbstractActor;

public abstract class BreakableTool<A extends Actor> extends AbstractActor implements Usable<A>{
    private int remainingUses;
    private Scene scene;

    public BreakableTool(int remainingUses) {
        this.remainingUses = remainingUses;
    }

    /* make method "useWith()" for all tools, witch will represent the tool usage */
    @Override
    public void useWith(A actor){
        if(this.remainingUses > 0) {
            this.remainingUses -= 1;
        }
        if(this.remainingUses == 0) {
            this.removedFromScene(scene);
            this.getScene().removeActor(this);
        }
    }

    /* a method, which returns a variable "remainingUses" */
    public int getRemainingUses(){
        return remainingUses;
    }

    public void setRemainingUses(int remainingUses){
        this.remainingUses = remainingUses;
    }
}
