package sk.tuke.kpi.oop.game.scenarios;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.*;
import sk.tuke.kpi.oop.game.Computer;
import sk.tuke.kpi.oop.game.Cooler;
import sk.tuke.kpi.oop.game.Reactor;
import sk.tuke.kpi.oop.game.Ventilator;
import sk.tuke.kpi.oop.game.behaviours.RandomlyMoving;
import sk.tuke.kpi.oop.game.characters.Alien;
import sk.tuke.kpi.oop.game.characters.AlienMother;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.controllers.KeeperController;
import sk.tuke.kpi.oop.game.controllers.MovableController;
import sk.tuke.kpi.oop.game.controllers.ShooterController;
import sk.tuke.kpi.oop.game.items.*;
import sk.tuke.kpi.oop.game.openables.Door;
import sk.tuke.kpi.oop.game.openables.LockedDoor;


public class DieHard implements SceneListener {
    private Energy energy;
    private Ammo ammo;
    private Ripley ellen;


    public static class Factory implements ActorFactory{
        private Reactor reactor;

        @Override
        public @Nullable Actor create(@Nullable String type, @Nullable String name) {
            assert name != null;
            switch(name){
                case "ellen":
                    return new Ripley();
                case "energy":
                    return new Energy();
                case "door":
                    //return new Door(name, Door.Orientation.VERTICAL);
                    return new LockedDoor(name, Door.Orientation.VERTICAL);
                case "back door":
                    //return new Door(name, Door.Orientation.HORIZONTAL);
                    return new LockedDoor(name, Door.Orientation.HORIZONTAL);
                case "alien":
                    return new Alien(80, new RandomlyMoving());
                case "alien mother":
                    return new AlienMother(200, new RandomlyMoving());
                case "ammo":
                    return new Ammo();
                case "computer":
                    return new Computer();
                case "ventilator":
                    return new Ventilator();
                case "access card":
                    return new AccessCard();
                case "hammer":
                    return new Hammer();
                case "cooler":
                    return new Cooler(reactor);
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

        Disposable movableController = scene.getInput().registerListener(new MovableController(ellen));
        Disposable keeperController = scene.getInput().registerListener(new KeeperController(ellen));
        Disposable shooterController = scene.getInput().registerListener(new ShooterController(ellen));

        scene.getGame().pushActorContainer(ellen.getBackpack());

        //AccessCard accessCard = new AccessCard();
        //ellen.getBackpack().add(accessCard);

        scene.getMessageBus().subscribe(Ripley.RIPLEY_DIED, (Ripley)->movableController.dispose());
        scene.getMessageBus().subscribe(Ripley.RIPLEY_DIED, (Ripley)->keeperController.dispose());
        scene.getMessageBus().subscribe(Ripley.RIPLEY_DIED, (Ripley)->shooterController.dispose());
    }

    @Override
    public void sceneUpdating(@NotNull Scene scene) {
        ellen = scene.getFirstActorByType(Ripley.class);
        assert ellen != null;

        ellen.showRipleyState();

        energy = scene.getFirstActorByType(Energy.class);
        if(energy != null && ellen.intersects(energy)){
            energy.useWith(ellen);
        }

        ammo = scene.getFirstActorByType(Ammo.class);
        if(ammo != null && ellen.intersects(ammo)){
            ammo.useWith(ellen);
        }

    }



    @Override
    public void sceneCreated(@NotNull Scene scene) {

    }
}
