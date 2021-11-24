package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.items.Backpack;
import sk.tuke.kpi.oop.game.items.Collectible;

//import java.util.Objects;


public class Drop<A extends Keeper> extends AbstractAction<A> {
    private Backpack backpack;
    private Scene scene;
    private Collectible lastItem;

    @Override
    public void execute(float deltaTime) {
        /*if((getActor() != null || !getActor().getBackpack().isEmpty()) && !isDone()){
            backpack = getActor().getBackpack();
            scene = getActor().getScene();

            if(!backpack.isEmpty()){
                //System.out.println("CHEEECK !!!!");
                int x = getActor().getPosX() + (getActor().getWidth() - Objects.requireNonNull(backpack.peek()).getWidth());
                int y = getActor().getPosY() + (getActor().getHeight() - Math.abs(backpack.peek().getHeight()/3));

                scene.addActor(Objects.requireNonNull(backpack.peek()), x, y);
                getActor().getBackpack().remove(Objects.requireNonNull(backpack.peek()));
            }
        }
        setDone(true);*/


        if(getActor() == null || getActor().getBackpack().isEmpty() || getActor().getScene() == null){
            setDone(true);
        }
        if(!isDone()){
            backpack = getActor().getBackpack();
            scene = getActor().getScene();
            lastItem = getActor().getBackpack().peek();
            assert lastItem != null;

            //if(!backpack.isEmpty()){
                //System.out.println("CHEEECK !!!!");
                int x = getActor().getPosX() + (getActor().getWidth() - (lastItem.getWidth()));
                int y = getActor().getPosY() + (getActor().getHeight() - Math.abs(lastItem.getHeight()/3));

                scene.addActor(lastItem, x, y);
                getActor().getBackpack().remove(lastItem);
            //}
        }
        setDone(true);
    }
}
