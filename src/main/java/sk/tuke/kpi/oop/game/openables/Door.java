package sk.tuke.kpi.oop.game.openables;

//add libraries
import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.map.MapTile;
import sk.tuke.kpi.gamelib.messages.Topic;
import sk.tuke.kpi.oop.game.items.Usable;

import java.util.Objects;

//create a public class Door
public class Door extends AbstractActor implements Openable, Usable<Actor> {
    /*  create a private variables  */

    //variables for animations
    private Animation openedDoorAnimation = new Animation("sprites/vdoor.png", 16, 32,
        0.1f, Animation.PlayMode.ONCE_REVERSED);
    private Animation closedDoorAnimation = new Animation("sprites/vdoor.png", 16, 32,
        0.1f, Animation.PlayMode.ONCE);

    //another variables
    private boolean opened;
    public enum Orientation{
        VERTICAL,
        HORIZONTAL
    }
    private Orientation doorOrientation;
    //create public variables for topic
    public static final Topic<Door> DOOR_OPENED = Topic.create("door opened", Door.class);
    public static final Topic<Door> DOOR_CLOSED = Topic.create("door closed", Door.class);


    //public Door (default)
    public Door(){
        //default type of opened
        opened = false;

        //add default animation and stop it
        setAnimation(closedDoorAnimation);
        getAnimation().stop();
    }
    //public Door (if we have +2 new variables in the Door() )
    public Door(String name, Orientation orientation){
        super(name);
        //default type of opened
        opened = false;

        //if door's orientation is vertical
        if(orientation == Orientation.VERTICAL){
            doorOrientation = orientation;

            setAnimation(closedDoorAnimation);
        } else if(orientation == Orientation.HORIZONTAL){   //if door's orientation is horizontal
            doorOrientation = orientation;

            //create new animations for horizontal door
            closedDoorAnimation = new Animation("sprites/hdoor.png", 32, 16, 0.1f, Animation.PlayMode.ONCE);
            openedDoorAnimation = new Animation("sprites/hdoor.png", 32, 16, 0.1f, Animation.PlayMode.ONCE_REVERSED);

            //set the new animation
            setAnimation(closedDoorAnimation);
        }

        //always stop the setting animation
        getAnimation().stop();
    }



    //a method for open and close the door
    @Override
    public void useWith(Actor actor) {
        if(!isOpen()) open();
        else close();
    }

    //return Actor.class
    @Override
    public Class<Actor> getUsingActorClass() {
        return Actor.class;
    }


    //method for open the door (of all orientations)
    @Override
    public void open() {
        opened = true;

        /*if(doorOrientation == Orientation.HORIZONTAL){
            Objects.requireNonNull(getScene()).getMap().getTile(this.getPosX()/16 - 1, this.getPosY()/16).setType(MapTile.Type.CLEAR);
            Objects.requireNonNull(getScene()).getMap().getTile(this.getPosX()/16 - 1, this.getPosY()/16 + 1).setType(MapTile.Type.CLEAR);
        } else{
            Objects.requireNonNull(getScene()).getMap().getTile(this.getPosX()/16, this.getPosY()/16).setType(MapTile.Type.CLEAR);
            Objects.requireNonNull(getScene()).getMap().getTile(this.getPosX()/16 + 1, this.getPosY()/16).setType(MapTile.Type.CLEAR);
        }*/

        if(doorOrientation == Orientation.VERTICAL){
            Objects.requireNonNull(getScene()).getMap().getTile(this.getPosX()/16, this.getPosY()/16).setType(MapTile.Type.CLEAR);
            Objects.requireNonNull(getScene()).getMap().getTile(this.getPosX()/16, this.getPosY()/16 + 1).setType(MapTile.Type.CLEAR);
        } else{
            Objects.requireNonNull(getScene()).getMap().getTile(this.getPosX()/16, this.getPosY()/16).setType(MapTile.Type.CLEAR);
            Objects.requireNonNull(getScene()).getMap().getTile(this.getPosX()/16 + 1, this.getPosY()/16).setType(MapTile.Type.CLEAR);
        }

        setAnimation(openedDoorAnimation);

        //animations play
        getAnimation().play();
        getAnimation().stop();
        //publication of the message, that the door is opened
        getScene().getMessageBus().publish(DOOR_OPENED, this);
    }

    //a method for close the door (of all orientations)
    @Override
    public void close() {
        opened = false;

        /*if(doorOrientation == Orientation.HORIZONTAL){
            Objects.requireNonNull(getScene()).getMap().getTile(this.getPosX()/16 - 1, this.getPosY()/16).setType(MapTile.Type.WALL);
            Objects.requireNonNull(getScene()).getMap().getTile(this.getPosX()/16 - 1, this.getPosY()/16 + 1).setType(MapTile.Type.WALL);
        } else{*/
            //Objects.requireNonNull(getScene()).getMap().getTile(this.getPosX()/16, this.getPosY()/16).setType(MapTile.Type.WALL);
            //Objects.requireNonNull(getScene()).getMap().getTile(this.getPosX()/16 + 1, this.getPosY()/16).setType(MapTile.Type.WALL);
        //}

        /*if(doorOrientation == Orientation.HORIZONTAL){
            Objects.requireNonNull(getScene()).getMap().getTile(this.getPosX()/16, this.getPosY()/16).setType(MapTile.Type.WALL);
            Objects.requireNonNull(getScene()).getMap().getTile(this.getPosX()/16 + 1, this.getPosY()/16).setType(MapTile.Type.WALL);
        }*/
        if(doorOrientation == Orientation.VERTICAL){
            Objects.requireNonNull(getScene()).getMap().getTile(this.getPosX()/16, this.getPosY()/16).setType(MapTile.Type.WALL);
            Objects.requireNonNull(getScene()).getMap().getTile(this.getPosX()/16, this.getPosY()/16 + 1).setType(MapTile.Type.WALL);
        } else{
            Objects.requireNonNull(getScene()).getMap().getTile(this.getPosX()/16, this.getPosY()/16).setType(MapTile.Type.WALL);
            Objects.requireNonNull(getScene()).getMap().getTile(this.getPosX()/16 + 1, this.getPosY()/16).setType(MapTile.Type.WALL);
        }

        setAnimation(closedDoorAnimation);

        //play the animations
        getAnimation().play();
        getAnimation().stop();
        //publication the message, when the door is closed
        getScene().getMessageBus().publish(DOOR_CLOSED, this);
    }

    //return variable about the situations with door (opened it, or not)
    @Override
    public boolean isOpen() {
        return opened;
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);

        //always checks if the door is opened, or closed
        if(isOpen()) open();
        else close();
    }
}
