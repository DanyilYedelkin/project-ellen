package sk.tuke.kpi.oop.game.openables;

import sk.tuke.kpi.gamelib.Actor;

public interface Openable extends Actor {
    void open();        //otvorenie predmetu (aktéra)
    void close();       //zatvorenie predmetu (aktéra)
    boolean isOpen();   //vráti true, ak je predmet (aktér) otvorený, v opačnom prípade vráti false
}
