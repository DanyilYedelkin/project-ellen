package sk.tuke.kpi.oop.game.scenarios;

//add libraries
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.*;
import sk.tuke.kpi.oop.game.*;
import sk.tuke.kpi.oop.game.behaviours.FollowRipley;
import sk.tuke.kpi.oop.game.behaviours.RandomlyMoving;
import sk.tuke.kpi.oop.game.characters.*;
import sk.tuke.kpi.oop.game.market.Money;
import sk.tuke.kpi.oop.game.market.VendingMachines;
import sk.tuke.kpi.oop.game.message.DeathMessage;
import sk.tuke.kpi.oop.game.message.WinMessage;
import sk.tuke.kpi.oop.game.controllers.KeeperController;
import sk.tuke.kpi.oop.game.controllers.MovableController;
import sk.tuke.kpi.oop.game.controllers.ShooterController;
import sk.tuke.kpi.oop.game.items.*;
import sk.tuke.kpi.oop.game.openables.Door;
import sk.tuke.kpi.oop.game.openables.LockedDoor;
import sk.tuke.kpi.oop.game.randomEffect.Barrel;
import sk.tuke.kpi.oop.game.randomEffect.Body;
import sk.tuke.kpi.oop.game.randomEffect.BodyTrap;
import sk.tuke.kpi.oop.game.visual.Engine;

//create public class DieHard (my scenario of the game)
public class DieHard implements SceneListener {
    //create private variables for gameplay
    private Ripley ellen;
    private  Disposable movableController;
    private  Disposable keeperController;
    private  Disposable shooterController;


    public static class Factory implements ActorFactory{
        private Reactor reactor;

        @Override
        public @Nullable Actor create(@Nullable String type, @Nullable String name) {
            assert name != null;
            //creating actors from the map, using their names
            switch(name){
                case "ellen":
                    return new Ripley();
                case "energy":
                    return new Energy();
                case "door":
                    return new LockedDoor(name, Door.Orientation.VERTICAL);
                case "back door":
                    return new LockedDoor(name, Door.Orientation.HORIZONTAL);
                case "alien":
                    return new Alien(80, new RandomlyMoving());
                case "clever alien":
                    return new Alien(80, new FollowRipley());
                case "alien mother":
                    return new AlienMother(200, new FollowRipley());
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
                case "tunnel for finish":
                    return new TunnelFinish();
                case "barrel":
                    return new Barrel();
                case "body":
                    return new Body();
                case "shield":
                    return new BulletproofVest();
                case "lurker":
                    return new Lurker(50, new FollowRipley());
                case "body trap":
                    return new BodyTrap();
                case "coca cola":
                    return new CocaCola();
                case "sprite":
                    return new Sprite();
                case "engine":
                    return new Engine();
                case "money":
                    return new Money();
                case "vending machines":
                    return new VendingMachines();
                case "alien egg":
                    return new AlienEgg(50);
                default:
                    return null;
            }
        }
    }

    //a method, which initialized the scene
    @Override
    public void sceneInitialized(@NotNull Scene scene) {
        //find our ellen (main character)
        ellen = scene.getFirstActorByType(Ripley.class);
        assert ellen != null;
        //for following the main character
        scene.follow(ellen);

        //for find alien egg in the scene
        AlienEgg alienEgg = scene.getFirstActorByType(AlienEgg.class);

        movableController = scene.getInput().registerListener(new MovableController(ellen));
        keeperController = scene.getInput().registerListener(new KeeperController(ellen));
        shooterController = scene.getInput().registerListener(new ShooterController(ellen));

        scene.getGame().pushActorContainer(ellen.getBackpack());
        //for activate the alien egg after open the door :D
        scene.getMessageBus().subscribe(Door.DOOR_OPENED, (AlienEgg)->{
            assert alienEgg != null;
            alienEgg.activate();
        });


        scene.getMessageBus().subscribe(Ripley.RIPLEY_DIED, (Ripley)->movableController.dispose());
        scene.getMessageBus().subscribe(Ripley.RIPLEY_DIED, (Ripley)->keeperController.dispose());
        scene.getMessageBus().subscribe(Ripley.RIPLEY_DIED, (Ripley)->shooterController.dispose());

        scene.getMessageBus().subscribe(Ripley.RIPLEY_WIN, (Ripley)->movableController.dispose());
        scene.getMessageBus().subscribe(Ripley.RIPLEY_WIN, (Ripley)->keeperController.dispose());
        scene.getMessageBus().subscribe(Ripley.RIPLEY_WIN, (Ripley)->shooterController.dispose());
    }

    @Override
    public void sceneUpdating(@NotNull Scene scene) {
        ellen = scene.getFirstActorByType(Ripley.class);
        assert ellen != null;

        ellen.showRipleyState();

        if(ellen.getHealth().getValue() == 0){
            DeathMessage deathMessage = new DeathMessage();
            scene.addActor(deathMessage, ellen.getPosX() - 120, ellen.getPosY() - 20);
        }

        TunnelFinish tunnelFinish = scene.getFirstActorByType(TunnelFinish.class);
        if(tunnelFinish != null && ellen.intersects(tunnelFinish)){
            WinMessage winMessage = new WinMessage();

            cancel(scene);
            scene.addActor(winMessage, ellen.getPosX() - 120, ellen.getPosY() - 20);
        }

    }

    private void cancel(@NotNull Scene scene){
        scene.getInput().onKeyPressed((Ripley)->movableController.dispose());
        scene.getInput().onKeyPressed((Ripley)->keeperController.dispose());
        scene.getInput().onKeyPressed((Ripley)->shooterController.dispose());
        scene.getGame().getInput().onKeyReleased((Ripley)->movableController.dispose());
        scene.getGame().getInput().onKeyReleased((Ripley)->keeperController.dispose());
        scene.getGame().getInput().onKeyReleased((Ripley)->shooterController.dispose());
    }



    @Override
    public void sceneCreated(@NotNull Scene scene) {

    }
}
