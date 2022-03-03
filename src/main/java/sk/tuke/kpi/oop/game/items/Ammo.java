package sk.tuke.kpi.oop.game.items;

//add libraries
import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.characters.Armed;
import sk.tuke.kpi.oop.game.characters.Ripley;


import java.util.List;
import java.util.Objects;


//create a public class Ammo
public class Ammo extends AbstractActor implements Usable<Armed>{
    //create a private variable
    private boolean repeat = true;

    //default Ammo()
    public Ammo(){
        //create and set default animation for ammo
        Animation ammo = new Animation("sprites/ammo.png", 16, 16);
        setAnimation(ammo);
    }

    //a method for use ammo by armed actor
    @Override
    public void useWith(Armed armed) {
        if(armed != null && armed.getFirearm().getAmmo() < 500){
            armed.getFirearm().reload(50);

            Objects.requireNonNull(getScene()).removeActor(this);
        }

    }

    @Override
    public Class<Armed> getUsingActorClass() {
        return Armed.class;
    }


    //for checking and pick up ammo by actor
    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        new Loop<>(
            new Invoke<>(this::useAndRemove)
        ).scheduleOn(scene);
    }

    //a method for using ammo
    private void useAndRemove(){
        List<Actor> actorList;
        actorList = Objects.requireNonNull(getScene()).getActors();

        for(Actor ellen : actorList){
            //checks if ellen is Ripley, if they're intersected and if the ellen's ammo < 500
            if(ellen instanceof Ripley && ellen.intersects(this) && ((Ripley) ellen).getFirearm().getAmmo() < 500 && repeat){
                ((Ripley) ellen).getFirearm().reload(50);

                Objects.requireNonNull(getScene()).removeActor(this);
                //a variable for not a repeat use ammo
                repeat = false;
                break;
            }
        }
    }

}
