package sk.tuke.kpi.oop.game.items;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Shield extends AbstractActor {

    public Shield(){
        setAnimation(new Animation("sprites/shield.png", 26, 26));
    }
}
