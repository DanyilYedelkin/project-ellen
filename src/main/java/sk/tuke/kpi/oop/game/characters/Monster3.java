package sk.tuke.kpi.oop.game.characters;

import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.behaviours.Behaviour;

public class Monster3 extends Alien {

    public Monster3(int healthValue, Behaviour<? super Alien> behaviour){
        super(healthValue, behaviour);

        setAnimation(new Animation("sprites/monster_2.png", 78, 127, 0.2f));
    }
}
