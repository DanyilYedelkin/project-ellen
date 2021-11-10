package sk.tuke.kpi.oop.game.items;

/* (the code is not perfect, later, it will be modified and improved) */

//add libraries
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.Reactor;
//import sk.tuke.kpi.oop.game.tools.BreakableTool;

public class FireExtinguisher extends BreakableTool<Reactor> implements Collectible{
    //private Animation extinguisherAnimation;

    public FireExtinguisher() {
        super(1);
        // create animation object
        //extinguisherAnimation = new Animation("sprites/extinguisher.png", 16, 16);
        // set actor's animation to just created Animation object
        //setAnimation(extinguisherAnimation);
        setAnimation(new Animation("sprites/extinguisher.png", 16, 16));
    }

    @Override // override method "useWith" for a fire extinguisher
    public void useWith(Reactor reactor){
        if(reactor != null && reactor.extinguish()) super.useWith(reactor);
    }
}
