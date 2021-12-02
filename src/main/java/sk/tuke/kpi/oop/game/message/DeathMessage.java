package sk.tuke.kpi.oop.game.message;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class DeathMessage extends AbstractActor {
    public DeathMessage(){
        setAnimation(new Animation("sprites/popup_level_failed.png", 288, 128));
    }
}
