package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.Actor;

public interface Movable extends Actor {
    int getSpeed();
    default void stoppedMoving() {}
    default void startedMoving(Direction direction) {}
}
