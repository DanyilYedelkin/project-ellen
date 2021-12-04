package sk.tuke.kpi.oop.game.controllers;

//add libraries
import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Input;
import sk.tuke.kpi.gamelib.KeyboardListener;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.actions.Drop;
import sk.tuke.kpi.oop.game.actions.Shift;
import sk.tuke.kpi.oop.game.actions.Take;
import sk.tuke.kpi.oop.game.actions.Use;
import sk.tuke.kpi.oop.game.items.Usable;

import java.util.Objects;

//create public class KeeperController
public class KeeperController implements KeyboardListener {
    //create a private variable keeper
    private Keeper keeper;

    //default KeeperController
    public KeeperController(Keeper keeper) {
        this.keeper = keeper;
    }

    //a method for pressing keys to do something with items in the scene, or in the backpack
    @Override
    public void keyPressed(@NotNull Input.Key key) {
        if(key == Input.Key.ENTER){
            new Take<>().scheduleFor(keeper);
        } else if(key == Input.Key.BACKSPACE){
            new Drop<>().scheduleFor(keeper);
        } else if(key == Input.Key.S){
            new Shift<>().scheduleFor(keeper);
        } else if(key == Input.Key.U){
            Usable<?> usable = Objects.requireNonNull(keeper.getScene()).getActors().stream().filter(Usable.class::isInstance).filter(keeper::intersects).map(Usable.class::cast).findFirst().orElse(null);
            if(usable != null){
                new Use<>(usable).scheduleForIntersectingWith(keeper);
            }
        } else if(key == Input.Key.B && keeper.getBackpack().peek() instanceof Usable){
            Use<?> use = new Use<>((Usable<?>) keeper.getBackpack().peek());
            //keeper.getBackpack().peek()
            //keeper.getBackpack().getContent().remove(1)

            use.scheduleForIntersectingWith(keeper);
        }
    }

}
