package sk.tuke.kpi.oop.game.characters;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Alien extends AbstractActor {
    private Animation alienAnimation;

    public Alien(){
        alienAnimation = new Animation("sprites/alien.png", 32, 32, 0.1f, Animation.PlayMode.LOOP_PINGPONG);

        setAnimation(alienAnimation);
    }
}
