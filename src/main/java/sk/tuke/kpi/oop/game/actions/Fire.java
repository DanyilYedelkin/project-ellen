package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.characters.Armed;
import sk.tuke.kpi.oop.game.weapons.Fireable;

import java.util.Objects;

public class Fire<A extends Armed> extends AbstractAction<A> {

    @Override
    public void execute(float deltaTime){
        int posX = Direction.fromAngle(Objects.requireNonNull(getActor()).getAnimation().getRotation()).getDx();
        int posY = Direction.fromAngle(Objects.requireNonNull(getActor()).getAnimation().getRotation()).getDy();

        if(getActor() == null){
            setDone(true);

            return;
        } else if(isDone()){
            return;
        }

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

