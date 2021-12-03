package sk.tuke.kpi.oop.game.market;

import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.Repairable;
import sk.tuke.kpi.oop.game.items.BreakableTool;
import sk.tuke.kpi.oop.game.items.Collectible;

import java.util.Objects;


public class Money extends BreakableTool<Repairable> implements Collectible {
    public Money(){
        super(1);
        setRemainingUses(1);

        setAnimation(new Animation("sprites/money.png", 16, 16));
    }

    @Override
    public Class<Repairable> getUsingActorClass() {
        return Repairable.class;
    }
    @Override // a method, which is override for a hammer
    public void useWith(Repairable repairable){
        if(repairable != null && repairable.repair()) {
            super.useWith(repairable);

            Objects.requireNonNull(getScene()).removeActor(this);
        }
    }
}
