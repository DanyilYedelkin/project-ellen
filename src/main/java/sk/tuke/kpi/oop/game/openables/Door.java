package sk.tuke.kpi.oop.game.openables;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.map.MapTile;
import sk.tuke.kpi.gamelib.messages.Topic;
import sk.tuke.kpi.oop.game.items.Usable;

import java.util.Objects;

public class Door extends AbstractActor implements Openable, Usable<Actor> {
    private Animation openedDoorAnimation;
    private Animation closedDoorAnimation;
    private boolean opened;
    public static final Topic<Door> DOOR_OPENED = Topic.create("door opened", Door.class);
    public static final Topic<Door> DOOR_CLOSED = Topic.create("door closed", Door.class);


    public Door(){
        opened = false;

        closedDoorAnimation = new Animation("sprites/vdoor.png", 16, 32, 0.1f, Animation.PlayMode.ONCE);
        openedDoorAnimation = new Animation("sprites/vdoor.png", 16, 32, 0.1f, Animation.PlayMode.ONCE_REVERSED);

        setAnimation(closedDoorAnimation);
        getAnimation().stop();
    }
    public Door(String name){
        super(name);
        opened = false;

        closedDoorAnimation = new Animation("sprites/vdoor.png", 16, 32, 0.1f, Animation.PlayMode.ONCE);
        openedDoorAnimation = new Animation("sprites/vdoor.png", 16, 32, 0.1f, Animation.PlayMode.ONCE_REVERSED);

        setAnimation(closedDoorAnimation);
        getAnimation().stop();
    }



    @Override
    public void useWith(Actor actor) {
        if(!isOpen()) open();
        else close();
    }

    @Override
    public Class<Actor> getUsingActorClass() {
        return Actor.class;
    }


    @Override
    public void open() {
        opened = true;

        Objects.requireNonNull(getScene()).getMap().getTile(getPosX()/16, getPosY()/16).setType(MapTile.Type.CLEAR);

        setAnimation(openedDoorAnimation);

        getAnimation().play();
        getAnimation().stop();
        getScene().getMessageBus().publish(DOOR_OPENED, this);
    }

    @Override
    public void close() {
        opened = false;

        Objects.requireNonNull(getScene()).getMap().getTile(getPosX()/16, getPosY()/16).setType(MapTile.Type.WALL);

        setAnimation(closedDoorAnimation);

        getAnimation().play();
        getAnimation().stop();
        getScene().getMessageBus().publish(DOOR_CLOSED, this);
    }

    @Override
    public boolean isOpen() {
        return opened;
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);

        if(isOpen()){
            open();
        } else{
            close();
        }
    }
}
