package sk.tuke.kpi.oop.game.tools;

import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.Reactor;
//import sk.tuke.kpi.oop.game.tools.BreakableTool;

public class FireExtinguisher extends BreakableTool<Reactor> {
    //private Animation extinguisherAnimation;

    public FireExtinguisher() {
        super(1);
        // create animation object
        //extinguisherAnimation = new Animation("sprites/extinguisher.png", 16, 16);
        // set actor's animation to just created Animation object
        //setAnimation(extinguisherAnimation);
        setAnimation(new Animation("sprites/extinguisher.png", 16, 16));
    }

    @Override
    public void useWith(Reactor reactor){
        if(reactor != null && reactor.extinguish()) super.useWith(reactor);
    }
}
