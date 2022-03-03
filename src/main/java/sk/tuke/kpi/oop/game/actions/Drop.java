package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.items.Backpack;
import sk.tuke.kpi.oop.game.items.Collectible;



public class Drop<A extends Keeper> extends AbstractAction<A> {
    private Backpack backpack;
    private Scene scene;
    private Collectible lastItem;

    @Override
    public void execute(float deltaTime) {

        if(getActor() == null || getActor().getBackpack().isEmpty() || getActor().getScene() == null) {
            setDone(true);
        }
        if(!isDone()) {
            backpack = getActor().getBackpack();
            scene = getActor().getScene();
            lastItem = getActor().getBackpack().peek();

            if(this.lastItem != null) {
                 int x = getActor().getPosX() + (getActor().getWidth() - (lastItem.getWidth()));
                 int y = getActor().getPosY() + (getActor().getHeight() - Math.abs(lastItem.getHeight() / 3));

                 scene.addActor(lastItem, x, y);
                 getActor().getBackpack().remove(lastItem);
            }
            
            setDone(true);
        }

        setDone(true);

        if(!isDone()){
            checkDebugging();
        }
    }

    private void checkDebugging() {
        if(backpack.isEmpty()) {
            System.out.println("I'm empty! You can't peek any items in me");
        }
    }
}
