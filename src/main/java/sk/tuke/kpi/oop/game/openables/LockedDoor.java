package sk.tuke.kpi.oop.game.openables;

import sk.tuke.kpi.gamelib.Actor;

public class LockedDoor extends Door{
    private boolean isLocked;

    public LockedDoor(){
        super();
        isLocked = true;
    }
    public LockedDoor(String name, Orientation orientation){
        super(name, orientation);
        isLocked = true;
    }

    //zamknutie (a zároveň zatvorenie) dverí
    public void lock(){
        isLocked = true;
        this.close();
    }
    //odomknutie (a zároveň otvorenie) dverí
    public void unlock(){
        isLocked = false;
        this.open();
    }
    //vráti aktuálny stav dverí - či sú zamknuté alebo nie
    private boolean isLocked(){
        return isLocked;
    }

    @Override
    public void useWith(Actor actor){
        if(!isLocked()) super.useWith(actor);
    }
}
