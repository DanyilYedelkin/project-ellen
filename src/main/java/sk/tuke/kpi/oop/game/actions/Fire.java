package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.characters.Armed;
import sk.tuke.kpi.oop.game.weapons.Fireable;

import java.util.Objects;

public class Fire<A extends Armed> extends AbstractAction<A> {

    @Override
    public void execute(float deltaTime){

        if(getActor() == null){
            setDone(true);
        }

        if(isDone()){
            return;
        }

        int posX = Direction.fromAngle(getActor().getAnimation().getRotation()).getDx();
        int posY = Direction.fromAngle(getActor().getAnimation().getRotation()).getDy();

        Fireable fireable = Objects.requireNonNull(getActor()).getFirearm().fire();
        if(fireable != null){
            Objects.requireNonNull(getActor().getScene()).addActor(fireable, getActor().getPosX() + 8 + posX*24,
                getActor().getPosY() + 8 + posY*24);

            fireable.startedMoving(Direction.fromAngle(getActor().getAnimation().getRotation()));
            new Move<Fireable>(Direction.fromAngle(getActor().getAnimation().getRotation()), Float.MAX_VALUE).scheduleFor(fireable);
        }

        if(!isDone()){
            setDone(true);
        }
    }

}

