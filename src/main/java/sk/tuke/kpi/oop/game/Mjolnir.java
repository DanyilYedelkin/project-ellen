package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.graphics.Animation;

public class Mjolnir extends Hammer{
    private Animation mjolnirAnimation;

    public Mjolnir() {
        this.healthUse = 4;
        mjolnirAnimation = new Animation("sprites/hammer.png", 16, 16);
        setAnimation(mjolnirAnimation);
    }
}
