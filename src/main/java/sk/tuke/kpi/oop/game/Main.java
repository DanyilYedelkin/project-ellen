package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.*;
import sk.tuke.kpi.gamelib.backends.lwjgl.LwjglBackend;
//import sk.tuke.kpi.oop.game.scenarios.FirstSteps;
import sk.tuke.kpi.oop.game.scenarios.DieHard;
//import sk.tuke.kpi.oop.game.scenarios.EscapeRoom;
//import sk.tuke.kpi.oop.game.scenarios.MissionImpossible;


public class Main {
    public static void main(String[] args) {
        // setting game window: window name and its dimensions
        WindowSetup windowSetup = new WindowSetup("Project Ellen", 800, 600);

        // creating instance of game application
        // using class `GameApplication` as implementation of interface `Game`
        Game game = new GameApplication(windowSetup, new LwjglBackend()); // in case of Mac OS system use "new Lwjgl2Backend()" as the second parameter

        // creating scene for game
        // using class `World` as implementation of interface `Scene`
        //Scene scene  = new World("World", "maps/mission-impossible.tmx", new MissionImpossible.Factory());
        //Scene scene  = new World("World", "maps/escape-room.tmx", new EscapeRoom.Factory());
        Scene scene  = new World("World", "maps/die-hard.tmx", new DieHard.Factory());

        // adding scene into the game
        game.addScene(scene);

        //MissionImpossible listener = new MissionImpossible();
        //scene.addListener(listener);

        //EscapeRoom listener = new EscapeRoom();
        DieHard listener = new DieHard();
        scene.addListener(listener);

        game.getInput().onKeyPressed(Input.Key.ESCAPE, game::stop);
        // running the game
        game.start();
    }
}
