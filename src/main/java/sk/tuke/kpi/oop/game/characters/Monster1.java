package sk.tuke.kpi.oop.game.characters;

import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.behaviours.Behaviour;

public class Monster1 extends Alien {

    public Monster1(int healthValue, Behaviour<? super Alien> behaviour){
        super(healthValue, behaviour);

        setAnimation(new Animation("sprites/monster.png", 72, 128, 0.2f));
    }
}
