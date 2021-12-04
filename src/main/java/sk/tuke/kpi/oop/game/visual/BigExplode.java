package sk.tuke.kpi.oop.game.visual;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class BigExplode extends AbstractActor {
    public BigExplode(){
        setAnimation(new Animation("sprites/large_explosion.png",
            32, 32, 0.1f, Animation.PlayMode.LOOP_PINGPONG));
    }
}
