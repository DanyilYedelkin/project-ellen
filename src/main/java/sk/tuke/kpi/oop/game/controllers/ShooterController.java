package sk.tuke.kpi.oop.game.controllers;

//add libraries
import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Input;
import sk.tuke.kpi.gamelib.KeyboardListener;
import sk.tuke.kpi.oop.game.actions.Fire;
import sk.tuke.kpi.oop.game.characters.Armed;

//create public class ShooterController
public class ShooterController implements KeyboardListener {
    //create a private variable
    private Armed armed;

    //default ShooterController
    public ShooterController(Armed armed){
        this.armed = armed;
    }

    //a method for pressing SPACE to shoot
    @Override
    public void keyPressed(@NotNull Input.Key key){
        if(key == Input.Key.SPACE){
            //create new Fire
            Fire<Armed> fire = new Fire<>();

            fire.setActor(armed);
            fire.scheduleFor(armed);
        }
    }

}
