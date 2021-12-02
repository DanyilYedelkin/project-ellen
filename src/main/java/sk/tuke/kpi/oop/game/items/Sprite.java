package sk.tuke.kpi.oop.game.items;

import sk.tuke.kpi.gamelib.graphics.Animation;

public class Sprite extends CocaCola {
    public Sprite(){
        super();

        setAnimation(new Animation("sprites/sprite.png", 29, 16));
    }
}
