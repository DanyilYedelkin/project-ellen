package sk.tuke.kpi.oop.game.items;

//add libraries
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.Repairable;

public class Hammer extends BreakableTool<Repairable> implements Collectible{
    private int healthUse;       // a variable, that have a health point of the hammer


    public Hammer() {
        super(1);
        this.healthUse = 1;        // health point of a regular hammer
        setRemainingUses(1);

        setAnimation(new Animation("sprites/hammer.png", 16, 16));
    }

    /* a method, that returns health points of the hammer */
    public int getHealth() {
        return this.healthUse;
    }

    @Override // a method, which is override for a hammer
    public void useWith(Repairable repairable){
        if(repairable != null && repairable.repair()) super.useWith(repairable);
    }
    @Override
    public Class<Repairable> getUsingActorClass() {
        return Repairable.class;
    }

}
