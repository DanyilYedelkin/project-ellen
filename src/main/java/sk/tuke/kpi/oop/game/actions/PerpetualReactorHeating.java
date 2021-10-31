package sk.tuke.kpi.oop.game.actions;

//add libraries
import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.Reactor;

public class PerpetualReactorHeating extends AbstractAction<Reactor> {
    //creating a private variable
    private int increment;

    public PerpetualReactorHeating(int increment) {
        this.increment = increment;
    }

    @Override
    public void execute(float deltaTime){
        //increases the reactor temperature by the required number of temperature units
        if(getActor() != null && deltaTime >= 0){
            getActor().increaseTemperature(this.increment + (int)deltaTime);
        }
    }
}
