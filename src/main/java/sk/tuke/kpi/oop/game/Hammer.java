package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Hammer extends AbstractActor {
    private Animation hammerAnimation;
    protected int healthUse;

    public Hammer(){
        this.healthUse = 1;
        this.hammerAnimation = new Animation("sprites/hammer.png", 16, 16);
        setAnimation(hammerAnimation);
    }
    public int getHealth() { return this.healthUse; }
    public void use(){
        if(this.healthUse > 0) this.healthUse -= 1;
        if(this.healthUse == 0){
            this.getScene().removeActor(this);
        }
    }
}
