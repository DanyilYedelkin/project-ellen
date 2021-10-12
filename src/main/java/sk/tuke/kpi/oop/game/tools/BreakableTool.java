package sk.tuke.kpi.oop.game.tools;

import sk.tuke.kpi.gamelib.framework.AbstractActor;

public abstract class BreakableTool extends AbstractActor {
    private int remainingUses;

    public BreakableTool(int remainingUses) {
        this.remainingUses = remainingUses;
    }

    /* make method "use()" for all tools, witch will represent the tool usage */
    public void use(){
        if(this.remainingUses > 0) {
            this.remainingUses -= 1;
        }
        if(this.remainingUses == 0) {
            this.getScene().removeActor(this);
        }
    }

    /* a method, which returns a variable "remainingUses" */
    public int getRemainingUses(){
        return remainingUses;
    }
}
