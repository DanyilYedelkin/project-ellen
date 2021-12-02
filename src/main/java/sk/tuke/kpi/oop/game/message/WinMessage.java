package sk.tuke.kpi.oop.game.message;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class WinMessage extends AbstractActor {
    public WinMessage(){
        setAnimation(new Animation("sprites/popup_level_done.png", 288, 128));
    }
}
