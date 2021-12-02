package sk.tuke.kpi.oop.game.scenarios;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.*;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.controllers.KeeperController;
import sk.tuke.kpi.oop.game.controllers.MovableController;
import sk.tuke.kpi.oop.game.controllers.ShooterController;
import sk.tuke.kpi.oop.game.items.AccessCard;
import sk.tuke.kpi.oop.game.items.Ammo;
import sk.tuke.kpi.oop.game.items.Energy;
import sk.tuke.kpi.oop.game.openables.Door;


public class EscapeRoom implements SceneListener {
    private Ripley ellen;

    public static class Factory implements ActorFactory {
        @Override
        public @Nullable Actor create(@Nullable String type, @Nullable String name) {
            assert name != null;
            switch(name){
                case "ellen":
                    return new Ripley();
                    //<object id="1" name="ellen" type="ripley" x="416" y="192" width="32" height="32"/>
                case "energy":
                    return new Energy();
                case "exit door":
                    //return new LockedDoor(name, Door.Orientation.VERTICAL);
                case "front door":
                    return new Door(name, Door.Orientation.VERTICAL);
                    //return new LockedDoor(name, Door.Orientation.VERTICAL);
                case "back door":
                    return new Door(name, Door.Orientation.HORIZONTAL);
                    //return new LockedDoor(name, Door.Orientation.HORIZONTAL);
                case "alien":
                    //return new Alien();
                    //return new Alien(100, new RandomlyMoving());
                case "alien mother":
                    //return new AlienMother(200);
                    //return new AlienMother(200, new RandomlyMoving());
                    //<object id="17" name="front door" type="waiting2" x="112" y="224" width="78" height="127"/>
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

        Disposable movableController = scene.getInput().registerListener(new MovableController(ellen));
        Disposable keeperController = scene.getInput().registerListener(new KeeperController(ellen));
        Disposable shooterController = scene.getInput().registerListener(new ShooterController(ellen));

        scene.getGame().pushActorContainer(ellen.getBackpack());

        AccessCard accessCard = new AccessCard();
        ellen.getBackpack().add(accessCard);

        scene.getMessageBus().subscribe(Ripley.RIPLEY_DIED, (Ripley)->movableController.dispose());
        scene.getMessageBus().subscribe(Ripley.RIPLEY_DIED, (Ripley)->keeperController.dispose());
        scene.getMessageBus().subscribe(Ripley.RIPLEY_DIED, (Ripley)->shooterController.dispose());
    }

    @Override
    public void sceneUpdating(@NotNull Scene scene) {
        ellen = scene.getFirstActorByType(Ripley.class);
        assert ellen != null;

        ellen.showRipleyState();
    }



    @Override
    public void sceneCreated(@NotNull Scene scene) {

    }
}
