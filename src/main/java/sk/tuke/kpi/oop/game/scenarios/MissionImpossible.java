package sk.tuke.kpi.oop.game.scenarios;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.*;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.When;
import sk.tuke.kpi.oop.game.Locker;
import sk.tuke.kpi.oop.game.Ventilator;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.controllers.KeeperController;
import sk.tuke.kpi.oop.game.controllers.MovableController;
import sk.tuke.kpi.oop.game.items.AccessCard;
import sk.tuke.kpi.oop.game.items.Ammo;
import sk.tuke.kpi.oop.game.items.Energy;
//import sk.tuke.kpi.oop.game.openables.Door;
import sk.tuke.kpi.oop.game.items.Hammer;
import sk.tuke.kpi.oop.game.openables.Door;
import sk.tuke.kpi.oop.game.openables.LockedDoor;

public class MissionImpossible implements SceneListener {
    private Energy energy;
    private Ammo ammo;
    //private Ripley ellen;

    public static class Factory implements ActorFactory {

        @Override
        public @Nullable Actor create(@Nullable String type, @Nullable String name) {
            assert name != null;
            assert type != null;

            //access card, door, ellen, energy, locker, ventilator.
            switch(name){
                case "access card":
                    return new AccessCard();
                case "door":
                    return new LockedDoor();
                case "ellen":
                    return new Ripley();
                case "energy":
                    return new Energy();
                case "locker":
                    return new Locker();
                case "ventilator":
                    return new Ventilator();
                default:
                    return null;
            }
            //end of the switch(name){}
        }
    }

    @Override
    public void sceneInitialized(@NotNull Scene scene){
        Ripley ellen = scene.getFirstActorByType(Ripley.class);
        assert ellen != null;
        scene.follow(ellen);

        Disposable movableController = scene.getInput().registerListener(new MovableController(ellen));
        Disposable keeperController = scene.getInput().registerListener(new KeeperController(ellen));

        scene.getGame().pushActorContainer(ellen.getBackpack());

        energy = scene.getFirstActorByType(Energy.class);
        //assert energy != null;
        if(energy != null){
            new When<>(
                () -> ellen.intersects(energy),
                new Invoke<>(() -> energy.useWith(ellen))
            ).scheduleFor(ellen);
        }

        ammo = scene.getFirstActorByType(Ammo.class);
        if(ammo != null){
            new When<>(
                () -> ellen.intersects(ammo),
                new Invoke<>(() -> ammo.useWith(ellen))
            ).scheduleFor(ellen);
        }

        Hammer hammer = new Hammer();
        scene.addActor(hammer, 150, 100);

        //scene.getMessageBus().subscribe(Door.DOOR_OPENED, (Ripley)->ellen.decreaseEnergy());
        scene.getMessageBus().subscribe(Ventilator.VENTILATOR_REPAIRED, (Ripley)->ellen.stopDecreaseEnergy().dispose());

        scene.getMessageBus().subscribe(Ripley.RIPLEY_DIED, (Ripley)->movableController.dispose());
        scene.getMessageBus().subscribe(Ripley.RIPLEY_DIED, (Ripley)->keeperController.dispose());
    }

    @Override
    public void sceneUpdating(@NotNull Scene scene){
        Ripley ellen = scene.getFirstActorByType(Ripley.class);
        assert ellen != null;

        ellen.showRipleyState();
    }
}
