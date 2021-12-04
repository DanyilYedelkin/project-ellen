package sk.tuke.kpi.oop.game.openables;

//add the library
import sk.tuke.kpi.gamelib.Actor;


public class LockedDoor extends Door{
    //create a variable, which is about, if the door is locked
    private boolean isLocked;

    //default LockerDoor()
    public LockedDoor(){
        super();
        isLocked = true;
    }
    //LockedDoor with +2 new variables in the ()
    public LockedDoor(String name, Orientation orientation){
        super(name, orientation);
        isLocked = true;
    }

    //zamknutie (a zároveň zatvorenie) dverí
    //a method for lock the door
    public void lock(){
        isLocked = true;
        this.close();
    }

    //odomknutie (a zároveň otvorenie) dverí
    //a method for unlock the door
    public void unlock(){
        isLocked = false;
        this.open();
    }

    //vráti aktuálny stav dverí - či sú zamknuté alebo nie
    //return the current door's situation (if it's locked, or not)
    private boolean isLocked(){
        return isLocked;
    }

    //a method for using access card
    @Override
    public void useWith(Actor actor){
        if(!isLocked()) super.useWith(actor);
    }
}
