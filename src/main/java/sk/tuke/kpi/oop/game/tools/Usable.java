package sk.tuke.kpi.oop.game.tools;

import sk.tuke.kpi.gamelib.Actor;

public interface Usable<A extends Actor> {
    void useWith(A actor);
}
