package sk.tuke.kpi.oop.game.characters;

import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.behaviours.Behaviour;

public class Monster2 extends Alien {

    public Monster2(int healthValue, Behaviour<? super Alien> behaviour){
        super(healthValue, behaviour);

        setAnimation(new Animation("sprites/monster_1.png", 60, 128, 0.2f));
    }
}
