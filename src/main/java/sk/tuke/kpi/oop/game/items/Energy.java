package sk.tuke.kpi.oop.game.items;

//add libraries
import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.characters.Alive;
import sk.tuke.kpi.oop.game.characters.Ripley;

import java.util.List;
import java.util.Objects;


//create public class Energy
public class Energy extends AbstractActor implements Usable<Alive>{
    //create the private variable
    private boolean repeat = true;

    //default Energy()
    public Energy(){
        //create and set the new energy's animation
        Animation energy = new Animation("sprites/energy.png", 16, 16);
        setAnimation(energy);
    }

    //a method for using energy by alive actor (ripley)
    @Override
    public void useWith(Alive alive){
        //checks if alive health points isn't 100
        if(alive != null && alive.getHealth().getValue() != 100){
            alive.getHealth().refill(100);
            if(intersects(alive)) alive.getHealth().restore();

            (Objects.requireNonNull(getScene())).removeActor(this);
        }
    }

    @Override
    public Class<Alive> getUsingActorClass() {
        return Alive.class;
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        new Loop<>(
            new Invoke<>(this::useAndRemove)
        ).scheduleOn(scene);
    }

    //a method for using and removing energy from the scene
    private void useAndRemove(){
        List<Actor> actorList;
        actorList = Objects.requireNonNull(getScene()).getActors();

        for(Actor ellen : actorList){
            //if ellen is ripley and his health points isn't 100
            if(ellen instanceof Ripley && ellen.intersects(this) && repeat && ((Ripley) ellen).getHealth().getValue() != 100){
                ((Ripley) ellen).getHealth().refill(100);

                (Objects.requireNonNull(getScene())).removeActor(this);
                //a variable, which helps us to don't use energy more than once (1)
                repeat = false;
            }
        }

    }
}
