package sk.tuke.kpi.oop.game.weapons;

import sk.tuke.kpi.gamelib.graphics.Animation;

public class LaserBeam extends Bullet {
    public LaserBeam() {
        this.setDamage(50);
        this.setSpeed(7);

        setAnimation(new Animation("sprites/laser_beam.png",16,16));
    }
}
