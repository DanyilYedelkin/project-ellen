package sk.tuke.kpi.oop.game.behaviours;

import sk.tuke.kpi.gamelib.Actor;

public interface Behaviour<A extends Actor> {
    //metódu bez návratovej hodnoty setUp(A actor), ktorá dostane aktéra ako
    // parameter a ktorej úlohou bude definovať správanie sa daného aktéra.
    void setUp(A actor);
}
