package sk.tuke.kpi.oop.game.scenarios;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.*;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.When;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.controllers.KeeperController;
import sk.tuke.kpi.oop.game.controllers.MovableController;
import sk.tuke.kpi.oop.game.items.*;


public class FirstSteps implements SceneListener {
    private Ripley ripley;
    private Energy energy;
    private Ammo ammo;
    private FireExtinguisher fireExtinguisher = new FireExtinguisher();
    private Wrench wrench = new Wrench();
    private Hammer hammer = new Hammer();

    @Override
    public void sceneInitialized(@NotNull Scene scene){
        ripley = new Ripley();
        scene.addActor(ripley, 0, 0);

        MovableController movableController = new MovableController(ripley);
        scene.getInput().registerListener(movableController);
        KeeperController keeperController = new KeeperController(ripley);
        scene.getInput().registerListener(keeperController);

        energy = new Energy();
        scene.addActor(energy, 50, 50);
        new When<>(
            () -> ripley.intersects(energy),
            new Invoke<>(() -> energy.useWith(ripley))
        ).scheduleFor(ripley);



        ammo = new Ammo();
        scene.addActor(ammo,100, 100);

        new When<>(
            () -> ripley.intersects(ammo),
            new Invoke<>(() -> ammo.useWith(ripley))
        ).scheduleFor(ripley);


        ripley.getBackpack().add(fireExtinguisher);
        ripley.getBackpack().add(wrench);
        //ripley.getBackpack().add(hammer);

        scene.addActor(hammer, 100, 10);

        //ripley.getBackpack().shift();
        //ripley.getBackpack().remove(wrench);
        //ripley.getBackpack().getContent();
        //System.out.println(ripley.getBackpack().getContent());
        //System.out.println(ripley.getBackpack().getCapacity());
        //System.out.println(ripley.getBackpack().peek());
    }
    @Override
    public void sceneUpdating(@NotNull Scene scene) {
        int windowHeight = scene.getGame().getWindowSetup().getHeight();
        int yTextPos = windowHeight - GameApplication.STATUS_LINE_OFFSET;
        int windowWidth = scene.getGame().getWindowSetup().getWidth();
        int xTextPos = windowWidth - GameApplication.STATUS_LINE_OFFSET - 680;

        scene.getGame().getOverlay().drawText(" | Energy: " + ripley.getHealth(), xTextPos, yTextPos);
        //pushActorContainer()
        //ActorContainer<Collectible> actors = new ActorContainer<Collectible>()

        scene.getGame().pushActorContainer(ripley.getBackpack());
        scene.getGame().getOverlay().drawText("| Ammo: " + ripley.getAmmo(), 255, yTextPos);
    }
}
