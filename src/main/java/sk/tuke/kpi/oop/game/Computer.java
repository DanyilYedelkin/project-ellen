package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Computer extends AbstractActor{

    public Computer(){
        setAnimation(new Animation("sprites/computer.png",
            80, 48, 0.2f, Animation.PlayMode.LOOP));
    }
    public double add(double one, double two) { return one + two; }
    public int add(int one, int two) { return  one + two; }
}
