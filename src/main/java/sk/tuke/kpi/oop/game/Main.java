package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.*;
import sk.tuke.kpi.gamelib.backends.lwjgl.LwjglBackend;
import sk.tuke.kpi.oop.game.scenarios.FirstSteps;


public class Main {
    public static void main(String[] args) {
        // setting game window: window name and its dimensions
        WindowSetup windowSetup = new WindowSetup("Project Ellen", 800, 600);

        // creating instance of game application
        // using class `GameApplication` as implementation of interface `Game`
        Game game = new GameApplication(windowSetup, new LwjglBackend()); // in case of Mac OS system use "new Lwjgl2Backend()" as the second parameter

        // creating scene for game
        // using class `World` as implementation of interface `Scene`
        Scene scene = new World("world");

        // adding scene into the game
        game.addScene(scene);

        FirstSteps listener = new FirstSteps();
        scene.addListener(listener);

        scene.getInput().onKeyPressed(Input.Key.ESCAPE, game::stop);
        // running the game
        game.start();
    }
    public static void scenario(){

    }
}
