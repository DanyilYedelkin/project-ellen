package sk.tuke.kpi.oop.game.scenarios;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.*;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.When;
import sk.tuke.kpi.oop.game.Locker;
import sk.tuke.kpi.oop.game.Ventilator;
import sk.tuke.kpi.oop.game.characters.Alien;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.controllers.KeeperController;
import sk.tuke.kpi.oop.game.controllers.MovableController;
import sk.tuke.kpi.oop.game.items.AccessCard;
import sk.tuke.kpi.oop.game.items.Ammo;
import sk.tuke.kpi.oop.game.items.Energy;
import sk.tuke.kpi.oop.game.openables.Door;
import sk.tuke.kpi.oop.game.openables.LockedDoor;

public class EscapeRoom implements SceneListener {
    private Energy energy;
    private Ammo ammo;
    private Ripley ellen;

    public static class Factory implements ActorFactory {
        @Override
        public @Nullable Actor create(@Nullable String type, @Nullable String name) {
            assert name != null;
            switch(name){
                case "ellen":
                    return new Ripley();
                case "energy":
                    return new Energy();
                case "access card":
                    return new AccessCard();
                case "door":
                    return new LockedDoor();
                case "locker":
                    return new Locker();
                case "ventilator":
                    return new Ventilator();
                case "alien":
                    return new Alien();
                case "alien mother":
                    //return
                case "ammo":
                    return new Ammo();
                default:
                    return null;
            }
        }
    }

    @Override
    public void sceneInitialized(@NotNull Scene scene) {

        ellen = scene.getFirstActorByType(Ripley.class);
        assert ellen != null;
        scene.follow(ellen);

        Disposable movableCon = scene.getInput().registerListener(new MovableController(ellen));
        Disposable keeperCon = scene.getInput().registerListener(new KeeperController(ellen));

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


        AccessCard accessCard = new AccessCard();
        ellen.getBackpack().add(accessCard);
    }

    @Override
    public void sceneUpdating(@NotNull Scene scene) {
        ellen= scene.getFirstActorByType(Ripley.class);
        assert ellen != null;

        ellen.showRipleyState();
    }



    @Override
    public void sceneCreated(@NotNull Scene scene) {

    }
}
