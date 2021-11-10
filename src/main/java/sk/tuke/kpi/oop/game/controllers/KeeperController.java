package sk.tuke.kpi.oop.game.controllers;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Input;
import sk.tuke.kpi.gamelib.KeyboardListener;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.actions.Drop;
import sk.tuke.kpi.oop.game.actions.Shift;
import sk.tuke.kpi.oop.game.actions.Take;


public class KeeperController implements KeyboardListener {
    private Keeper keeper;

    public KeeperController(Keeper keeper) {
        this.keeper = keeper;
    }

    @Override
    public void keyPressed(@NotNull Input.Key key) {
        if(key == Input.Key.ENTER){
            new Take<>().scheduleFor(keeper);
        } else if(key == Input.Key.BACKSPACE){
            new Drop<>().scheduleFor(keeper);
        } else if(key == Input.Key.S){
            new Shift<>().scheduleFor(keeper);
        }
    }
}
