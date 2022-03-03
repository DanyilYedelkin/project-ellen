package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.items.Collectible;

import java.util.List;
import java.util.Objects;


public class Take<A extends Keeper> extends AbstractAction<A> {
    private Scene scene;
    private List<Actor> sceneItems;


    @Override
    public void execute(float deltaTime) {
        if(getActor() == null){
            setDone(true);
        } else if(getActor() != null && !isDone()){
            scene = getActor().getScene();
            sceneItems = Objects.requireNonNull(getActor().getScene()).getActors();

            for(Actor item : sceneItems){
                if(scene != null && item instanceof Collectible && item.intersects(getActor())){
                    try{
                        // code which can cause exception
                        getActor().getBackpack().add((Collectible)item);
                        Objects.requireNonNull(scene).removeActor(item);

                        break;
                    } catch (IllegalStateException exception){
                        // handling the exception of type Exception
                        // get the excpetion's message with ex.getMessage() method
                        scene.getOverlay().drawText(exception.getMessage(), 0, -10).showFor(2);
                    }
                }
            }
            setDone(true);
        }
    }

}
